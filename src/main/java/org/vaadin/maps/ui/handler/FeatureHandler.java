package org.vaadin.maps.ui.handler;

import com.tilioteo.common.event.TimekeepingComponentEvent;
import com.vaadin.util.ReflectTools;
import org.vaadin.maps.shared.ui.Style;
import org.vaadin.maps.shared.ui.handler.FeatureHandlerState;
import org.vaadin.maps.ui.CanFreeze;
import org.vaadin.maps.ui.control.Control;
import org.vaadin.maps.ui.feature.VectorFeature;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Kamil Morong
 */
public abstract class FeatureHandler extends AbstractHandler implements RequiresVectorFeatureLayer, CanFreeze {

    protected Style featureStyle = null;

    protected FeatureHandler(Control control) {
        super(control);
    }

    @Override
    protected FeatureHandlerState getState() {
        return (FeatureHandlerState) super.getState();
    }

    public Style getFeatureStyle() {
        return featureStyle;
    }

    public void setFeatureStyle(Style featureStyle) {
        this.featureStyle = featureStyle;
    }

    /**
     * Adds the draw feature listener.
     *
     * @param listener the Listener to be added.
     */
    public void addDrawFeatureListener(DrawFeatureListener listener) {
        addListener(DrawFeatureEvent.class, listener, DrawFeatureListener.DRAW_FEATURE_METHOD);
    }

    /**
     * Removes the draw feature listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removeDrawFeatureListener(DrawFeatureListener listener) {
        removeListener(DrawFeatureEvent.class, listener, DrawFeatureListener.DRAW_FEATURE_METHOD);
    }

    @Override
    public void freeze() {
        setFrozen(true);
    }

    @Override
    public void unfreeze() {
        setFrozen(false);
    }

    protected void setFrozen(boolean frozen) {
        getState().frozen = frozen;
    }

    /**
     * Interface for listening for a {@link DrawFeatureEvent} fired by a
     * {@link PointHandler}.
     */
    public interface DrawFeatureListener extends Serializable {

        Method DRAW_FEATURE_METHOD = ReflectTools.findMethod(DrawFeatureListener.class,
                "drawFeature", DrawFeatureEvent.class);

        /**
         * Called when a feature has been drawn.
         *
         * @param event An event containing information about the geometry.
         */
        void drawFeature(DrawFeatureEvent event);

    }

    /**
     * This event is thrown, when the geometry is drawn.
     */
    public static class DrawFeatureEvent extends TimekeepingComponentEvent {

        private final VectorFeature feature;

        public DrawFeatureEvent(long timestamp, PointHandler source, VectorFeature feature) {
            super(timestamp, source);
            this.feature = feature;
        }

        public VectorFeature getFeature() {
            return feature;
        }
    }

}
