package com.ucl.imaginethisserver.FrontendComponents;


import com.ucl.imaginethisserver.FigmaComponents.Color;
import com.ucl.imaginethisserver.FigmaComponents.Group;
import com.ucl.imaginethisserver.FigmaComponents.Paint;
import com.ucl.imaginethisserver.Util.CodeGenerator;
import com.ucl.imaginethisserver.Util.FrontendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class NavBarComponent extends GroupComponent {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

    private Color backgroundColor = new Color(213, 230, 236, 1);

    /**
     * A list of navigation button that the bottom navigation bar contains.
     */
    public static List<NavigationButtonComponent> NAV_BUTTONS = new ArrayList<>();
    /**
     * The key is the name of the button
     * The value is the name of the wireframe name that the button corresponding to.
     */
    public static HashMap<String, String> BUTTON_MAP = new HashMap<>();
    public static List<Paint> containerFills;
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

    @Override
    public boolean requiresReusableComponent() { return false; };

    @Override
    public String getReusableComponentName() { return null; };

    @Override
    public String generateCode() {
        try {
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
                    "                    backgroundColor: " + backgroundColor.toString() +",\n" +
                    "                    paddingBottom: 2,\n" +
                    "                    paddingTop: 2,\n" +
                    "                    height: 65,\n" +
                    "                },\n" +
                    "            }}>").append("\n");

            for (FrontendComponent component : getComponents()) {
                if (!(component instanceof NavigationButtonComponent)) continue;
                NavigationButtonComponent navigationButton = (NavigationButtonComponent) component;
                code.append(" <Tab.Screen\n" +
                        "                name=\"" + navigationButton.getText() + "\"\n" +
                        "                component={" + navigationButton.getText() + "}\n" + // TODO: Needs fixing
                        "                options={{\n" +
                        "                    tabBarIcon: () => {\n" +
                        "                        return (\n" +
                        "                            <Image\n" +
                        "                                source={require(\'" + navigationButton.getImageName() + "\')}\n" +
                        "                                style={{width: 26, height: 26}}\n" +
                        "                                resizeMode=\"contain\"\n" +
                        "                            />\n" +
                        "                        )\n" +
                        "                    },\n" +
                        "                }}/>\n");
            }
            code.append(" </Tab.Navigator>\n" +
                    "    );\n" +
                    "}\n");
            return code.toString();

        } catch (Exception e) {
            logger.error("Failed to generate NavBar component.");
            return "<View>\n" +
                    "    <P>The navigation bar code couldn't be generated due to some unexpected errors, please check your structure of figma file based on our guideline</P>\n" +
                    "</View>\n";

        }
    }
}
