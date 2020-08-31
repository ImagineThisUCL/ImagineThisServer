package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.Component.WireframeComponent;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;
import com.ucl.imaginethisserver.FrontendComponent.NavBar;
import com.ucl.imaginethisserver.FrontendComponent.Navigator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FrontendUtil {
    private static final AtomicInteger IMAGE_ID = new AtomicInteger(0);
    public static String FOLDER_NAME = "";
    public static List<String> GENERATE_PAGE_LIST = new ArrayList<>();


    /**
     * Put all of the front components that are considered in the same line into one sub list.
     * @param frontendComponentList The list that contains of target all front components.
     * @return A list of front component list, each sublist contains the components that are considered at the same horizontal line.
     */
    public static ArrayList<List<FrontendComponent>> getInlineComponentList(ArrayList<FrontendComponent> frontendComponentList){
        ArrayList<List<FrontendComponent>> inlineComponentList = new ArrayList<>();
        FrontendComponent preComponent = frontendComponentList.get(0);
        int startIndex = 0;
        int endIndex = -1;
        for(int i = 0; i < frontendComponentList.size(); i++){
            if(!frontendComponentList.get(i).isSameLine(preComponent)){
                preComponent = frontendComponentList.get(i);
                List<FrontendComponent> list = new ArrayList<>();
                for(int t = startIndex; t <= endIndex; t++){
                    list.add(frontendComponentList.get(t));
                }
                if(list.size() > 1){
                    list.sort(new Comparator<FrontendComponent>() {
                        @Override
                        public int compare(FrontendComponent o1, FrontendComponent o2) {
                            if (o1.getPositionX() > o2.getPositionX()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    });
                }
                inlineComponentList.add(list);
                startIndex = i;
                endIndex = startIndex;
            }else{
                endIndex ++;
            }
        }
        List<FrontendComponent> list = new ArrayList<>();
        for(int t = startIndex; t <= endIndex; t++){
            list.add(frontendComponentList.get(t));
        }
        if(list.size() > 1){
            list.sort(new Comparator<FrontendComponent>() {
                @Override
                public int compare(FrontendComponent o1, FrontendComponent o2) {
                    if (o1.getPositionX() > o2.getPositionX()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }
        inlineComponentList.add(list);
        return inlineComponentList;
    }

    /**
     * Download the image from Figma server to the local server according to the figma url.
     * @param imageUrl target image URL
     * @param folderName target download folder name
     * @return the name of downlaoded image (in 'png' format)
     * @throws IOException
     */
    public static String downloadImage(String imageUrl, String folderName) throws IOException {
        File storageFile = new File("OutputStorage");
        storageFile.mkdir();
        File outputAppFolder = new File("OutputStorage/" + folderName);
        outputAppFolder.mkdir();
        File assetsFolder = new File("OutputStorage/" + folderName + "/assets");
        assetsFolder.mkdir();
        File imgFolder = new File("OutputStorage/" + folderName + "/assets/img");
        imgFolder.mkdir();

        URL url = new URL(imageUrl);
        BufferedImage img = ImageIO.read(url);
        String imageName = "OutputStorage/" + folderName + "/assets/img/" + IMAGE_ID.incrementAndGet() + ".png";
        File file = new File(imageName);
        ImageIO.write(img, "png", file);
        return imageName;
    }

    /**
     * Set all of the static variable to its initial value. This method would be called at the beginning of every project generation request.
     */
    public static void refreshStaticVariable(){
        WireframeComponent.NAV_BAR = null;
        WireframeComponent.setIsContainNavbar(false);

        Navigator.NAVIGATOR_MAP = new HashMap<>();
        Navigator.hasPlaceholder = false;

        NavBar.NAV_BUTTONS = new ArrayList<>();
        NavBar.BUTTON_MAP = new HashMap<>();
        NavBar.isError = false;
    }
}
