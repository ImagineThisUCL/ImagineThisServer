package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.DAO.FigmaComponent;
import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FrontendUtil {
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
}
