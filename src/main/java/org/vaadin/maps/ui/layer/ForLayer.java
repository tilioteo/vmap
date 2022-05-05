package org.vaadin.maps.ui.layer;

import org.vaadin.maps.server.Bounds;
import org.vaadin.maps.server.ViewWorldTransform;
import org.vaadin.maps.server.ViewWorldTransform.TransformChangeListener;

/**
 * @author Kamil Morong
 */
public interface ForLayer {

    String getCRS();

    Bounds getExtent();

    ViewWorldTransform getViewWorldTransform();

    void addTransformChangeListener(TransformChangeListener listener);

    void removeTransformChangeListener(TransformChangeListener listener);

}
