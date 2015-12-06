/**
 * 
 */
package org.vaadin.maps.shared.ui.layer;

import org.vaadin.maps.shared.ui.AbstractLayerState;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public class WMSLayerState extends AbstractLayerState {
	{
		primaryStyleName = "v-wmslayer";
	}

	public boolean singleTile = true;
}
