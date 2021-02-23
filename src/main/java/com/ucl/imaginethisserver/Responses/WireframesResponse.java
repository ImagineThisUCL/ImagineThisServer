package com.ucl.imaginethisserver.Responses;

import com.ucl.imaginethisserver.FigmaComponents.Wireframe;

import java.util.List;

public class WireframesResponse {

    private String projectID;

    private String projectName;

    private List<Wireframe> wireframes;

    public WireframesResponse(String projectID, String projectName, List<Wireframe> wireframes) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.wireframes = wireframes;
    }

}
