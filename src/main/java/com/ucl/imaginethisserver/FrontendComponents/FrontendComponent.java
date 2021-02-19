package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Color;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The abstract class to represent a frontend component. All of the frontend components inherit this class.
 */
public abstract class FrontendComponent {

    @Value("config.templateFilesFolder")
    private String templateFilesFolder;

    private int width;
    private int height;
    private int positionX;
    private int positionY;
    private String align;
    private int flex = -1;
    private Color backgroundColor;
    private Color borderColor;
    private double borderWidth;
    private double cornerRadius;

    //
    // Getters
    //
    public int getWidth() { return this.width; }

    public int getHeight(){
     return this.height;
    }

    public int getPositionX(){
     return this.positionX;
    }

    public int getPositionY(){
     return this.positionY;
    }

    public String getAlign() {return this.align;}

    public int getFlex(){
        return this.flex;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public double getBorderWidth() { return borderWidth; };

    public double getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(double cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    //
    // Setters
    //
    public void setWidth(int width) {
     this.width = width;
    }

    public void setHeight(int height){
     this.height = height;
    }

    public void setPositionX(int positionX) {
     this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
     this.positionY = positionY;
    }

    public void setAlign(String align) { this.align = align; }

    public void setFlex(int flex){
        this.flex = flex;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }


    // Denotes whether the component uses a reusable component
    public abstract boolean isReusable();

    public abstract String generateReusableCode() throws IOException;

    public abstract String generateCode();

    // Helper method for reading reusable template files
    protected String readTemplateFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(templateFilesFolder, fileName)));
    }


    /**
    * The judge if two front-end components are locate in the same line.
    * @param anotherComponent The other frontend components.
    * @return
    */
    public boolean isSameLine(FrontendComponent anotherComponent) {

        return this.positionY == anotherComponent.getPositionY();
//        // If they are the same component.
//        if(a_component.width == this.width && a_component.height == this.height && a_component.positionY == this.positionY && a_component.positionX == positionX){
//            return true;
//        }
//
//        if (this.positionX < a_component.positionX) {
//            if (this.positionX + this.width >= a_component.positionX) { return false; };
//        } else {
//            if (a_component.positionX + a_component.width >= this.positionX) { return false; };
//        }
//        if((this.positionY + this.height > a_component.positionY && this.positionY <= a_component.positionY) ||
//            (a_component.positionY + a_component.height > this.positionY && a_component.positionY <= this.positionY)
//            ){
//            return true;
//        } else {
//            return false;
//        }
//    }
    }

    /**
     * Put all of the front components that are considered in the same line into one sub list.
     * @return A list of front component list, each sublist contains the components that are considered at the same horizontal line.
     */
    public static List<List<FrontendComponent>> getInlineComponentList(List<FrontendComponent> frontendComponents) {
        List<List<FrontendComponent>> linesList = new ArrayList<>();
        // Sort components
        frontendComponents.sort((o1, o2) -> {
            if (o1.getPositionY() > o2.getPositionY()) return 1;
            else return -1;
        });
        // We are going to use the fact that isSameLine is an equivalence relation
        for (FrontendComponent component : frontendComponents) {
            boolean lineFound = false;
            for (List<FrontendComponent> line : linesList) {
                if (component.isSameLine(line.get(0))) {
                    line.add(component);
                    lineFound = true;
                    break;
                }
            }
            // If component is not on any already existing line, add a new one
            if (!lineFound) {
                linesList.add(Arrays.asList(component));
            }
        }
        // Sort all lines
        for (List<FrontendComponent> line : linesList) {
            line.sort((o1, o2) -> {
                if (o1.getPositionX() > o2.getPositionX()) return 1;
                else return -1;
            });
        }
        return linesList;
    }


    public static <T extends FrontendComponent> boolean containsComponent(List<FrontendComponent> frontendComponents, Class<T> cls) {
        for (FrontendComponent component : frontendComponents) {
            if (cls.isInstance(component)) return true;
        }
        return false;
    };

}
