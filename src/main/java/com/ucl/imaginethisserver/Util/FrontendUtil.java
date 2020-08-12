package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FrontendUtil {
    private static final AtomicInteger IMAGE_ID = new AtomicInteger(0);

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

    public static String downloadImage(String imageUrl) throws IOException {
        File outputAppFolder = new File("OutputApp");
        outputAppFolder.mkdir();
        File assetsFolder = new File("OutputApp/assets");
        assetsFolder.mkdir();
        File imgFolder = new File("OutputApp/assets/img");
        imgFolder.mkdir();

        URL url = new URL(imageUrl);
        BufferedImage img = ImageIO.read(url);
        String imageName = "OutputApp/assets/img/" + IMAGE_ID.incrementAndGet() + ".png";
        File file = new File(imageName);
        ImageIO.write(img, "png", file);
        return imageName;
    }
}
