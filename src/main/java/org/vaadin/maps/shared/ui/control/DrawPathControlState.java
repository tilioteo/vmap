package org.vaadin.maps.shared.ui.control;

import java.util.Map;

/**
 * @author Kamil Morong
 */
public class DrawPathControlState extends DrawPointControlState {
    public Map<String, String> startPointStyle = null;
    public Map<String, String> startPointHoverStyle = null;
    public Map<String, String> lineStyle = null;
    public Map<String, String> vertexStyle = null;

    {
        primaryStyleName = "v-drawpathcontrol";
    }

}
