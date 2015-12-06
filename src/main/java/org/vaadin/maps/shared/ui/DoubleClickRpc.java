/**
 * 
 */
package org.vaadin.maps.shared.ui;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

/**
 * @author Kamil Morong
 *
 */
public interface DoubleClickRpc extends ServerRpc {

	/**
	 * Called when a double click event has occurred and there are server side
	 * listeners for the event.
	 * 
	 * @param mouseDetails
	 *            Details about the mouse when the event took place
	 */
	public void doubleClick(long timestamp, MouseEventDetails mouseDetails);

}
