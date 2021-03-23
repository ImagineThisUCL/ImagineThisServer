package com.ucl.imaginethisserver.FigmaComponents;

import com.ucl.imaginethisserver.ReactComponents.NavBarComponent;

import java.util.ArrayList;
import java.util.List;

public class FigmaFile {

    private String projectID;
    private String projectName;
    private String lastModified;
    private String version;

    private List<FigmaPage> figmaPages;

    public FigmaFile(String projectID) {
        this.projectID = projectID;
        this.figmaPages = new ArrayList<>();
    }

    public String getProjectID() { return projectID; }
    public String getProjectName() { return projectName; }
    public String getLastModified() { return lastModified; }
    public String getVersion() { return version; }
    public List<FigmaPage> getPages() { return figmaPages; }

    public void setProjectName(String name) { this.projectName = name; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }
    public void setVersion(String version) { this.version = version; }

    public void addPage(FigmaPage figmaPage) { figmaPages.add(figmaPage); }

    public List<FigmaWireframe> getWireframes() {
        List<FigmaWireframe> wireframes = new ArrayList<>();
        for (FigmaPage figmaPage : figmaPages) {
            wireframes.addAll(figmaPage.getWireframes());
        }
        return wireframes;
    }

    public FigmaWireframe getWireframeById(String id) {
        for (FigmaWireframe wireframe : getWireframes()) {
            if (wireframe.getId().equals(id)) return wireframe;
        }
        return null;
    }

    public List<FigmaComponent> getComponents() {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        for (FigmaPage figmaPage : getPages()) {
            figmaComponents.addAll(figmaPage.getComponents());
        }
        return figmaComponents;
    }
    public List<FigmaComponent> getAllComponents() {
        List<FigmaComponent> figmaComponents = new ArrayList<>();
        for (FigmaPage figmaPage : getPages()) {
            figmaComponents.addAll(figmaPage.getAllComponents());
        }
        return figmaComponents;
    }

    public <T extends FigmaComponent> boolean containsComponent(Class<T> cls) {
        return FigmaComponent.containsComponent(getComponents(), cls);
    }

    public String getInitialWireframeName() {
        if (containsComponent(Navigation.class)) return NavBarComponent.NAME;
        // Return Page's initial wireframe's name
        if (!getPages().isEmpty()) {
            String initialWireframeId = getPages().get(0).getPrototypeStartNodeID();
            if (initialWireframeId == null) return null;
            return getWireframeById(initialWireframeId).getName();
        }
        return null;
    }

    public void filterWireframesByName(List<String> wireframeList) {
        for (FigmaPage figmaPage : getPages()) {
            figmaPage.filterWireframesByName(wireframeList);
        }
    }
}
