package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Page;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;

public class ImageButton extends FrontendComponent {
    private String imageURL;
    private String transitionNodeID;

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    public String generateCode() throws IOException {
        try {
            StringBuilder imageButtonCode = new StringBuilder();
            String imageName = FrontendUtil.downloadImage(this.imageURL.replaceAll("\"", ""), FrontendUtil.FOLDER_NAME);
            imageName = imageName.replace("OutputStorage/" + FrontendUtil.FOLDER_NAME, "../..");
            imageButtonCode.append("<ImageButton\n");
            if (this.transitionNodeID != null) {
                String navigateWireframe = Page.getWireframeByID(transitionNodeID).getName().replaceAll(" ", "");
                imageButtonCode.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
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
