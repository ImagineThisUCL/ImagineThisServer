package CodeGeneratorTest;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.ReusableComponent;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.*;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.List;

public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException {
        String type = "originalToken";
        String projectID = "YpBnZ4aEB2YgGpiOQfxQCU";
        String accessToken = "55606-14145446-ae86-4135-9be4-6c1e47195471";
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
        Wireframe setUpWireframe = testPage.getWireframeByName("Reach out");
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
