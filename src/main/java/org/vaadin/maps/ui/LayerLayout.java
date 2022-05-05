package org.vaadin.maps.ui;

import com.vaadin.shared.Connector;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Component;
import org.vaadin.maps.event.LayoutEvents.LayoutClickEvent;
import org.vaadin.maps.event.LayoutEvents.LayoutClickListener;
import org.vaadin.maps.event.LayoutEvents.LayoutClickNotifier;
import org.vaadin.maps.shared.ui.layerlayout.LayerLayoutServerRpc;
import org.vaadin.maps.shared.ui.layerlayout.LayerLayoutState;
import org.vaadin.maps.ui.layer.Layer;

import java.io.Serializable;
import java.util.*;

/**
 * @author Kamil Morong
 */
public class LayerLayout extends AbstractLayout<Layer> implements LayoutClickNotifier<Layer> {

    // Maps each layer to a position
    private final LinkedHashMap<Layer, LayerOrder> layerToOrder = new LinkedHashMap<>();
    private int measuredWidth = -1;
    private int measuredHeight = -1;
    private final LayerLayoutServerRpc rpc = new LayerLayoutServerRpc() {

        @Override
        public void layoutClick(long timestamp, MouseEventDetails mouseDetails, Connector clickedConnector) {
            fireEvent(LayoutClickEvent.createEvent(timestamp, LayerLayout.this, mouseDetails, clickedConnector));
        }

        @Override
        public void updateMeasuredSize(int newWidth, int newHeight) {
            LayerLayout.this.updateMeasuredSize(newWidth, newHeight);
        }
    };

    /**
     * Creates an LayerLayout with full size.
     */
    public LayerLayout() {
        registerRpc(rpc);
        setSizeFull();
    }

    @Override
    protected LayerLayoutState getState() {
        return (LayerLayoutState) super.getState();
    }

    /**
     * Gets an iterator for going through all layers enclosed in the layer
     * layout.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Component> iterator() {
        return (Iterator<Component>) ((Set<?>) layerToOrder.keySet()).iterator();
    }

    /**
     * Gets an iterator for going through all layers enclosed in the layer
     * layout.
     */
    @Override
    public Iterator<Layer> typedIterator() {
        return layerToOrder.keySet().iterator();
    }

    /**
     * Gets the number of contained layers. Consistent with the iterator
     * returned by {@link #iterator()}.
     *
     * @return the number of contained components
     */
    @Override
    public int getComponentCount() {
        return layerToOrder.size();
    }

    /**
     * Replaces one layer with another one. The new layer inherits the old
     * layers position.
     */
    @Override
    public void replaceComponent(Layer oldLayer, Layer newLayer) {
        LayerOrder order = getOrder(oldLayer);
        removeComponent(oldLayer);
        addLayer(newLayer, order);
    }

    @Override
    public void addComponent(Layer layer) {
        layer.setSizeFull();
        addLayer(layer, new LayerOrder(getComponentCount() + 1));
    }

    /**
     * Adds the layer using the given position. Ensures the position is only set
     * if the component is added correctly.
     *
     * @param layer The layer to add
     * @param order The position info for the component. Must not be null.
     * @throws IllegalArgumentException If adding the component failed
     */
    private void addLayer(Layer layer, LayerOrder order) throws IllegalArgumentException {
        /*
         * Create position instance and add it to layerToCoordinates map. We
         * need to do this before we call addComponent so the attachListeners
         * can access this position. #6368
         */
        internalSetOrder(layer, order);
        try {
            super.addComponent(layer);
        } catch (IllegalArgumentException e) {
            internalRemoveLayer(layer);
            throw e;
        }
        markAsDirty();
    }

    /**
     * Removes the layer from all internal data structures. Does not actually
     * remove the layer from the layout (this is assumed to have been done by
     * the caller).
     *
     * @param layer The layer to remove
     */
    private void internalRemoveLayer(Layer layer) {
        layerToOrder.remove(layer);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        // This could be in internalRemoveLayer and internalSetComponent if
        // Map<Connector,String> was supported. We cannot get the child
        // connectorId unless the component is attached to the application so
        // the String->String map cannot be populated in internal* either.
        Map<String, String> connectorToPosition = new HashMap<String, String>();
        for (Iterator<Layer> li = typedIterator(); li.hasNext(); ) {
            Layer l = li.next();
            connectorToPosition.put(l.getConnectorId(), getOrder(l).getCSSString());
        }
        getState().connectorToCssPosition = connectorToPosition;

    }

