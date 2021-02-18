package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FigmaComponents.Page;
import com.ucl.imaginethisserver.FigmaComponents.Wireframe;

import java.util.ArrayList;
import java.util.List;

public class FigmaFile {

    private String projectID;
    private String projectName;
    private String lastModified;
    private String version;

    private List<Page> pages;

    public FigmaFile(String projectID) {
        this.projectID = projectID;
        this.pages = new ArrayList<>();
    }

    public String getProjectID() { return projectID; };
    public String getProjectName() { return projectName; };
    public String getLastModified() { return lastModified; };
    public String getVersion() { return version; };

    public void addPage(Page page) { pages.add(page); };
    public List<Page> getPages() { return pages; };

    public List<Wireframe> getWireframes() {
        List result = new ArrayList<>();
        for (Page page : pages) {
            for (Wireframe wireframe : page.getWireframes()) {
                result.add(wireframe);
            }
        }
        return result;
    };

    public void setProjectName(String name) { this.projectName = name; };
    public void setLastModified(String lastModified) { this.lastModified = lastModified; };
    public void setVersion(String version) { this.version = version; };


}
