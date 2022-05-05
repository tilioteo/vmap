package org.vaadin.maps.client.ui;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;

/**
 * @author Kamil Morong
 */
public abstract class AbstractLayerConnector extends AbstractSingleComponentContainerConnector {

    @Override
    public boolean delegateCaptionHandling() {
        return false;
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
        // nop
    }

}
