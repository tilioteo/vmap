package org.vaadin.maps.shared.ui.control;

import com.vaadin.shared.communication.ClientRpc;

/**
 * @author Kamil Morong
 */
public interface ControlClientRpc extends ClientRpc {

    void activate();

    void deactivate();

}
