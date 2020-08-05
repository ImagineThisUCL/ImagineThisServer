package com.ucl.imaginethisserver.FrontendComponent;

public class IconButton extends FrontendComponent{

    private String text;
    private String iconURL;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String generateCode() {
        return
        "<TouchableOpacity>\n" +
        "<Image\n" +
        "source={{uri: " + this.getIconURL() + "}}\n" +
        "style={{width: " + this.width +
        ", height: " + this.height +
        "}}\n" +
        "/>\n" +
        "</TouchableOpacity>";
    }

}
