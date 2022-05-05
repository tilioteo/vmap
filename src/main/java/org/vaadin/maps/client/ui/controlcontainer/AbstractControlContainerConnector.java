package org.vaadin.maps.client.ui.controlcontainer;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import org.vaadin.maps.shared.ui.controlcontainer.AbstractControlContainerState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractControlContainerConnector extends AbstractComponentContainerConnector {

    @Override
    public AbstractControlContainerState getState() {
        return (AbstractControlContainerState) super.getState();
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
        // nop
    }

}
