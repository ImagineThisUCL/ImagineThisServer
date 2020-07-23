package CodeGeneratorTest;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.ReusableComponent;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.AuthenticateResponse;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.List;

public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException {
        String type = "originalToken";
        String projectID = "BitRrRD7JWTBWYaC9qCCIN";
        String accessToken = "54950-b9461cc1-f3c2-41f8-9fe7-a8f741083aa7";
        AuthenticateType authType = null;
        if(type.equals("originalToken")){
            authType = AuthenticateType.ORIGINAL_TOKEN;
        }else if(type.equals("oauth2Token")){
            authType = AuthenticateType.OAUTH2;
        }
        JsonObject figmaTreeStructure = FigmaAPIUtil.requestFigmaFile(projectID, accessToken,authType);
        if (figmaTreeStructure == null) {
            return;
        }
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);
        testPage.loadWireframes(projectID, accessToken, authType);
//        List<Wireframe> responseList = testPage.getWireframeList();
        Wireframe setUpWireframe = testPage.getWireframeByName("Set Up");
        setUpWireframe.loadComponent(projectID,accessToken,authType);
        setUpWireframe.sortComponentByY();
        CodeGenerator.writeWireframeCode(setUpWireframe.getName(),setUpWireframe);
//        CodeGenerator.writeReusableComponentCode(ReusableComponent.P);
    }
}
