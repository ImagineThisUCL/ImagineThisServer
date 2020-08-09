package CodeGeneratorTest;

import com.google.gson.JsonObject;
import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.DAO.Group;
import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.Navigator;
import com.ucl.imaginethisserver.Util.AuthenticateType;
import com.ucl.imaginethisserver.Util.FigmaAPIUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException {
        String type = "originalToken";
        String projectID = "YpBnZ4aEB2YgGpiOQfxQCU";
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
        ArrayList<String> nameList = new ArrayList<>();
//        nameList.add("Set Up");
//        nameList.add("Information to populat messages");
//        nameList.add("Reach out");
//        nameList.add("Care Network Page");
        nameList.add("Start Passive Tracking and Reporting");
//        nameList.add("Report for Sharing");
        nameList.add("Wellbeing by Activity");

        generatePage(nameList,
                figmaTreeStructure,
                projectID,
                accessToken,
                authType);
    }

    public static void generatePage(ArrayList<String> names, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType) throws IOException {
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);

        testPage.loadWireframes(projectID, accessToken, authType);
//        List<Wireframe> responseList = testPage.getWireframeList();
        CodeGenerator.generatePackageFile();
        for(String name : names){
            System.out.println("Now Generating: " + name);
            Wireframe setUpWireframe = testPage.getWireframeByName(name);
            setUpWireframe.loadComponent(projectID,accessToken,authType);
            setUpWireframe.sortComponentByY();
            for(FigmaComponent component : setUpWireframe.getComponentList()){
                if(component.getType().equals("GROUP")){
                    ((Group)component).loadComponent(projectID,accessToken,authType);
                }
            }
            CodeGenerator.writeWireframeCode(setUpWireframe.getName(),setUpWireframe, projectID, accessToken, authType);
        }
        for(String wireframeName : Navigator.NAVIGATOR_MAP.keySet()){
            if(!names.contains(wireframeName)){
                Navigator.NAVIGATOR_MAP.put(wireframeName, "Placeholder");
                Navigator.hasPlaceholder = true;
            }
        }
        if(WireframeComponent.IsContainNavBar()){
            CodeGenerator.writeAppJSCode(WireframeComponent.NAV_BAR);
        }else if(!Navigator.NAVIGATOR_MAP.isEmpty()){
            CodeGenerator.writeAppJSCode(null);
        }
        if(NavBar.hasPlaceholder() || Navigator.hasPlaceholder){
            CodeGenerator.writePlaceholderCode();
        }
    }
}
