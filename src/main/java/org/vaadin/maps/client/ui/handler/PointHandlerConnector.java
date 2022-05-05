package org.vaadin.maps.client.ui.handler;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.Connector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.DateUtility;
import org.vaadin.maps.client.drawing.Utils;
import org.vaadin.maps.client.ui.VPointHandler;
import org.vaadin.maps.client.ui.VPointHandler.GeometryEvent;
import org.vaadin.maps.client.ui.VPointHandler.GeometryEventHandler;
import org.vaadin.maps.client.ui.VPointHandler.SyntheticClickHandler;
import org.vaadin.maps.client.ui.layer.VectorFeatureLayerConnector;
import org.vaadin.maps.shared.ui.handler.PointHandlerServerRpc;
import org.vaadin.maps.shared.ui.handler.PointHandlerState;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.handler.PointHandler.class)
public class PointHandlerConnector extends FeatureHandlerConnector
        implements SyntheticClickHandler, GeometryEventHandler {

    @Override
    protected void init() {
        super.init();

        getWidget().clickHandlerSlave = this;
        getWidget().addGeometryEventHandler(this);
    }

    @Override
    public VPointHandler getWidget() {
        return (VPointHandler) super.getWidget();
    }

    @Override
    public PointHandlerState getState() {
        return (PointHandlerState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("layer")) {
            Connector connector = getState().layer;
            if (connector != null) {
                if (connector instanceof VectorFeatureLayerConnector)
                    getWidget().setLayer(((VectorFeatureLayerConnector) connector).getWidget());
            } else
                getWidget().setLayer(null);
        }
    }

    @Override
    public void syntheticClick(MouseEventDetails details, Element relativeElement) {
        final int[] mouseEventXY = VPointHandler.getMouseEventXY(details, relativeElement);
        details.setRelativeX(mouseEventXY[0]);
        details.setRelativeX(mouseEventXY[1]);
        getRpcProxy(PointHandlerServerRpc.class).click(DateUtility.getTimestamp(), details);
    }

    @Override
    public void geometry(GeometryEvent event) {
        getRpcProxy(PointHandlerServerRpc.class).geometry(DateUtility.getTimestamp(),
                Utils.GeometryToWKBHex(event.getGeometry()));
    }

}
