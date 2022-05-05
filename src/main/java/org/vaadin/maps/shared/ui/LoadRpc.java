package org.vaadin.maps.shared.ui;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 */
public interface LoadRpc extends ServerRpc {
    /**
     * Called when a load event has occurred and there are server side listeners
     * for the event.
     */
    void load(long timestamp);

    /**
     * Called when a load event has occurred and there are server side listeners
     * for the event.
     */
    void error(long timestamp);
}
