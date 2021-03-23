package com.ucl.imaginethisserver.Responses;

import com.ucl.imaginethisserver.FigmaComponents.FigmaWireframe;

import java.util.List;

public class WireframesResponse {

    private String projectID;

    private String projectName;

    private List<FigmaWireframe> wireframes;

    public WireframesResponse(String projectID, String projectName, List<FigmaWireframe> wireframes) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.wireframes = wireframes;
    }

}
