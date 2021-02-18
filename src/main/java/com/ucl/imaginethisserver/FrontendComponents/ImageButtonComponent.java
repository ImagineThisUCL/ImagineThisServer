package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Page;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;

/**
 *  Image button that contains the image of the button's view and
 *  the transitionNodeID of the button, basically the redirect link of it.
*/
public class ImageButtonComponent extends FrontendComponent {
    private String imageURL;
    private String transitionNodeID;

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    @Override
    public boolean isReusable() { return true; };

    @Override
    public String generateReusableCode() throws IOException {
        return readTemplateFile("ImageButton.js");
    };

    @Override
    public String generateCode() {
        try {
            StringBuilder imageButtonCode = new StringBuilder();
            String imageName = FrontendUtil.downloadImage(this.imageURL.replaceAll("\"", ""), FrontendUtil.FOLDER_NAME);
            imageName = imageName.replace("OutputStorage/" + FrontendUtil.FOLDER_NAME, "../..");
            imageButtonCode.append("<ImageButton\n");
            if (this.transitionNodeID != null) {
                String navigateWireframe = ""; // TODO: page.getWireframeByID(transitionNodeID).getName();
                if(NavBarComponent.BUTTON_MAP.containsValue(navigateWireframe)){
                    imageButtonCode.append("onPress={() => this.props.navigation.navigate('NavigationBar', {screen:'" + navigateWireframe + "'})}\n");
                }else{
                    Navigator.NAVIGATOR_MAP.put(navigateWireframe,navigateWireframe);
                    imageButtonCode.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
                }
            }
            imageButtonCode.append("style={{padding: 10}}\n");
            imageButtonCode.append("imageStyle={{width: ").append(this.getWidth()).append(", height: ").append(this.getHeight()).append("}}\n");
            imageButtonCode.append("imageSrc={require(\'").append(imageName).append("\')}/>");

            return imageButtonCode.toString();
        }catch (Exception e){
            return "<P>The image button component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }
}
