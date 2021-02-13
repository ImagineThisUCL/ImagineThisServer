package com.ucl.imaginethisserver.Controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.DAO.GenerateResponse;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import com.ucl.imaginethisserver.Util.FrontendUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  Functions used to handle two kinds of authentication of Figma API.
 */
@RestController
public class GenerationController {
    /**
     * @param payload The json format data which comes from the front-end. The format is
     *                {
     *                  "accessToken" : [Figma accessToken used to request the API],
     *                  "projectID": [Figma project ID used to request the API],
     *                  "type" : [Request Type], there are two types of request method, which are 'originalToken' and 'oauth2Token',
     *                           'originalToken': the user authenticate with Figma using his personal [access token] and  [project ID]
     *                           'oauth2Token': the user authenticate with Figma using OAuth 2.0 protocol.
     *                  "nameList": the list of wireframes that the user try to generate.
     *                }
     *
     * @param response
     * @return  A Generate Response Object, which has two fields.
     *          - isSuccess: a boolean value to indicate if the generation process is successful
     *          - fileName: the generated zip file name if the it succeed
     *                      null if it is fail.
     * @throws IOException
     */
    // @CrossOrigin(origins = "http://localhost:3000")
    @ApiIgnore
    @CrossOrigin(origins = "http://88.80.186.99")
    @PostMapping("/api/v1/generatePage")
    public GenerateResponse generatePages(@RequestBody Map<String, Object> payload, HttpServletResponse response) throws IOException {
        String accessToken = payload.get("accessToken").toString();
        String projectID = payload.get("projectID").toString();
        String type = payload.get("authType").toString();
        List<String> nameList = FigmaAPIUtil.processPagesList((List<String>) payload.get("pageList"));
        AuthenticateType authType = null;
        if (type.equals("originalToken")) {
            authType = AuthenticateType.ORIGINAL_TOKEN;
        } else if (type.equals("oauth2Token")) {
            authType = AuthenticateType.OAUTH2;
        }
        try {
            JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID, accessToken, authType);
            String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"", "");
            if (figmaTreeStructure == null) {
                return new GenerateResponse(false, null);
            }
            FrontendUtil.FOLDER_NAME = projectID;
            FrontendUtil.GENERATE_PAGE_LIST = nameList;
            FigmaAPIUtil.generatePageByName(nameList,
                    figmaTreeStructure,
                    projectID,
                    accessToken,
                    authType,FrontendUtil.FOLDER_NAME);

            // Start a Docker container that will publish the Expo app
            try {
                System.out.println("Started running a publishing container!!");
                String mountDirectory = System.getProperty("user.dir") + "/OutputStorage/" + projectID;
                DockerClient dockerClient = DockerClientBuilder.getInstance().build();
                CreateContainerResponse container
                        = dockerClient.createContainerCmd("imaginethis-expo")
                        .withEnv("PROJECT_NAME=" + projectName)
                        .withHostConfig(HostConfig.newHostConfig().withBinds(new Bind(mountDirectory, new Volume("/usr/src/app"))))
                        .exec();
                dockerClient.startContainerCmd(container.getId()).exec();
            } catch(Exception e) {
                System.out.println("Error in publishing app to Expo.");
            }

            return new GenerateResponse(true, "OutputStorage/" + projectID + ".zip");
        } catch (Exception e) {
            e.printStackTrace();
            FileUtils.deleteDirectory(new File("OutputStorage/" + FrontendUtil.FOLDER_NAME));
            return new GenerateResponse(false, null);

        }
    }

    /**
     * This method is used to download the generate file from the server to the client side.
     * @param fileName the target file name that uesr try to generate.
     */
    // @CrossOrigin(origins = "http://localhost:3000")
    @ApiIgnore
    @CrossOrigin(origins = "http://88.80.186.99")
    @GetMapping("/api/v1/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam (value = "fileName") String fileName,  HttpServletRequest request) throws IOException {
        File file = new File(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        //The default name of the generate output file is OutputApp.zip
        headers.setContentDispositionFormData("myfile","OutputApp.zip");
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
