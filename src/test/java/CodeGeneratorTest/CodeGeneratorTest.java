package CodeGeneratorTest;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.DAO.Group;
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

        generatePage("Set Up",
                figmaTreeStructure,
                projectID,
                accessToken,
                authType);

        generatePage("Information to populat messages",
                figmaTreeStructure,
                projectID,
                accessToken,
                authType);

//        knowWTFisPassed("Information to populat messages",
//                figmaTreeStructure,
//                projectID,
//                accessToken,
//                authType);

    }

    public static void knowWTFisPassed(String name, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType) throws IOException {
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);
        testPage.loadWireframes(projectID, accessToken, authType);
        Wireframe foundWireframe = testPage.getWireframeByName(name);
        foundWireframe.loadComponent(projectID,accessToken,authType);
        foundWireframe.sortComponentByY();

        System.out.print(foundWireframe);
    }

    public static void generatePage(String name, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType) throws IOException {
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);

        testPage.loadWireframes(projectID, accessToken, authType);
//        List<Wireframe> responseList = testPage.getWireframeList();
        Wireframe foundWireframe = testPage.getWireframeByName(name);

        foundWireframe.loadComponent(projectID,accessToken,authType);
        foundWireframe.sortComponentByY();

        for(FigmaComponent component : foundWireframe.getComponentList()){
            if(component.getType().equals("GROUP")){
                ((Group)component).loadComponent(projectID,accessToken,authType);
            }
        }

        CodeGenerator.generateOutputFolder();
        CodeGenerator.writeWireframeCode(foundWireframe.getName(),foundWireframe);
//        CodeGenerator.writeReusableComponentCode(ReusableComponent.P);
    }
}
