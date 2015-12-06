/**
 * 
 */
package org.vaadin.maps.ui.layer;

import org.vaadin.maps.server.Bounds;
import org.vaadin.maps.server.ViewWorldTransform;
import org.vaadin.maps.server.ViewWorldTransform.TransformChangeListener;

/**
 * @author Kamil Morong
 *
 */
public interface ForLayer {

	public String getCRS();

	public Bounds getExtent();

	public ViewWorldTransform getViewWorldTransform();

	public void addTransformChangeListener(TransformChangeListener listener);

	public void removeTransformChangeListener(TransformChangeListener listener);

}
