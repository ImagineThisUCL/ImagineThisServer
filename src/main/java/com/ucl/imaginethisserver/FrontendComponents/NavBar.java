package com.ucl.imaginethisserver.FrontendComponents;


import com.ucl.imaginethisserver.DAO.Fills;
import com.ucl.imaginethisserver.Util.FrontendUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class NavBar extends FrontendComponent {

    /**
     * A list of navigation button that the bottom navigation bar contains.
     */
    public static ArrayList<NavButton> NAV_BUTTONS = new ArrayList<>();
    /**
     * The key is the name of the button
     * The value is the name of the wireframe name that the button corresponding to.
     */
    public static HashMap<String, String> BUTTON_MAP = new HashMap<>();
    public static List<Fills> containerFills;
    /**
     * If there is any exception be thrown during bottom navigation bar source code generation.
     */
    public static boolean isError = false;


    public static boolean hasPlaceholder() {
        for (String wireframe : BUTTON_MAP.values()) {
            if (wireframe.equals("Placeholder")) {
                return true;
            }
        }
        return false;
    }

    public String generateCode() throws IOException {
        try {
            String backgroundColor = "\"#D5E6EC\"";
            if(containerFills != null && containerFills.size() > 0){
                backgroundColor = containerFills.get(0).getColor().toString();
            }
            NAV_BUTTONS.sort(new Comparator<NavButton>() {
                @Override
                public int compare(NavButton o1, NavButton o2) {
                    if (o1.getPositionX() > o2.getPositionX()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
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
                    "                    backgroundColor: " + backgroundColor +",\n" +
                    "                    paddingBottom: 2,\n" +
                    "                    paddingTop: 2,\n" +
                    "                    height: 65,\n" +
                    "                },\n" +
                    "            }}>").append("\n");
            for(NavButton navButton : NAV_BUTTONS) {
                String imageName = FrontendUtil.downloadImage(navButton.getIconURL().replaceAll("\"", ""), FrontendUtil.FOLDER_NAME);
                imageName = imageName.replace("OutputStorage/" + FrontendUtil.FOLDER_NAME, ".");
                String viewName;
                if (BUTTON_MAP.get(navButton.getText()) != null) {
                    viewName = BUTTON_MAP.get(navButton.getText()).replaceAll("[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~@#￥%……&*——+|{}‘”“’ -]", "");
                } else {
                    viewName = "Placeholder";
                }
                code.append(" <Tab.Screen\n" +
                        "                name=\"" + navButton.getText() + "\"\n" +
                        "                component={" + viewName + "}\n" +
                        "                options={{\n" +
                        "                    tabBarIcon: () => {\n" +
                        "                        return (\n" +
                        "                            <Image\n" +
                        "                                source={require(\'" + imageName + "\')}\n" +
                        "                                style={{width: 26, height: 26}}\n" +
                        "                                resizeMode=\"contain\"\n" +
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
        } catch (Exception e) {
            this.isError = true;
            return "<View>\n" +
                    "    <P>The navigation bar code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" +
                    "</View>\n";

        }
    }
}