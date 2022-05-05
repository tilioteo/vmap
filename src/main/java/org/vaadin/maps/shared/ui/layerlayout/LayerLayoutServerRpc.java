package org.vaadin.maps.shared.ui.layerlayout;

import com.vaadin.shared.communication.ServerRpc;
import org.vaadin.maps.shared.ui.LayoutClickRpc;

/**
 * @author Kamil Morong
 */
public interface LayerLayoutServerRpc extends LayoutClickRpc, ServerRpc {

    void updateMeasuredSize(int newWidth, int newHeight);

}
