package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * The response that server send to the client side after authentication process.
 */
public class AuthenticateResponse {
    /**
     * The name of current target Figma project
     */
    @Expose()
    private String projectName;
    /**
     * A list of wireframes that the current target Figma project contains
     */
    @Expose()
    private List<Wireframe> wireframeList;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setWireframeList(List<Wireframe> wireframeList) {
        this.wireframeList = wireframeList;
    }

    public AuthenticateResponse(String projectName, List<Wireframe> wireframes){
        this.projectName = projectName;
        this.wireframeList = wireframes;
    }
}
