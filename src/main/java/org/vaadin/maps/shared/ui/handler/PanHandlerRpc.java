/**
 * 
 */
package org.vaadin.maps.shared.ui.handler;

import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 *
 */
public interface PanHandlerRpc extends ServerRpc {

	public void panStart(long timestamp, int x, int y);

	public void panEnd(long timestamp, int deltaX, int deltaY);

}
