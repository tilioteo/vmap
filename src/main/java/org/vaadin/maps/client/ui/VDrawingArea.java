/**
 * 
 */
package org.vaadin.maps.client.ui;

import org.vaadin.gwtgraphics.client.Drawing;
import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author Kamil Morong
 *
 */
public class VDrawingArea extends DrawingArea {

	public static final String CLASSNAME = "v-drawingarea";

	/**
	 * element created by drawing implementation (svg or vml)
	 */
	private Element drawingElement;

	public VDrawingArea() {
		super(0, 0);

		initDrawingElement();

		setStyleName(CLASSNAME);
	}

	@Override
	public Class<? extends Drawing> getType() {
		return null;
	}

	private void initDrawingElement() {
		drawingElement = getImpl().createElement(super.getType());
		super.getElement().appendChild(drawingElement);
	}

	@Override
	public com.google.gwt.user.client.Element getElement() {
		return DOM.asOld(drawingElement);
	}

	/**
	 * gets the div element of widget
	 * 
	 * @return {@link Element}
	 */
	public Element getContainerElement() {
		return super.getElement();
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
	}

	@Override
	public void setHeight(String height) {
		super.setHeight(height);
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
	}
}
