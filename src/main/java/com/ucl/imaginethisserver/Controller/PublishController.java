package com.ucl.imaginethisserver.Controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class PublishController {

    @Value("config.outputStorageFolder")
    private String outputStorageFolder;

    @PostMapping("/api/v1/projects/{project-id}/publish")
    public void generatePages(@PathVariable("project-id") String projectID) throws IOException {


        // Start a Docker container that will publish the Expo app
//        try {
//            System.out.println("Started running a publishing container!!");
//            String mountDirectory = System.getProperty("user.dir") + "/OutputStorage/" + projectID;
//            DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//            CreateContainerResponse container
//                    = dockerClient.createContainerCmd("imaginethis-expo")
//                    .withEnv("PROJECT_NAME=" + projectName)
//                    .withHostConfig(HostConfig.newHostConfig().withBinds(new Bind(mountDirectory, new Volume("/usr/src/app"))))
//                    .exec();
//            dockerClient.startContainerCmd(container.getId()).exec();
//        } catch(Exception e) {
//            System.out.println("Error in publishing app to Expo.");
//        }

    }
}
