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
import com.ucl.imaginethisserver.Util.FrontendUtil;
import com.ucl.imaginethisserver.Util.ZipUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CodeGeneratorTest {
    public static void main(String[] args) throws IOException {
        String type = "originalToken";
        String projectID = "YpBnZ4aEB2YgGpiOQfxQCU";
//        String projectID = "NZFfXsPz4Zhkt3zSa6lIyB";
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
        String pageName = "Screens";
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Set Up");
//        nameList.add("Information to populat messages");
        nameList.add("Reach out");
//        nameList.add("Care Network Page");
//        nameList.add("Start Passive Tracking and Reporting");
//        nameList.add("Report for Sharing");
//        nameList.add("Wellbeing by Activity");
//        nameList.add("Wellbeing Network");
//        nameList.add("Wellbeing by Activity");
////        nameList.add("Wellbeing rating 1");
//        nameList.add("Wellbeing rating 2");
        nameList.add("Weekly Confirm Wellbeing");
//        nameList.add("ABUB");
//        nameList.add("Age Connect");
//        nameList.add("Friend of Mine");
//        nameList.add("Torfaen");
//        nameList.add("Carers Trust");
//        nameList.add("Dewis");
//        nameList.add("Calls made v Well-being");
//        nameList.add("Outdoor steps v Well-being");
//        nameList.add("Messages Sent");
//        nameList.add("Dementia Support 1");
//        nameList.add("Dementia Support 2");
//        nameList.add("Headspace");
//        nameList.add("Home Page: Weekly");
//        nameList.add("Wellbeing rating 2");
//        nameList.add("Messages Sent");
//        nameList.add("Outdoor steps v Well-being");
        FrontendUtil.FOLDER_NAME = projectID;
        generatePageByName(nameList,
                figmaTreeStructure,
                projectID,
                accessToken,
                authType,FrontendUtil.FOLDER_NAME);
    }

    public static void generatePageByName(List<String> names, JsonObject figmaTreeStructure, String projectID, String accessToken, AuthenticateType authType, String folderName) throws IOException {
        String projectName = figmaTreeStructure.get("name").toString().replaceAll("\"","");
        List<Page> pageList = FigmaAPIUtil.extractPages(figmaTreeStructure);
        Page testPage = pageList.get(0);

        testPage.loadWireframes(projectID, accessToken, authType);
        CodeGenerator.generatePackageFile(folderName);
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
            CodeGenerator.writeWireframeCode(setUpWireframe.getName(),setUpWireframe, projectID, accessToken, authType, folderName);
        }
        for(String wireframeName : Navigator.NAVIGATOR_MAP.keySet()){
            if(!names.contains(wireframeName)){
                Navigator.NAVIGATOR_MAP.put(wireframeName, "Placeholder");
                Navigator.hasPlaceholder = true;
            }
        }
        if(WireframeComponent.IsContainNavBar()){
            CodeGenerator.writeAppJSCode(WireframeComponent.NAV_BAR, folderName);
        }else if(!Navigator.NAVIGATOR_MAP.isEmpty()){
            CodeGenerator.writeAppJSCode(null, folderName);
        }
        if(NavBar.hasPlaceholder() || Navigator.hasPlaceholder){
            CodeGenerator.writePlaceholderCode(folderName);
        }
        //Zip the output folder to a zip file so that the user could download
//        ZipUtil.zipFile("OutputStorage/" + folderName);
//        FileUtils.deleteDirectory(new File("OutputStorage/" + folderName));
    }
}
