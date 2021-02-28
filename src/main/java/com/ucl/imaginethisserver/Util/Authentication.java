package com.ucl.imaginethisserver.Util;


/*
 * Class used for authenticating users and for storing data
 * used for authentication against Figma API.
 */
public class Authentication {

    private String userID;
    private AuthenticationType figmaAuthType;
    private String figmaAccessToken;

    public Authentication() {}

    public Authentication(String figmaAuthType, String figmaAccessToken) {
        assert(figmaAuthType.equals("originalToken") || figmaAuthType.equals("oauth2Token"));
        if (figmaAuthType.equals("originalToken")) {
            this.figmaAuthType = AuthenticationType.ORIGINAL_TOKEN;
        } else if (figmaAuthType.equals("oauth2Token")) {
            this.figmaAuthType = AuthenticationType.OAUTH2;
        }
        this.figmaAccessToken = figmaAccessToken;
        this.userID = null;
    }

    public Authentication(String figmaAuthType, String figmaAccessToken, String userID) {
        this(figmaAuthType, figmaAccessToken);
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public AuthenticationType getFigmaAuthType() {
        return figmaAuthType;
    }

    public String getFigmaAccessToken() {
        return figmaAccessToken;
    }


}
