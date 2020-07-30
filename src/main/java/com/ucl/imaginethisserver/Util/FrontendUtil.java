package com.ucl.imaginethisserver.Util;

import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;

import java.util.ArrayList;
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
        inlineComponentList.add(list);
        return inlineComponentList;
    }
}
