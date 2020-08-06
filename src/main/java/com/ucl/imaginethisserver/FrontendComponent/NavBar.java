package com.ucl.imaginethisserver.FrontendComponent;


import java.util.ArrayList;
import java.util.HashMap;

public class NavBar extends FrontendComponent{

    public static ArrayList<NavButton> NAV_BUTTONS = new ArrayList<>();

    public static HashMap<String, String> BUTTON_MAP = new HashMap<>();

    public static boolean hasNavBar = false;

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
                "            }}>").append("\n");
        for(NavButton navButton : NAV_BUTTONS){
            code.append(" <Tab.Screen\n" +
                    "                name=\"" + navButton.getText() + "\"\n" +
                    "                component={" + BUTTON_MAP.get(navButton.getText()).replaceAll(" ","") + "}\n" +
                    "                options={{\n" +
                    "                    tabBarIcon: () => {\n" +
                    "                        return (\n" +
                    "                            /* Option b with uri */\n" +
                    "                            <Image\n" +
                    "                                source={{uri: " + navButton.getIconURL() + "}}\n" +
                    "                                style={{width: 24, height: 22}}\n" +
                    "                            />\n" +
                    "                        )\n" +
                    "                    },\n" +
                    "                }}/>").append("\n");
        }
        code.append(" </Tab.Navigator>\n" +
                "    );\n" +
                "}");
        code.append("\n");
        return code.toString();
    }



}

