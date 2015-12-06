/**
 * 
 */
package org.vaadin.maps.client.ui.handler;

import org.vaadin.maps.client.ui.VLineHandler;
import org.vaadin.maps.shared.ui.handler.LineHandlerState;

import com.vaadin.shared.ui.Connect;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.maps.ui.handler.LineHandler.class)
public class LineHandlerConnector extends PointHandlerConnector {

	@Override
	public VLineHandler getWidget() {
		return (VLineHandler) super.getWidget();
	}

	@Override
	public LineHandlerState getState() {
		return (LineHandlerState) super.getState();
	}

}
