package CodeGeneratorTest;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.DAO.Group;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.List;

public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException {
        String type = "originalToken";
        //Navigation Bar
        String projectID = "YpBnZ4aEB2YgGpiOQfxQCU";
        //Form
//        String projectID = "o611joQBw7GbvEKWX7ZKQl";
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
        String name = "Information to populat messages";
//        String name = "Reach out";
//        String name = "Care Network Page";
        String name = "Information to populat messages";
        generatePage(name,
                figmaTreeStructure,
                projectID,
                accessToken,
                authType);
    }

    public static void generatePage(String name, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType) throws IOException {
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);

        testPage.loadWireframes(projectID, accessToken, authType);
//        List<Wireframe> responseList = testPage.getWireframeList();
        CodeGenerator.generatePackageFile();
        Wireframe setUpWireframe = testPage.getWireframeByName(name);
        setUpWireframe.loadComponent(projectID,accessToken,authType);
        setUpWireframe.sortComponentByY();
        for(FigmaComponent component : setUpWireframe.getComponentList()){
            if(component.getType().equals("GROUP")){
                ((Group)component).loadComponent(projectID,accessToken,authType);
            }
        }
        CodeGenerator.writeWireframeCode(setUpWireframe.getName(),setUpWireframe, projectID, accessToken, authType);
        if(WireframeComponent.IsContainNavBar()){
            CodeGenerator.writeAppJSCode(WireframeComponent.NAV_BAR);
        }
        if(NavBar.hasPlaceholder()){
            CodeGenerator.writePlaceholderCode();
        }
    }
}
