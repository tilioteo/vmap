package org.vaadin.maps.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.HashMap;

/**
 * @author Kamil Morong
 */
public class VMapContainer extends VLayerLayout {

    private final HashMap<PanEventHandler, HandlerRegistration> panEventHandlerMap = new HashMap<>();
    private final HashMap<ZoomEventHandler, HandlerRegistration> zoomEventHandlerMap = new HashMap<>();

    @Override
    public void onPanEnd(int totalX, int totalY) {
        fireEvent(new PanEvent(this, totalX, totalY));

        super.onPanEnd(totalX, totalY);
    }

    @Override
    public void onZoom(double zoom) {
        fireEvent(new ZoomEvent(this, zoom));

        super.onZoom(zoom);
    }

    public void addPanEventHandler(PanEventHandler handler) {
        panEventHandlerMap.put(handler, addHandler(handler, PanEvent.TYPE));
    }

    public void removePanEventHandler(PanEventHandler handler) {
        if (panEventHandlerMap.containsKey(handler)) {
            removeHandler(panEventHandlerMap.get(handler));
            panEventHandlerMap.remove(handler);
        }
    }

    public void addZoomEventHandler(ZoomEventHandler handler) {
        zoomEventHandlerMap.put(handler, addHandler(handler, ZoomEvent.TYPE));
    }

    public void removeZoomEventHandler(ZoomEventHandler handler) {
        if (zoomEventHandlerMap.containsKey(handler)) {
            removeHandler(zoomEventHandlerMap.get(handler));
            zoomEventHandlerMap.remove(handler);
        }
    }

    protected final void removeHandler(HandlerRegistration handler) {
        if (handler != null) {
            handler.removeHandler();
        }
    }

    public interface PanEventHandler extends EventHandler {
        void onPanEnd(PanEvent event);
    }

    public interface ZoomEventHandler extends EventHandler {
        void onZoom(ZoomEvent event);
    }

    public static class PanEvent extends GwtEvent<PanEventHandler> {

        public static final Type<PanEventHandler> TYPE = new Type<>();

        private final int shiftX;
        private final int shiftY;

        public PanEvent(VMapContainer source, int shiftX, int shiftY) {
            setSource(source);
            this.shiftX = shiftX;
            this.shiftY = shiftY;
        }

        public int getShiftX() {
            return shiftX;
        }

        public int getShiftY() {
            return shiftY;
        }

        @Override
        public Type<PanEventHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(PanEventHandler handler) {
            handler.onPanEnd(this);
        }

    }

    public static class ZoomEvent extends GwtEvent<ZoomEventHandler> {

        public static final Type<ZoomEventHandler> TYPE = new Type<>();

        private final double zoom;

        public ZoomEvent(VMapContainer source, double zoom) {
            setSource(source);
            this.zoom = zoom;
        }

        public double getZoom() {
            return zoom;
        }

        @Override
        public Type<ZoomEventHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(ZoomEventHandler handler) {
            handler.onZoom(this);
        }

    }

}
