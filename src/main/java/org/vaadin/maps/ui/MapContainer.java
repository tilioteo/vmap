package org.vaadin.maps.ui;

import org.vaadin.maps.server.Bounds;
import org.vaadin.maps.server.ViewWorldTransform;
import org.vaadin.maps.server.ViewWorldTransform.TransformChangeListener;
import org.vaadin.maps.shared.ui.Style;
import org.vaadin.maps.shared.ui.mapcontainer.MapContainerRpc;
import org.vaadin.maps.ui.control.AbstractControl;
import org.vaadin.maps.ui.layer.AbstractLayer;
import org.vaadin.maps.ui.layer.ControlLayer;
import org.vaadin.maps.ui.layer.ForLayer;

import java.util.HashMap;

/**
 * @author Kamil Morong
 */
public class MapContainer extends LayerLayout implements ForLayer {

    private final HashMap<String, Style> styles = new HashMap<>();

    private final ControlLayer controlLayer;
    private final ViewWorldTransform transform = new ViewWorldTransform();
    private final MapContainerRpc rpc = new MapContainerRpc() {
        @Override
        public void panEnd(int shiftX, int shiftY) {
            transform.shiftWorld(-shiftX, shiftY);
        }

        @Override
        public void zoom(double zoom) {
            transform.scaleWorld(zoom);
        }
    };
    private String crs = null;
    /**
     * initial bounds
     */
    private Bounds bounds = null;

    public MapContainer() {
        super();

        registerRpc(rpc);
        // Add default control layer
        controlLayer = new ControlLayer();
        addComponent(controlLayer);
    }

    public void addStyle(String id, Style style) {
        if (id != null && style != null) {
            styles.put(id, style);
        }
    }

    public void removeStyle(String id) {
        styles.remove(id);
        // TODO remove styling from objects
    }

    public Style getStyle(String id) {
        return styles.get(id);
    }

    public void addLayer(AbstractLayer<?> layer) {
        if (layer != null) {
            addComponent(layer);
            layer.setForLayer(this);
        }
    }

    public void removeLayer(AbstractLayer<?> layer) {
        if (layer != null) {
            removeComponent(layer);
            layer.setForLayer(null);
        }
    }

    public void addControl(AbstractControl control) {
        controlLayer.addComponent(control);
    }

    public void removeControl(AbstractControl control) {
        controlLayer.removeComponent(control);
    }

    @Override
    public String getCRS() {
        return crs;
    }

    public void setCRS(String crs) {
        if (crs != null && !crs.trim().isEmpty() && CRSUtility.checkCRS(crs.trim())) {
            this.crs = crs;
        } else {
            this.crs = null;
        }
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        if (bounds != null) {
            this.bounds = bounds;
        }

        transform.fitWorldToView(this.bounds);
    }

    @Override
    public Bounds getExtent() {
        return transform.getWorld();
    }

    @Override
    protected void updateMeasuredSize(int newWidth, int newHeight) {
        final boolean fit = !transform.getView().isValid();
        transform.resizeView(newWidth, newHeight);

        if (fit) {
            transform.fitWorldToView(bounds);
        }

        super.updateMeasuredSize(newWidth, newHeight);
    }

    @Override
    public ViewWorldTransform getViewWorldTransform() {
        return transform;
    }

    @Override
    public void addTransformChangeListener(TransformChangeListener listener) {
        transform.addTransformChangeListener(listener);
    }

    @Override
    public void removeTransformChangeListener(TransformChangeListener listener) {
        transform.removeTransformChangeListener(listener);
    }

}
