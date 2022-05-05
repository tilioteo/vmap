package org.vaadin.maps.shared.ui.layer;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 */
public interface WMSLayerServerRpc extends ServerRpc {

    public void requestSingleTile(int width, int height, int shiftX, int shiftY);

}
