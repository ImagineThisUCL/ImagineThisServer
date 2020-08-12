package com.ucl.imaginethisserver.FrontendComponent;

import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;

public class Image extends FrontendComponent {
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String generateCode() throws IOException {
        String imageName = FrontendUtil.downloadImage(this.imageURL.replaceAll("\"",""));
        imageName = imageName.replace("OutputApp","../..");
        return "<Image\n" +
                "source={require(\'"+ imageName + "\')}\n" +
                "style={{width: " + this.width +
                ", height: " + this.height + "}}\n" +
                "/>";
    }
}
