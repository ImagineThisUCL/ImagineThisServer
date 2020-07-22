package com.ucl.imaginethisserver.Controller;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.DAO.AuthenticateResponse;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class AuthenticateController {

    @GetMapping("/authToken")
    public AuthenticateResponse getFigmaProject(@RequestParam(value = "projectID") String projectID, @RequestParam(value = "accessToken") String accessToken,
                                                @RequestParam(value = "authType") String type, HttpServletResponse response) throws IOException {
        AuthenticateType authType = null;
        if(type.equals("originalToken")){
            authType = AuthenticateType.ORIGINAL_TOKEN;
        }else if(type.equals("oauth2Token")){
            authType = AuthenticateType.OAUTH2;
        }
        JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID, accessToken,authType);
        if (figmaTreeStructure == null) {
            response.setStatus(500);
            return null;
        }
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);
        testPage.loadWireframes(projectID, accessToken, authType);
        List<Wireframe> responseList = testPage.getWireframeList();
        response.setStatus(200);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse(projectName,responseList);
        return authenticateResponse;

    }


}
