package org.vaadin.maps.shared.ui.layerlayout;

import org.vaadin.maps.shared.ui.AbstractLayoutState;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Morong
 */
public class LayerLayoutState extends AbstractLayoutState {
    // Maps each component to a position
    public Map<String, String> connectorToCssPosition = new HashMap<>();

    {
        primaryStyleName = "v-layerlayout";
    }
}