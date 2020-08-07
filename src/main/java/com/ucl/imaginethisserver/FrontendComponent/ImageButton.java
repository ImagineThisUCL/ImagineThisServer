package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.DAO.Page;

import java.util.List;

public class ImageButton extends FrontendComponent {
    private String imageURL;
    private String transitionNodeID;

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTransitionNodeID(String transitionNodeID) {
        this.transitionNodeID = transitionNodeID;
    }

    public String generateCode(){
        StringBuilder imageButtonCode = new StringBuilder();
        imageButtonCode.append("<ImageButton\n");
        if(this.transitionNodeID!=null){
            String navigateWireframe = Page.getWireframeByID(transitionNodeID).getName().replaceAll(" ","");
            imageButtonCode.append("onPress={() => this.props.navigation.navigate('").append(navigateWireframe).append("')}\n");
        }
        imageButtonCode.append("style={{padding: 10}}\n");
        imageButtonCode.append("imageStyle={{width: ").append(this.getWidth()).append(", height: ").append(this.getHeight()).append("}}\n");
        imageButtonCode.append("imageSrc={{uri: ").append(this.imageURL).append("}}/>");

        return imageButtonCode.toString();
    }
}
