package com.ucl.imaginethisserver.FrontendComponent;

import java.io.IOException;

public abstract class FrontendComponent {
     protected int width;
     protected int height;
     protected int positionX;
     protected int positionY;
     protected String align;

     public String generateCode() throws IOException {
         return "";
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

     public boolean isSameLine(FrontendComponent a_component){
         if(this.positionX < a_component.positionX){
             if(this.positionX + this.width < a_component.positionX){
                 if( a_component.positionY + a_component.height > this.positionY){
                     return true;
                 }else{
                     return false;
                 }

             }else{
                 return false;
             }
         }else {
             if(a_component.positionX + a_component.width < this.positionX){
                 if( a_component.positionY + a_component.height > this.positionY){
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
