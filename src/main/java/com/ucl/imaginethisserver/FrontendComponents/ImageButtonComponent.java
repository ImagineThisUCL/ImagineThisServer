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

    public String getImageURL() { return imageURL; };

    public String getImageName() { return imageURL; };

    public String getTransitionNodeID() { return transitionNodeID; };

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    @Override
    public boolean requiresReusableComponent() { return true; };

    @Override
    public String getReusableComponentName() { return "ImageButton.js"; };

    @Override
    public String generateCode() {
        try {
            StringBuilder code = new StringBuilder();
            code.append("<ImageButton\n");
            if (getTransitionNodeID() != null) {
                String navigateWireframe = ""; // TODO: page.getWireframeByID(transitionNodeID).getName();
                if(NavBarComponent.BUTTON_MAP.containsValue(navigateWireframe)){
                    code.append("onPress={() => this.props.navigation.navigate('NavigationBar', {screen:'" + navigateWireframe + "'})}\n");
                }else{
                    Navigator.NAVIGATOR_MAP.put(navigateWireframe,navigateWireframe);
                    code.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
                }
            }
            code.append("style={{padding: 10}}\n");
            code.append("imageStyle={{width: ").append(getWidth()).append(", height: ").append(getHeight()).append("}}\n");
            code.append("imageSrc={require('").append(getImageName()).append("')}/>");

            return code.toString();
        }catch (Exception e){
            return "<P>The image button component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }
    }
}
