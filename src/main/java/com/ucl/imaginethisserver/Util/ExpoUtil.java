package com.ucl.imaginethisserver.Util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpoUtil {

    @Value("${config.outputStorageFolder}")
    private String OUTPUT_STORAGE_FOLDER;

    private static Logger logger = LoggerFactory.getLogger(ExpoUtil.class);

    public void publish(FigmaFile figmaFile) {
        logger.info("Started publishing project {} to Expo.", figmaFile.getProjectName());

        List<String> environmentVariables = new ArrayList<>();
        environmentVariables.add("PROJECT_NAME=" + figmaFile.getProjectName());
        environmentVariables.add("PROJECT_ID=" + figmaFile.getProjectID());

        // Start a Docker container that will publish the Expo app
        // Mount the project folder directory into the container directory /usr/src/app
        try {
            String projectFolder = String.format("%s/%s", OUTPUT_STORAGE_FOLDER, figmaFile.getProjectID());
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
    }

}
