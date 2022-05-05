package org.vaadin.maps.shared.ui.mapcontainer;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 */
public interface MapContainerRpc extends ServerRpc {

    void panEnd(int shiftX, int shiftY);

    void zoom(double zoom);

}
