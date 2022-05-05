package org.vaadin.maps.ui;

import com.vaadin.ui.Component;
import org.vaadin.maps.shared.ui.AbstractLayoutState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractLayout<C extends Component> extends AbstractComponentContainer<C> implements Layout<C> {

    @Override
    protected AbstractLayoutState getState() {
        return (AbstractLayoutState) super.getState();
    }

}
