package com.ucl.imaginethisserver.Controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class GenerationController {
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/generatePage")
    public GenerateResponse generatePages(@RequestBody Map<String, Object> payload, HttpServletResponse response) throws IOException {
        String accessToken = payload.get("accessToken").toString();
        String projectID = payload.get("projectID").toString();
        String type = payload.get("authType").toString();
        List<String> nameList = FigmaAPIUtil.convertJsonToList(payload.get("pageList").toString());
        AuthenticateType authType = null;
        if (type.equals("originalToken")) {
            authType = AuthenticateType.ORIGINAL_TOKEN;
        } else if (type.equals("oauth2Token")) {
            authType = AuthenticateType.OAUTH2;
        }
        try {
            JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID, accessToken, authType);
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
            return new GenerateResponse(true, "OutputStorage/" + projectID + ".zip");
        } catch (Exception e) {
            e.printStackTrace();
            FileUtils.deleteDirectory(new File("OutputStorage/" + FrontendUtil.FOLDER_NAME));
            return new GenerateResponse(false, null);

        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam (value = "fileName") String fileName,  HttpServletRequest request) throws IOException {
        File file = new File(fileName);
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
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
