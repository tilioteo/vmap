package org.vaadin.maps.ui.featurecontainer;

import com.tilioteo.common.event.MouseEvents;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;
import org.vaadin.maps.shared.ui.featurecontainer.VectorFeatureContainerServerRpc;
import org.vaadin.maps.ui.feature.VectorFeature;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Kamil Morong
 */
public class VectorFeatureContainer extends AbstractFeatureContainer<VectorFeature> {

    protected final VectorFeatureContainerServerRpc rpc = new VectorFeatureContainerServerRpc() {
        @Override
        public void click(long timestamp, MouseEventDetails mouseDetails) {
            fireEvent(new ClickEvent(timestamp, VectorFeatureContainer.this, mouseDetails));
        }
    };

    public VectorFeatureContainer() {
        registerRpc(rpc);
    }

    /**
     * Adds the container click listener.
     *
     * @param listener the Listener to be added.
     */
    public void addClickListener(ClickListener listener) {
        addListener(ClickEvent.class, listener, ClickListener.CONTAINER_CLICK_METHOD);
    }

    /**
     * Removes the container click listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removeClickListener(ClickListener listener) {
        removeListener(ClickEvent.class, listener, ClickListener.CONTAINER_CLICK_METHOD);
    }

    /**
     * Interface for listening for a {@link ClickEvent} fired by a
     * {@link VectorFeatureContainer}.
     */
    public interface ClickListener extends Serializable {

        Method CONTAINER_CLICK_METHOD = ReflectTools.findMethod(ClickListener.class, "click",
                ClickEvent.class);

        /**
         * Called when a {@link VectorFeatureContainer} has been clicked. A
         * reference to the container is given by
         * {@link ClickEvent#getContainer()}.
         *
         * @param event An event containing information about the click.
         */
        void click(ClickEvent event);

    }

    /**
     * Click event. This event is thrown, when the vector feature container is
     * clicked.
     */
    public static class ClickEvent extends MouseEvents.ClickEvent {

        /**
         * New instance of text change event.
         *
         * @param source the Source of the event.
         */
        public ClickEvent(long timestamp, Component source) {
            super(timestamp, source, null);
        }

        /**
         * Constructor with mouse details
         *
         * @param source  The source where the click took place
         * @param details Details about the mouse click
         */
        public ClickEvent(long timestamp, Component source, MouseEventDetails details) {
            super(timestamp, source, details);
        }

        /**
         * Gets the VectorFeatureContainer where the event occurred.
         *
         * @return the Source of the event.
         */
        public VectorFeatureContainer getContainer() {
            return (VectorFeatureContainer) getSource();
        }

    }

}
