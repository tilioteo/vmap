/**
 * 
 */
package org.vaadin.maps.shared.ui.control;

import com.vaadin.shared.communication.ClientRpc;

/**
 * @author Kamil Morong
 *
 */
public interface ControlClientRpc extends ClientRpc {

	public void activate();

	public void deactivate();

}
