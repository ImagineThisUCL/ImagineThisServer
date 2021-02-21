package com.ucl.imaginethisserver.FrontendComponents;

import com.ucl.imaginethisserver.FigmaComponents.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NavBarComponent extends GroupComponent {

    public static final String NAME = "NavigationBar";
    private final Logger logger = LoggerFactory.getLogger(NavBarComponent.class);
    private Color backgroundColor = new Color(213, 230, 236, 1);

    @Override
    public String getReusableComponentName() { return null; }

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
