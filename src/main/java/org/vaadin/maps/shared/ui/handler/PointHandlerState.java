/**
 * 
 */
package org.vaadin.maps.shared.ui.handler;

import com.vaadin.shared.Connector;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public class PointHandlerState extends FeatureHandlerState {
	{
		primaryStyleName = "v-pointhandler";
	}

	public Connector layer = null;
}
