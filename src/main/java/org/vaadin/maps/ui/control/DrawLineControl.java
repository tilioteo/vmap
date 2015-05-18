/**
 * 
 */
package org.vaadin.maps.ui.control;

import org.vaadin.maps.shared.ui.Style;
import org.vaadin.maps.shared.ui.control.DrawPathControlState;
import org.vaadin.maps.ui.StyleUtility;
import org.vaadin.maps.ui.handler.LineHandler;
import org.vaadin.maps.ui.layer.VectorFeatureLayer;

/**
 * @author kamil
 *
 */
@SuppressWarnings("serial")
public class DrawLineControl extends DrawFeatureControl<LineHandler> {

	private Style startPointStyle = null;
	private Style lineStyle = null;

	public DrawLineControl(VectorFeatureLayer layer) {
		super(layer);

		setStartPointStyle(Style.DEFAULT_DRAW_START_POINT);
		setLineStyle(Style.DEFAULT_DRAW_LINE);
	}

	@Override
	protected DrawPathControlState getState() {
		return (DrawPathControlState) super.getState();
	}
	
	public Style getStartPointStyle() {
		return startPointStyle;
	}
	
	public void setStartPointStyle(Style style) {
		this.startPointStyle = style;
		getState().startPointStyle = StyleUtility.getStyleMap(style);
		markAsDirty();
	}
	
	public Style getLineStyle() {
		return lineStyle;
	}
	
	public void setLineStyle(Style style) {
		this.lineStyle = style;
		getState().lineStyle = StyleUtility.getStyleMap(style);
		markAsDirty();
	}
	
}
