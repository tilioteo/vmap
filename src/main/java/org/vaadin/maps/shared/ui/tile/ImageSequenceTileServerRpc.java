/**
 * 
 */
package org.vaadin.maps.shared.ui.tile;

import org.vaadin.maps.shared.ui.LoadRpc;

import com.vaadin.shared.MouseEventDetails;

/**
 * @author kamil
 *
 */
public interface ImageSequenceTileServerRpc extends LoadRpc {
	
    /**
     * Called when a click event has occurred and there are server side
     * listeners for the event.
     * 
     * @param mouseDetails
     *            Details about the mouse when the event took place
     * 
     * @param index
     *            Index of frame
     */
    public void click(long timestamp, MouseEventDetails mouseDetails, int index);
    
    public void changed(long timestamp, int index);
    
}
