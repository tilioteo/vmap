package org.vaadin.maps.shared.ui;

import com.vaadin.shared.Connector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 */
public interface LayoutClickRpc extends ServerRpc {
    /**
     * Called when a layout click event has occurred and there are server side
     * listeners for the event.
     *
     * @param mouseDetails     Details about the mouse when the event took place
     * @param clickedConnector The child component that was the target of the event
     */
    void layoutClick(long timestamp, MouseEventDetails mouseDetails, Connector clickedConnector);
}
