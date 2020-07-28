package com.ucl.imaginethisserver.FrontendComponent;


import java.util.ArrayList;
import java.util.HashMap;

public class NavBar extends FrontendComponent{

    public static ArrayList<NavButton> NAV_BUTTONS = new ArrayList<>();

    public static HashMap<String, String> BUTTON_MAP = new HashMap<>();

    public static boolean hasPlaceholder(){
        for(String wireframe : BUTTON_MAP.values()){
            if(wireframe.equals("Placeholder")){
                return true;
            }
        }
        return false;
    }

    public String generateCode(){
        StringBuilder code = new StringBuilder();
        code.append("function NavigationBar() {\n" +
                "    return (\n" +
                "        <Tab.Navigator\n" +
                "            tabBarOptions={{\n" +
                "                activeTintColor: \"#147EFB\",\n" +
                "                inactiveTintColor: \"#000000\",\n" +
                "                labelStyle: {\n" +
                "                    fontSize: 12,\n" +
                "                },\n" +
                "                style: {\n" +
                "                    backgroundColor: \"#D5E6EC\",\n" +
                "                    paddingBottom: 2\n" +
                "                },\n" +
                "            }}>");
        for(NavButton navButton : NAV_BUTTONS){
            code.append("<Tab.Screen\n" +
                    "                name=\"" + navButton.getText() + "\"\n" +
                    "                component={" + BUTTON_MAP.get(navButton.getText()).replaceAll(" ","") +"}\n" +
                    "                options={{\n" +
                    "                    tabBarIcon: ({ color, size }) => (\n" +
                    "                          <Image source={{uri : "+ navButton.getIconURL() + "}}/>\n\n" +
                    "                    )\n" +
                    "                }}/>");
        }
        code.append(" </Tab.Navigator>\n" +
                "    );\n" +
                "}");
        code.append("\n");
        return code.toString();
    }



}

