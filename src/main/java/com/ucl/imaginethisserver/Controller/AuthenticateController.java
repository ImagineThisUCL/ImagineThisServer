package com.ucl.imaginethisserver.Controller;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthenticateController {

    @GetMapping("/authToken")
    public List<Wireframe> getFigmaProject(@RequestParam(value="projectID")String projectID, @RequestParam(value = "accessToken")String accessToken, HttpServletResponse response) throws IOException {
        JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID,accessToken);
        if(figmaTreeStructure == null){
            response.setStatus(500);
            return null;
        }
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);
        testPage.loadWireframes(projectID,accessToken);
        List<Wireframe> responseList = testPage.getWireframeList();
        System.out.println(responseList.get(0));
        response.setStatus(200);
        return responseList;

    }
}
