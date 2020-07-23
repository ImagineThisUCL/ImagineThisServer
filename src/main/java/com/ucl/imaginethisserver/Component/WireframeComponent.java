package com.ucl.imaginethisserver.Component;

import com.ucl.imaginethisserver.CodeGenerator.CodeGenerator;
import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.DAO.Text;
import com.ucl.imaginethisserver.DAO.Wireframe;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

import java.io.IOException;
import java.util.ArrayList;

public class WireframeComponent{
    public ArrayList<FrontendComponent> frontendComponentList = new ArrayList<>();
    private boolean isContainText;

    public WireframeComponent(Wireframe wireframe) throws IOException {
        for(FigmaComponent component : wireframe.getComponentList()){
            if(component.getType().equals("TEXT")){
                frontendComponentList.add((Text)component);
                if(!isContainText){
                    isContainText = true;
                    CodeGenerator.writeReusableComponentCode(ReusableComponent.P);
                }
            }
        }
    }

    public String generateImportCode(){
       StringBuilder importCode = new StringBuilder();
       importCode.append("import { \n" +
               "    StyleSheet, \n" +
               "    View, \n" +
               "    Button } from \"react-native\"\n" +
               "import React, { Component } from \"react\"" + "\n");
       if(isContainText){
           importCode.append("import P from '../reusables/P'" + "\n");

       }
       importCode.append("\n");

       return importCode.toString();
    }

    public String generateStyleCode(){
        StringBuilder styleCode = new StringBuilder();
        styleCode.append("const styles = StyleSheet.create({\n" +
                "  view: {\n" +
                "    flex: 1,\n" +
                "    padding: 10,\n" +
                "    backgroundColor: \"#11287B\"\n" +
                "  },\n" +
                "  p: {\n" +
                "    marginBottom: 20,\n" +
                "    fontSize: 22,\n" +
                "    color: \"#f2f2f2\"\n" +
                "  },\n" +
                "  buttonWrapper: {\n" +
                "      flexDirection: \"row\",\n" +
                "      justifyContent: \"space-between\"\n" +
                "  },\n" +
                "  button: {\n" +
                "      fontSize: 28\n" +
                "  }\n" +
                "})" + "\n");
        styleCode.append("\n");
        return styleCode.toString();
    }

    public String generateViewCode(String className){
        StringBuilder viewCode = new StringBuilder();
        viewCode.append("class ").append(className).append(" extends Component {");
        viewCode.append("render() {\n" +
                "        return (\n" +
                "            <View style={styles.view}>" + "\n");
        for(FrontendComponent frontendComponent : frontendComponentList){
            viewCode.append(frontendComponent.generateCode()).append("\n");
        }
        viewCode.append("  </View>\n" +
                "        )\n" +
                "    }\n" +
                "}\n" +
                "export default ").append(className);

        return viewCode.toString();
    }

    public String generateCode(String className) {
        StringBuilder code = new StringBuilder();
        code.append(this.generateImportCode()).append(this.generateStyleCode()).append(this.generateViewCode(className));
        return code.toString();
    }


}
