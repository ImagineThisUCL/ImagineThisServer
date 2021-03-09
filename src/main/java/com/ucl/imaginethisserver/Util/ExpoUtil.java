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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /**
     * This method will send an invitation email to the target user
     * @param email
     * @return
     */
    public boolean invite(String email) throws IOException {
        // check if the env variables has been set
        if (EXPO_USERNAME == null || EXPO_PASSWORD == null || EXPO_ORGANIZATION_ID == null) {
            logger.error("Expo user credential hasn't been set! Please check the environment variables.");
            return false;
        }
        // first login to the expo
        try{
            JsonObject object = this.login(EXPO_USERNAME, EXPO_PASSWORD);
        } catch (IOException e) {
            logger.error("Error sending invitation.", e.fillInStackTrace());
            return false;
        }
        // send request to expo graphql endpoint
        String statement = ExpoGraphqlStatement.createUserInvitationForAccount(EXPO_ORGANIZATION_ID, email, "[VIEW]");
        URL queryURL = new URL(EXPO_GRAPHQL_API);
        HttpURLConnection connection = (HttpURLConnection)queryURL.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        // set session header
        connection.setRequestProperty("expo-session", sessionString);
        logger.info("" + connection.getRequestProperties());
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = statement.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // get the response
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("Response from Expo graphql: " + response.toString());
            return true;
        } catch (IOException e) {
            logger.error("Error: " + e.fillInStackTrace());
            return false;
        }
    }

    public boolean cancelInvitation(String invitationID) throws IOException {
        if (sessionString.equals("")) {
            logger.error("No session string found, please login first.");
            return false;
        }
        String statement = ExpoGraphqlStatement.createCancelInvitationStatement(invitationID);
        URL queryURL = new URL(EXPO_GRAPHQL_API);
        HttpURLConnection connection = (HttpURLConnection)queryURL.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        // set session header
        connection.setRequestProperty("expo-session", sessionString);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = statement.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        // get the response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("Response from Expo graphql: " + response.toString());
            return true;
        } else {
            logger.error("Error canceling invitation: " + connection.getResponseMessage());
            return false;
        }
    }

    /**
     * This method will login to the expo.io and get the session object string
     * @param username
     * @param password
     * @return
     */
    public JsonObject login(String username, String password) throws IOException {
        URL loginURL = new URL(EXPO_AUTH_API + "/loginAsync");
        HttpURLConnection connection = (HttpURLConnection)loginURL.openConnection();
        connection.setRequestMethod("POST");
        // set request body
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        JsonObject body = new JsonObject();
        body.addProperty("username", username);
        body.addProperty("password", password);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        // get the response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject object = new Gson().fromJson(response.toString(), JsonObject.class);
            sessionString = object.get("data").getAsJsonObject().get("sessionSecret").getAsString().replace("\\", "");
            return object;
        } else {
            logger.error("Error logging to expo: " + connection.getResponseMessage());
            return null;
        }
    }
}
