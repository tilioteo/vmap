package org.vaadin.maps.client.ui.handler;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.shared.ui.Connect;
import org.vaadin.maps.client.ui.VPathHandler;
import org.vaadin.maps.shared.ui.handler.PathHandlerState;

/**
 * @author Kamil Morong
 */
@Connect(org.vaadin.maps.ui.handler.PathHandler.class)
public class PathHandlerConnector extends PointHandlerConnector {

    @Override
    public VPathHandler getWidget() {
        return (VPathHandler) super.getWidget();
    }

    @Override
    public PathHandlerState getState() {
        return (PathHandlerState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("strategy")) {
            getWidget().setStrategyFromCode(getState().strategy);
        }
    }

}
