package org.vaadin.maps.client.ui.handler;

import com.vaadin.client.communication.StateChangeEvent;
import org.vaadin.maps.client.ui.AbstractDrawFeatureHandler;
import org.vaadin.maps.shared.ui.handler.FeatureHandlerState;

/**
 * @author Kamil Morong
 */
public class FeatureHandlerConnector extends AbstractHandlerConnector {

    @Override
    public FeatureHandlerState getState() {
        return (FeatureHandlerState) super.getState();
    }

    @Override
    public AbstractDrawFeatureHandler getWidget() {
        return (AbstractDrawFeatureHandler) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("frozen")) {
            getWidget().setFrozen(getState().frozen);
        }

    }

}
