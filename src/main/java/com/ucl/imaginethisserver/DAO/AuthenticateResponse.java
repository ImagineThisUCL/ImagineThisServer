package com.ucl.imaginethisserver.DAO;

import com.google.gson.annotations.Expose;

import java.util.List;

public class AuthenticateResponse {
    @Expose()
    private String projectName;
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
