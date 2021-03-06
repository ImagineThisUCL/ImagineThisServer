package com.ucl.imaginethisserver.Util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ExpoUtil {

    private final Logger logger = LoggerFactory.getLogger(ExpoUtil.class);

    private final String EXPO_CONTAINER_IMAGE = "imaginethissystem_imaginethis-expo";
    private final String EXPO_VOLUME_NAME = "imaginethissystem_imaginethis-backend-output-storage";

    private static final String EXPO_AUTH_API = "https://exp.host/--/api/v2/auth";
    private static final String EXPO_GRAPHQL_API = "https://expo.io/--/graphql";

    private String sessionString = "";

    @Value("${config.outputStorageFolder}")
    private String OUTPUT_STORAGE_FOLDER;

    @Value("${config.expoAccountUsername}")
    private String EXPO_USERNAME;

    @Value("${config.expoAccountPassword}")
    private String EXPO_PASSWORD;

    @Value("${config.expoOrganizationID}")
    private String EXPO_ORGANIZATION_ID;

    /**
     * Method that creates a new Docker container (from image imaginethis-expo see
     * https://github.com/ImagineThisUCL/ImagineThisExpo) that publishes app build
     * to Expo. Output directory is mounted into the container as volume.
     * @param projectID
     * @param projectName
     * @return success
     */
    public boolean publish(String projectID, String projectName, UUID conversionID) {

        logger.info("Started publishing project {} to Expo.", projectName);

        List<String> environmentVariables = new ArrayList<>();
        environmentVariables.add("PROJECT_NAME=" + projectName);
        environmentVariables.add("PROJECT_ID=" + projectID);
        environmentVariables.add("CONVERSION_ID=" + conversionID);

        // Start a Docker container that will publish the Expo app
        // Mount the project folder directory into the container directory /usr/src/app
        try {
            String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, projectID);
            DockerClient dockerClient = DockerClientBuilder.getInstance().build();
            CreateContainerResponse container
                    = dockerClient.createContainerCmd(EXPO_CONTAINER_IMAGE)
                    .withName(String.format("imaginethis-expo-%s", projectID)) // Useful so that multiple publishing jobs of same project cannot run at once
                    .withEnv(environmentVariables)
                    .withHostConfig(HostConfig.newHostConfig()
                            .withBinds(Bind.parse(String.format("%s:/usr/src/app", EXPO_VOLUME_NAME)))
                            .withNetworkMode("imaginethis-network") // Connect it to same bridge Docker network as the whole system
                            .withAutoRemove(true)) // Remove container once finished
                    .exec();
            dockerClient.startContainerCmd(container.getId()).exec();
        } catch(Exception e) {
            logger.error("Error in publishing app to Expo.", e);
            return false;
        }
        return true;
    }

    public JsonObject sendPostRequest(URL url, Map<String, String> headers, String payload) throws IOException {
        // open up connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // open output stream
        connection.setDoOutput(true);
        // set request method
        connection.setRequestMethod("POST");
        // set headers
        for (Map.Entry<String, String> e : headers.entrySet()) {
            connection.setRequestProperty(e.getKey(), e.getValue());
        }
        // set request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        logger.info("Sending request to " + url.toString());
        // send request and get response
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // close connection
            connection.disconnect();
            return new Gson().fromJson(response.toString(), JsonObject.class);
        } catch (IOException e) {
            logger.error("Error sending request.");
            throw e;
        }
    }

    /**
     * This method will send an invitation email to the target user
     * @param email
     * @return the invitation ID
     */
    public String invite(String email) throws IOException {
        // check if the env variables has been set
        if (EXPO_USERNAME == null || EXPO_PASSWORD == null || EXPO_ORGANIZATION_ID == null) {
            logger.error("Expo user credential hasn't been set! Please check the environment variables.");
            return null;
        }
        // first login to the expo
        try{
            login(EXPO_USERNAME, EXPO_PASSWORD);
        } catch (IOException e) {
            logger.error("Error sending invitation.", e.fillInStackTrace());
            return null;
        }
        // send request to expo graphql endpoint
        String statement = ExpoGraphqlStatement.createUserInvitationForAccount(EXPO_ORGANIZATION_ID, email, "[VIEW]");
        URL queryURL = new URL(EXPO_GRAPHQL_API);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("expo-session", sessionString);
        try {
            JsonObject response = sendPostRequest(queryURL, headers, statement);
            return response.get("data").getAsJsonObject()
                                    .get("userInvitation").getAsJsonObject()
                                    .get("createUserInvitationForAccount").getAsJsonObject()
                                    .get("id").getAsString();
        } catch (IOException e) {
            logger.error("Error sending invitation: " + e.fillInStackTrace());
            return null;
        }
    }

    public boolean cancelInvitation(String invitationID) throws IOException {
        if (sessionString.equals("")) {
            logger.error("No session string found, please login first.");
            return false;
        }
        String statement = ExpoGraphqlStatement.createCancelInvitationStatement(invitationID);
        URL queryURL = new URL(EXPO_GRAPHQL_API);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("expo-session", sessionString);
        try {
            sendPostRequest(queryURL, headers, statement);
            return true;
        } catch (IOException e) {
            logger.error("Error canceling invitation: " + e.fillInStackTrace());
        }
        return false;
    }

    /**
     * This method will login to the expo.io and get the session object string
     * @param username
     * @param password
     * @return
     */
    public JsonObject login(String username, String password) throws IOException {
        URL loginURL = new URL(EXPO_AUTH_API + "/loginAsync");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        JsonObject body = new JsonObject();
        body.addProperty("username", username);
        body.addProperty("password", password);
        try {
            JsonObject response = sendPostRequest(loginURL, headers, body.toString());
            sessionString = response.get("data").getAsJsonObject().get("sessionSecret").getAsString().replace("\\", "");
            return response;
        } catch (IOException e) {
            logger.error("Error logging to expo: " + e.fillInStackTrace());
        }
        return null;
    }
}
