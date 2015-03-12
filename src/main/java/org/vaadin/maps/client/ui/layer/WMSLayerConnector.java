/**
 * 
 */
package org.vaadin.maps.client.ui.layer;

import org.vaadin.maps.client.ui.AbstractLayerConnector;
import org.vaadin.maps.client.ui.VWMSLayer;
import org.vaadin.maps.client.ui.VWMSLayer.RequestSingleTileEvent;
import org.vaadin.maps.client.ui.VWMSLayer.RequestSingleTileHandler;
import org.vaadin.maps.shared.ui.layer.WMSLayerServerRpc;
import org.vaadin.maps.shared.ui.layer.WMSLayerState;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;

/**
 * @author kamil
 *
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.maps.ui.layer.WMSLayer.class)
public class WMSLayerConnector extends AbstractLayerConnector implements RequestSingleTileHandler {

	@Override
	protected void init() {
		super.init();
		
		getWidget().addRequestSingleTileHandler(this);
	}

	@Override
	public VWMSLayer getWidget() {
		return (VWMSLayer) super.getWidget();
	}

	@Override
	public WMSLayerState getState() {
		return (WMSLayerState) super.getState();
	}


	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		
		if (stateChangeEvent.hasPropertyChanged("singleTile")) {
			getWidget().setSingleTile(getState().singleTile);
		}
	}

	@Override
	public void onConnectorHierarchyChange(
			ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        // We always have 1 child, unless the child is hidden
        Widget content = getContentWidget();
        if (content != null) {
        	getWidget().setWidget(content);
        }
	}

	@Override
	public void requestSingleTile(RequestSingleTileEvent event) {
		getRpcProxy(WMSLayerServerRpc.class).requestSingleTile(event.getWidth(), event.getHeight(), event.getShiftX(), event.getShiftY());
	}

}
