package com.ucl.imaginethisserver.Requests;

import javax.validation.constraints.NotNull;
import java.util.List;

/*
 * Class for deserializing and validating request
 * to /api/v1/projects/{project-id}/build endpoint
 */
public class BuildRequest {

    @NotNull(message = "userId is mandatory")
    private String userId;

    @NotNull(message = "wireframeList is mandatory")
    private List<String> wireframeList;

    // Option for specifying whether to publish app or not
    // By default false
    private boolean publish = false;

    public String getUserId() { return userId; }

    public List<String> getWireframeList() { return wireframeList; }

    public boolean getPublishOption() { return publish; }

}
