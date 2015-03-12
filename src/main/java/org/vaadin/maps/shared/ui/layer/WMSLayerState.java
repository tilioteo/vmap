/**
 * 
 */
package org.vaadin.maps.shared.ui.layer;

import org.vaadin.maps.shared.ui.AbstractLayerState;

/**
 * @author kamil
 *
 */
@SuppressWarnings("serial")
public class WMSLayerState extends AbstractLayerState {
    {
        primaryStyleName = "v-wmslayer";
    }
    
    public boolean singleTile = true;
}
