package org.vaadin.maps.shared.ui.control;

import com.vaadin.shared.Connector;

import java.util.Map;

/**
 * @author Kamil Morong
 */
public class DrawFeatureControlState extends AbstractControlState {
    public Connector layer = null;
    public Map<String, String> cursorStyle = null;

    {
        primaryStyleName = "v-drawfeaturecontrol";
    }

}
