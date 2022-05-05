package org.vaadin.maps.ui.layer;

import com.vaadin.ui.Component;
import org.vaadin.maps.shared.ui.AbstractLayerState;
import org.vaadin.maps.ui.AbstractSingleComponentContainer;

/**
 * @author Kamil Morong
 */
public abstract class AbstractLayer<C extends Component> extends AbstractSingleComponentContainer<C> implements Layer {

    private ForLayer forLayer = null;

    @Override
    protected AbstractLayerState getState() {
        return (AbstractLayerState) super.getState();
    }

    public ForLayer getForLayer() {
        return forLayer;
    }

    public void setForLayer(ForLayer forLayer) {
        this.forLayer = forLayer;
    }
}
