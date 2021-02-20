package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.Util.FigmaAPIUtil;
import com.ucl.imaginethisserver.Util.FrontendUtil;

/**
 *  Frontend Component Image that contains the URL of the actual image within.
*/
public class ImageComponent extends FrontendComponent {

    private String imageURL;

    @Override
    public boolean isReusable() { return false; };

    @Override
    public String generateReusableCode() { return null; };

    @Override
    public String generateCode(){
        try {
            return "<Image\n" +
                    "source={require(\'" + imageURL + "\')}\n" +
                    "style={{width: " + getWidth() +
                    ", height: " + getHeight() + "}}\n" +
                    "/>";
        } catch (Exception e) {
            return "<P>The image component code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" ;
        }

    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
