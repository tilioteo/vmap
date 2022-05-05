package org.vaadin.maps.ui.tile;

/**
 * @author Kamil Morong
 */
public interface ClippedSizeHandler {

    void onSizeChange(int oldWidth, int oldHeight, int newWidth, int newHeight);

}
