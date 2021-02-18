package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.Util.FrontendUtil;

/**
 *  Frontend Component Image that contains the URL of the actual image within.
*/
public class ImageComponent extends FrontendComponent {
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String generateCode(){
        try {
            String imageName = FrontendUtil.downloadImage(this.imageURL.replaceAll("\"", ""), FrontendUtil.FOLDER_NAME);
            imageName = imageName.replace("OutputStorage/" + FrontendUtil.FOLDER_NAME, "../..");
            return "<Image\n" +
                    "source={require(\'" + imageName + "\')}\n" +
                    "style={{width: " + this.width +
                    ", height: " + this.height + "}}\n" +
                    "/>";
        }catch (Exception e){
            return "<P>The image component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }

    }
}
