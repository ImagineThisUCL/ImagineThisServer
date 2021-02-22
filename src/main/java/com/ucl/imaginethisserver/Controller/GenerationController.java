package com.ucl.imaginethisserver.Controller;

import com.ucl.imaginethisserver.FigmaComponents.FigmaFile;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;
import com.ucl.imaginethisserver.Responses.WireframesResponse;
import com.ucl.imaginethisserver.Service.GenerationService;
import com.ucl.imaginethisserver.Util.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Method for building a project
     * @param projectID
     * @param accessToken
     * @param type
     * @param payload
     * @return
     */
    @PostMapping("/projects/{project-id}/build")
    public ResponseEntity<Map<String, Boolean>> buildProject(
            @PathVariable("project-id") String projectID,
            @RequestParam(value = "accessToken") String accessToken,
            @RequestParam(value = "authType") String type,
            @RequestBody Map<String, Object> payload) {

        Authentication auth = new Authentication(type, accessToken);
        List<String> wireframeList = (List<String>) payload.get("wireframeList");

        boolean result = generationService.buildProject(projectID, auth, wireframeList);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Download project's zip file, a zipped directory with all source code
     * @param projectID
     * @return
     */
    @GetMapping("/projects/{project-id}/download")
    public ResponseEntity<Resource> downloadProject(
            @PathVariable("project-id") String projectID) {

        File file = generationService.downloadProject(projectID);

        if (projectID == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
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
     * Return a list of wireframes of the Figma project
     * @param projectID
     * @param accessToken
     * @param type
     * @return
     */
    @GetMapping("/projects/{project-id}/wireframes")
    public ResponseEntity<WireframesResponse> getFigmaProject(
            @PathVariable("project-id") String projectID,
            @RequestParam(value = "accessToken") String accessToken,
            @RequestParam(value = "authType") String type) {

        Authentication auth = new Authentication(type, accessToken);
        FigmaFile figmaFile = generationService.getFigmaFile(projectID, auth);
        String projectName = figmaFile.getProjectName();
        List<Wireframe> wireframes = figmaFile.getWireframes();
        WireframesResponse response = new WireframesResponse(projectID, projectName, wireframes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
