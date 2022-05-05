package org.vaadin.maps.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author Kamil Morong
 */
public interface Tile extends IsWidget, CanShift {

    int getWidth();

    int getHeight();

    interface SizeChangeHandler {
        void onSizeChange(Tile tile, int oldWidth, int oldHeight, int newWidth, int newHeight);
    }

    interface TileLoadHandler {
        void onLoad(Tile tile);
    }
}
