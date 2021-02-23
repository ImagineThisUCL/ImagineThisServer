package com.ucl.imaginethisserver.Util;

public class Authentication {

    private AuthenticationType type;
    private String accessToken;

    public Authentication(String authType, String authAccessToken) {

        assert(authType.equals("originalToken") || authType.equals("oauth2Token"));

        if (authType.equals("originalToken")) {
            type = AuthenticationType.ORIGINAL_TOKEN;
        } else if (authType.equals("oauth2Token")) {
            type = AuthenticationType.OAUTH2;
        }

        accessToken = authAccessToken;
    }

    public AuthenticationType getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
