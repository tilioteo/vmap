/**
 * 
 */
package org.vaadin.maps.shared.ui.handler;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 *
 */
public interface ZoomHandlerRpc extends ServerRpc {

	public void zoomChange(long timestamp, double zoomStep);

}
