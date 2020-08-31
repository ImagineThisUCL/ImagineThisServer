package com.ucl.imaginethisserver.FrontendComponent;

import java.io.IOException;

/**
 * The abstract class to represent a frontend component. All of the frontend components inherit this class.
 */
public abstract class FrontendComponent {
     protected int width;
     protected int height;
     protected int positionX;
     protected int positionY;
     protected String align;
     protected int flex = -1;

     public String generateCode() throws IOException {
         return "";
     }
     public int getFlex(){
         return this.flex;
     }

     public void setFlex(int flex){
         this.flex = flex;
     }
     public int getWidth(){
         return this.width;
     }

     public int getHeight(){
         return this.height;
     }

     public int getPositionX(){
         return this.positionX;
     }

     public int getPositionY(){
         return this.positionY;
     }

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

     public void setAlign(String align){this.align = align; }

     public String getAlign() {return this.align;}

    /**
     * The judge if two front-end components are locate in the same line.
     * @param a_component The other frontend components.
     * @return
     */
     public boolean isSameLine(FrontendComponent a_component){
         // If they are the same component.
         if(a_component.width == this.width && a_component.height == this.height && a_component.positionY == this.positionY && a_component.positionX == positionX){
             return true;
         }
         if(this.positionX < a_component.positionX){
             if(this.positionX + this.width < a_component.positionX){
                 if((this.positionY + this.height > a_component.positionY && this.positionY <= a_component.positionY)||(a_component.positionY + a_component.height > this.positionY && a_component.positionY <= this.positionY)){
                     return true;
                 }else{
                     return false;
                 }

             }else{
                 return false;
             }
         }else {
             if(a_component.positionX + a_component.width < this.positionX){
                 if((this.positionY + this.height > a_component.positionY && this.positionY <= a_component.positionY)||(a_component.positionY + a_component.height > this.positionY && a_component.positionY <= this.positionY)){
                     return true;
                 }else{
                     return false;
                 }

             }else{
                 return false;
             }
         }
     }

}
