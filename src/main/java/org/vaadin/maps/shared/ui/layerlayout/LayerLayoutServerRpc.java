package org.vaadin.maps.shared.ui.layerlayout;

import org.vaadin.maps.shared.ui.LayoutClickRpc;

import com.vaadin.shared.communication.ServerRpc;

public interface LayerLayoutServerRpc extends LayoutClickRpc, ServerRpc {
	
	public void updateMeasuredSize(int newWidth, int newHeight);

}
