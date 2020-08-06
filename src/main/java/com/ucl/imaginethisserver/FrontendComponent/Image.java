package com.ucl.imaginethisserver.FrontendComponent;

public class Image extends FrontendComponent {
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String generateCode(){
        return "<Image\n" +
                "source={require("+ this.imageURL +")}\n" +
                "/>";
    }
}
