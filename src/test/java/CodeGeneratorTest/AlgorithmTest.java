package CodeGeneratorTest;

import com.ucl.imaginethisserver.FrontendComponent.FrontendComponent;
import net.minidev.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmTest {
    public static void main(String[] args) {
       String str = "abcd\nefg";
       String oStr = str.replaceAll("\n","{\"\\\\n\"}");
        System.out.println(oStr);
//        ArrayList<Integer> testList = new ArrayList<>();
//        testList.add(2);
//        testList.add(2);
//        testList.add(3);
//        testList.add(3);
//        testList.add(5);
//        testList.add(7);
//        testList.add(8);
//        testList.add(8);
//        testList.add(9);
//        testList.add(9);
//        ArrayList<List<Integer>> equalList = new ArrayList<>();
//        int startIndex = 0;
//        int endIndex = -1;
//        int previousPosY = testList.get(0);
//        for(int i = 0; i < testList.size(); i++){
//            if(testList.get(i)!= previousPosY){
//                previousPosY = testList.get(i);
//                List<Integer> list = new ArrayList<>();
//                for(int t = startIndex; t <= endIndex; t++){
//                    list.add(testList.get(t));
//                }
//                equalList.add(list);
//                startIndex = i;
//                endIndex = startIndex;
//            }else{
//                endIndex ++;
//            }
//        }
//        List<Integer> list = new ArrayList<>();
//        for(int t = startIndex; t <= endIndex; t++){
//            list.add(testList.get(t));
//        }
//        equalList.add(list);
//        System.out.println(equalList);
    }
}
