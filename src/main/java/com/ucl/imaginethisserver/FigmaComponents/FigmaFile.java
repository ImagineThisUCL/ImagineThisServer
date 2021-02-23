package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.FrontendComponents.NavBarComponent;

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

    public String getProjectID() { return projectID; }
    public String getProjectName() { return projectName; }
    public String getLastModified() { return lastModified; }
    public String getVersion() { return version; }
    public List<Page> getPages() { return pages; }

    public void setProjectName(String name) { this.projectName = name; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }
    public void setVersion(String version) { this.version = version; }

    public void addPage(Page page) { pages.add(page); }

    public List<Wireframe> getWireframes() {
        List<Wireframe> wireframes = new ArrayList<>();
        for (Page page : pages) {
            wireframes.addAll(page.getWireframes());
        }
        return wireframes;
    }

    public Wireframe getWireframeById(String id) {
        for (Wireframe wireframe : getWireframes()) {
            if (wireframe.getId().equals(id)) return wireframe;
        }
        return null;
    }

    public List<FigmaComponent> getComponents() {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        for (Page page : getPages()) {
            figmaComponents.addAll(page.getComponents());
        }
        return figmaComponents;
    }

    public <T extends FigmaComponent> boolean containsComponent(Class<T> cls) {
        return FigmaComponent.containsComponent(getComponents(), cls);
    }

    public String getInitialWireframeName() {
        if (containsComponent(Navigation.class)) return NavBarComponent.NAME;
        // Return the first wireframe
        if (!getWireframes().isEmpty()) return getWireframes().get(0).getName();
        return null;
    }
}
