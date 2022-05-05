package org.vaadin.maps.client.ui;

import com.vaadin.client.ui.AbstractComponentContainerConnector;
import org.vaadin.maps.shared.ui.AbstractLayoutState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractLayoutConnector extends AbstractComponentContainerConnector {

    @Override
    public AbstractLayoutState getState() {
        return (AbstractLayoutState) super.getState();
    }
}
