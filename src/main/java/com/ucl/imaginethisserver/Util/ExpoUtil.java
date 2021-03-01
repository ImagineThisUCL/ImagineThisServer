package com.ucl.imaginethisserver.Util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpoUtil {

    private final Logger logger = LoggerFactory.getLogger(ExpoUtil.class);

    @Value("${config.outputStorageFolder}")
    private String OUTPUT_STORAGE_FOLDER;

    /**
     * Method that creates a new Docker container (from image imaginethis-expo see
     * https://github.com/ImagineThisUCL/ImagineThisExpo) that publishes app build
     * to Expo. Output directory is mounted into the container as volume.
     * @param projectID
     * @param projectName
     * @return success
     */
    public boolean publish(String projectID, String projectName) {

        logger.info("Started publishing project {} to Expo.", projectName);

        List<String> environmentVariables = new ArrayList<>();
        environmentVariables.add("PROJECT_NAME=" + projectName);
        environmentVariables.add("PROJECT_ID=" + projectID);

        // Start a Docker container that will publish the Expo app
        // Mount the project folder directory into the container directory /usr/src/app
        try {
            String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, projectID);
            DockerClient dockerClient = DockerClientBuilder.getInstance().build();
            CreateContainerResponse container
                    = dockerClient.createContainerCmd("imaginethis-expo")
                    .withEnv(environmentVariables)
                    .withHostConfig(HostConfig.newHostConfig().withBinds(new Bind(projectFolder, new Volume("/usr/src/app"))))
                    .exec();
            dockerClient.startContainerCmd(container.getId()).exec();
        } catch(Exception e) {
            logger.error("Error in publishing app to Expo.", e);
        }
        return true;
    }
}
