package org.vaadin.maps.ui.feature;

import com.vaadin.ui.AbstractComponent;
import org.vaadin.maps.shared.ui.feature.AbstractFeatureState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractFeature extends AbstractComponent implements Feature {

    @Override
    protected AbstractFeatureState getState() {
        return (AbstractFeatureState) super.getState();
    }

}
