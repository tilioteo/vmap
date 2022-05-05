package org.vaadin.maps.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import org.vaadin.maps.client.ui.VGridLayout.GridWrapper;

import java.util.HashMap;

/**
 * @author Kamil Morong
 */
public class VWMSLayer extends InteractiveLayer {

    /**
     * Class name, prefix in styling
     */
    public static final String CLASSNAME = "v-wmslayer";

    private final HashMap<RequestSingleTileHandler, HandlerRegistration> requestSingleTileHandlerMap = new HashMap<>();

    protected boolean base = true;
    protected boolean singleTile = true;

    public VWMSLayer() {
        super();
        setStylePrimaryName(CLASSNAME);
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public boolean isSingleTile() {
        return singleTile;
    }

    public void setSingleTile(boolean singleTile) {
        this.singleTile = singleTile;
    }

    @Override
    public void onSizeChange(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        Widget child = getWidget();
        if (child instanceof VGridLayout) {
            VGridLayout gridLayout = (VGridLayout) child;
            gridLayout.setMeasuredSize(newWidth, newHeight);

            if (singleTile) {
                // center tile
                Widget tileWidget = gridLayout.getWidget(0);
                GridWrapper wrapper = gridLayout.getChildWrapper(tileWidget);
                if (tileWidget instanceof Tile) {
                    Tile tile = (Tile) tileWidget;
                    int dx = wrapper.getLeft();
                    int dy = wrapper.getTop();
                    if (dx == 0) {
                        dx = (newWidth - tile.getWidth()) / 2;
                    }
                    if (dy == 0) {
                        dy = (newHeight - tile.getHeight()) / 2;
                    }
                    gridLayout.setWidgetPosition(tileWidget, dx, dy);

                    int shiftX = gridLayout.getShiftX();
                    int shiftY = gridLayout.getShiftY();
                    // check if there is uncovered place of visible area
                    if (Math.abs(shiftX) > Math.abs(dx) || Math.abs(shiftY) > Math.abs(dy)) {
                        // new tile needed
                        clearShift();
                        fireEvent(new RequestSingleTileEvent(this, newWidth, newHeight, shiftX, shiftY));
                    }
                }
            }
        }
    }

    @Override
    public void onPanEnd(int totalX, int totalY) {
        super.onPanEnd(totalX, totalY);

        if (!fixed) {
            Widget child = getWidget();
            if (child instanceof VGridLayout) {
                VGridLayout gridLayout = (VGridLayout) child;

                if (singleTile) {
                    Widget tileWidget = gridLayout.getWidget(0);
                    if (tileWidget instanceof Tile) {
                        Tile tile = (Tile) tileWidget;
                        GridWrapper wrapper = gridLayout.getChildWrapper(tileWidget);
                        if (wrapper != null) {
                            int dx = wrapper.getLeft();
                            int dy = wrapper.getTop();
                            int shiftX = gridLayout.getShiftX();
                            int shiftY = gridLayout.getShiftY();
                            if (Math.abs(shiftX) > Math.abs(dx) || Math.abs(shiftY) > Math.abs(dy)) {
                                // new tile needed
                                clearShift();
                                fireEvent(new RequestSingleTileEvent(this, tile.getWidth(), tile.getHeight(), shiftX,
                                        shiftY));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onZoom(double zoom) {
        super.onZoom(zoom);

        if (!fixed) {
            if (singleTile) {
                clearShift();
                fireEvent(new RequestSingleTileEvent(this, 0, 0, 0, 0));
            }
        }
    }

    public void addRequestSingleTileHandler(RequestSingleTileHandler handler) {
        requestSingleTileHandlerMap.put(handler, addHandler(handler, RequestSingleTileEvent.TYPE));
    }

    public void removeRequestSingleTileHandler(RequestSingleTileHandler handler) {
        if (requestSingleTileHandlerMap.containsKey(handler)) {
            removeHandler(requestSingleTileHandlerMap.get(handler));
            requestSingleTileHandlerMap.remove(handler);
        }
    }

    public interface RequestSingleTileHandler extends EventHandler {
        void requestSingleTile(RequestSingleTileEvent event);
    }

    public static class RequestSingleTileEvent extends GwtEvent<RequestSingleTileHandler> {

        public static final Type<RequestSingleTileHandler> TYPE = new Type<>();

        private final int width;
        private final int height;
        private final int shiftX;
        private final int shiftY;

        public RequestSingleTileEvent(VWMSLayer source, int width, int height, int shiftX, int shiftY) {
            setSource(source);
            this.width = 0;
            this.height = 0;
            this.shiftX = shiftX;
            this.shiftY = shiftY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getShiftX() {
            return shiftX;
        }

        public int getShiftY() {
            return shiftY;
        }

        @Override
        public Type<RequestSingleTileHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(RequestSingleTileHandler handler) {
            handler.requestSingleTile(this);
        }

    }

}
