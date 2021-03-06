package com.ucl.imaginethisserver.ReactComponents;

/**
 *  Frontend Component Image that contains the URL of the actual image within.
*/
public class ImageComponent extends ReactComponent {

    private String imageURL;

    @Override
    public String getReusableComponentName() { return null; }

    @Override
    public String generateCode(){
        try {
            return "<Image\n" +
                    "source={{uri: '" + imageURL + "'}}\n" +
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
