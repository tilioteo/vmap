package org.vaadin.maps.shared.ui.handler;

import com.vaadin.shared.Connector;

/**
 * @author Kamil Morong
 */
public class PointHandlerState extends FeatureHandlerState {
    public Connector layer = null;

    {
        primaryStyleName = "v-pointhandler";
    }
}
