package org.vaadin.maps.client.ui;

/**
 * @author Kamil Morong
 */
public interface SizeChangeHandler {

    void onSizeChange(int oldWidth, int oldHeight, int newWidth, int newHeight);
}