    @Override
    public void removeComponent(Layer layer) {
        internalRemoveLayer(layer);
        super.removeComponent(layer);
        markAsDirty();
    }

    /**
     * Gets the position of a layer in the layout. Returns null if layer is not
     * attached to the layout.
     * <p>
     * Note that you cannot update the position by updating this object. Call
     * {@link #setOrder(Layer, LayerOrder)} with the updated {@link LayerOrder}
     * object.
     * </p>
     *
     * @param layer The layer which position is needed
     * @return An instance of LayerPosition containing the position of the
     * layer, or null if the layer is not enclosed in the layout.
     */
    protected LayerOrder getOrder(Layer layer) {
        return layerToOrder.get(layer);
    }

    /**
     * Sets the position of a layer in the layout.
     *
     * @param layer
     * @param order
     */
    protected void setOrder(Layer layer, LayerOrder order) {
        if (!layerToOrder.containsKey(layer)) {
            throw new IllegalArgumentException("Layer must be a child of this layout");
        }
        internalSetOrder(layer, order);
    }

    /**
     * Updates the position for a layer. Caller must ensure layer is a child of
     * this layout.
     *
     * @param layer The layer. Must be a child for this layout. Not enforced.
     * @param order New position. Must not be null.
     */
    private void internalSetOrder(Layer layer, LayerOrder order) {
        // TODO check layer isBase and set proper z-index

        layerToOrder.put(layer, order);
        markAsDirty();
    }

    protected void updateMeasuredSize(int newWidth, int newHeight) {
        int oldWidth = this.measuredWidth;
        int oldHeight = this.measuredHeight;

        this.measuredWidth = newWidth;
        this.measuredHeight = newHeight;

        for (Component layer : this) {
            if (layer instanceof MeasuredSizeHandler) {
                ((MeasuredSizeHandler) layer).sizeChanged(oldWidth, oldHeight, newWidth, newHeight);
            }
        }
    }

    protected int getMeasuredWidth() {
        return measuredWidth;
    }

    protected int getMeasuredHeight() {
        return measuredHeight;
    }

    @Override
    public void addLayoutClickListener(LayoutClickListener<Layer> listener) {
        addListener(EventId.LAYOUT_CLICK_EVENT_IDENTIFIER, LayoutClickEvent.class, listener,
                LayoutClickListener.clickMethod);
    }

    @Override
    public void removeLayoutClickListener(LayoutClickListener<Layer> listener) {
        removeListener(EventId.LAYOUT_CLICK_EVENT_IDENTIFIER, LayoutClickEvent.class, listener);
    }

    /**
     * The LayerPosition class represents a layer position within the layer
     * layout. It contains the attributes for left, right, top and bottom and
     * the units used to specify them.
     */
    public class LayerOrder implements Serializable {

        private int zIndex;

        public LayerOrder(int zIndex) {
            this.zIndex = zIndex;
        }

        /**
         * Converts the internal values into a valid CSS string.
         *
         * @return A valid CSS string
         */
        public String getCSSString() {
            String s = "";

            s += "position:absolute;top:0px;left:0px";
            if (zIndex >= 0) {
                s += "z-index:" + zIndex + ";";
            }
            return s;
        }

        /**
         * Gets the 'z-index' attribute.
         *
         * @return the zIndex The z-index attribute
         */
        public int getZIndex() {
            return zIndex;
        }

        /**
         * Sets the 'z-index' attribute; the visual stacking order
         *
         * @param zIndex The z-index for the component.
         */
        public void setZIndex(int zIndex) {
            this.zIndex = zIndex;
            markAsDirty();
        }

        @Override
        public String toString() {
            return getCSSString();
        }

    }

}
