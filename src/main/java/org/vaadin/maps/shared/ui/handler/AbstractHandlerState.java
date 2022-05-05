package org.vaadin.maps.shared.ui.handler;

import com.vaadin.shared.AbstractComponentState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractHandlerState extends AbstractComponentState {
    public boolean active = false;

    {
        primaryStyleName = "v-handler";
    }

}
