package org.vaadin.maps.shared.ui.control;

import org.vaadin.maps.shared.ui.AbstractComponentContainerState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractControlState extends AbstractComponentContainerState {
    public boolean active = false;

    {
        primaryStyleName = "v-control";
    }

}
