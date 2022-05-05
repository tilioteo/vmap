package org.vaadin.maps.client.ui;

/**
 * @author Kamil Morong
 */
public interface PanHandler {

    void onPanStep(int dX, int dY);

    void onPanEnd(int totalX, int totalY);

}
