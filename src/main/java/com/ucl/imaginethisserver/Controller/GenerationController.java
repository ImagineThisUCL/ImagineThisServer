package com.ucl.imaginethisserver.Controller;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.DAO.FigmaFile;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.Service.GenerationService;
import com.ucl.imaginethisserver.Util.Authentication;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sun.jvm.hotspot.memory.Generation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Functions used to handle two kinds of authentication of Figma API.
 */
@RestController
@RequestMapping("/api/v1")
public class GenerationController {

    private final GenerationService generationService;

    @Autowired
    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
    }


    /**
     * @param payload The json format data which comes from the front-end. The format is
     *                {
     *                  "accessToken" : [Figma accessToken used to request the API],
     *                  "type" : [Request Type], there are two types of request method, which are 'originalToken' and 'oauth2Token',
     *                           'originalToken': the user authenticate with Figma using his personal [access token] and  [project ID]
     *                           'oauth2Token': the user authenticate with Figma using OAuth 2.0 protocol.
     *                  "wireframeList": the list of wireframes that the user try to generate.
     *                }
     *
     * @return  A Generate Response Object, which has two fields.
     *          - success: a boolean value to indicate if the generation process is successful
     * @throws IOException
     */
    @PostMapping("/projects/{project-id}/build")
    public ResponseEntity<Map<String, Boolean>> buildProject(
            @RequestBody Map<String, Object> payload,
            @PathVariable("project-id") String projectID) throws IOException {

        String accessToken = payload.get("accessToken").toString();
        String type = payload.get("authType").toString();
        Authentication auth = new Authentication(type, accessToken);
        List<String> wireframeList = (List<String>) payload.get("wireframeList");
        Map<String, Boolean> response = new HashMap<>();

        generationService.buildProject(projectID, auth, wireframeList);
        return null;
    }

    /**
     * This method is used to download the generate file from the server to the client side.
     */
    @GetMapping("/projects/{project-id}/download")
    public ResponseEntity<Resource> downloadProject(
            @PathVariable("project-id") String projectID,
            HttpServletRequest request) throws IOException {

        File file = new File(projectID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        // The default name of the generate output file is OutputApp.zip
        headers.setContentDispositionFormData("myfile","OutputApp.zip");
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * The method is used to get access of target Figma project wireframes
     * @param projectID The figma project ID which the user try to access
     * @param accessToken The user's personal access token
     * @param type authenticate type
     *             - originalToken
     *             - oauth2Token
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/projects/{project-id}/wireframes")
    public ResponseEntity<List<Wireframe>> getFigmaProject(
            @PathVariable("project-id") String projectID,
            @RequestParam(value = "accessToken") String accessToken,
            @RequestParam(value = "authType") String type,
            HttpServletResponse response) throws IOException {

//        Authentication auth = new Authentication(type, accessToken);
//        JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID, auth);
//        if (figmaTreeStructure == null) {
//            response.setStatus(500);
//            return null;
//        }
//        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"", "");
////        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
//        Page firstPage = pageList.get(0);
//        firstPage.loadWireframes(projectID, auth);
//        List<Wireframe> responseList = firstPage.getWireframeList();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
