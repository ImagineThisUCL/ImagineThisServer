package com.ucl.imaginethisserver.Responses;

import com.google.gson.annotations.Expose;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;

import java.util.List;

public class WireframesResponse {

    @Expose
    private String projectID;

    @Expose
    private String projectName;

    @Expose
    private List<Wireframe> wireframes;

    public WireframesResponse(String projectID, String projectName, List<Wireframe> wireframes) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.wireframes = wireframes;
    }

}
