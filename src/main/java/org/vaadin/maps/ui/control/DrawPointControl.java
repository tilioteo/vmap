/**
 * 
 */
package org.vaadin.maps.ui.control;

import org.vaadin.maps.shared.ui.control.DrawPointControlState;
import org.vaadin.maps.ui.handler.PointHandler;
import org.vaadin.maps.ui.layer.VectorFeatureLayer;

/**
 * @author kamil
 *
 */
@SuppressWarnings("serial")
public class DrawPointControl extends DrawFeatureControl<PointHandler> {

	public DrawPointControl(VectorFeatureLayer layer) {
		super(layer);
	}

	@Override
	protected DrawPointControlState getState() {
		return (DrawPointControlState) super.getState();
	}
}
