package org.vaadin.maps.client.ui.featurecontainer;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractComponentContainerConnector;
import org.vaadin.maps.shared.ui.featurecontainer.AbstractFeatureContainerState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractFeatureContainerConnector extends AbstractComponentContainerConnector {

    @Override
    public AbstractFeatureContainerState getState() {
        return (AbstractFeatureContainerState) super.getState();
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
        // nop
    }

}
