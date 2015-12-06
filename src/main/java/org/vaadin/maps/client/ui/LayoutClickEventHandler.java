/**
 * 
 */
package org.vaadin.maps.client.ui;

import org.vaadin.maps.client.DateUtility;
import org.vaadin.maps.shared.ui.LayoutClickRpc;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.ui.AbstractClickEventHandler;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;

/**
 * @author Kamil Morong
 *
 */
public abstract class LayoutClickEventHandler extends AbstractClickEventHandler {

	public LayoutClickEventHandler(ComponentConnector connector) {
		this(connector, EventId.LAYOUT_CLICK_EVENT_IDENTIFIER);
	}

	public LayoutClickEventHandler(ComponentConnector connector, String clickEventIdentifier) {
		super(connector, clickEventIdentifier);
	}

	protected abstract ComponentConnector getChildComponent(Element element);

	protected ComponentConnector getChildComponent(NativeEvent event) {
		return getChildComponent((Element) event.getEventTarget().cast());
	}

	@Override
	protected void fireClick(NativeEvent event) {
		MouseEventDetails mouseDetails = MouseEventDetailsBuilder.buildMouseEventDetails(event, getRelativeToElement());
		getLayoutClickRPC().layoutClick(DateUtility.getTimestamp(), mouseDetails, getChildComponent(event));
	}

	protected abstract LayoutClickRpc getLayoutClickRPC();
}
