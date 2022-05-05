package org.vaadin.maps.client.ui.layer;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.AbstractLayerConnector;
import org.vaadin.maps.client.ui.VControlLayer;
import org.vaadin.maps.shared.ui.layer.ControlLayerState;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.layer.ControlLayer.class)
public class ControlLayerConnector extends AbstractLayerConnector {

    @Override
    public VControlLayer getWidget() {
        return (VControlLayer) super.getWidget();
    }

    @Override
    public ControlLayerState getState() {
        return (ControlLayerState) super.getState();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        // We always have 1 child, unless the child is hidden
        Widget content = getContentWidget();
        if (content != null) {
            getWidget().setWidget(content);
        }
    }

}
