package org.vaadin.maps.client.ui.handler;

import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VLineHandler;
import org.vaadin.maps.shared.ui.handler.LineHandlerState;

/**
 * @author Kamil Morong
 */
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
