/*
 * Copyright 2011 Henri Kerola
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.gwtgraphics.client;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;

/**
 * The following example shows how a DrawingArea instance is created and added
 * to a GWT application. A rectangle is added to this DrawingArea instance. When
 * the user clicks this rectangle its color changes.
 * 
 * <pre>
 * DrawingArea canvas = new DrawingArea(200, 200);
 * RootPanel.get().add(canvas);
 * 
 * Rectangle rect = new Rectangle(10, 10, 100, 50);
 * canvas.add(rect);
 * rect.setFillColor(&quot;blue&quot;);
 * rect.addClickHandler(new ClickHandler() {
 * 	public void onClick(ClickEvent event) {
 * 		Rectangle rect = (Rectangle) event.getSource();
 * 		if (rect.getFillColor().equals(&quot;blue&quot;)) {
 * 			rect.setFillColor(&quot;red&quot;);
 * 		} else {
 * 			rect.setFillColor(&quot;blue&quot;);
 * 		}
 * 	}
 * });
 * </pre>
 * 
 * 
 * @author Henri Kerola
 * @author Kamil Morong
 * 
 */
public class DrawingArea extends AbstractDrawingContainer
		implements Sizeable, HasClickHandlers, HasAllMouseHandlers, HasDoubleClickHandlers {

	/**
	 * Creates a DrawingArea of given width and height.
	 * 
	 * @param width
	 *            the width of DrawingArea in pixels
	 * @param height
	 *            the height of DrawingArea in pixels
	 */
	public DrawingArea(int width, int height) {
		super();
		getImpl().initCanvasSize(getElement(), width, height);
	}

	@Override
	public Class<? extends Drawing> getType() {
		return DrawingArea.class;
	}

	/**
	 * Returns a String that indicates what graphics renderer is used. This
	 * String is "VML" for Internet Explorer and "SVG" for other browsers.
	 * 
	 * @return the used graphics renderer
	 */
	public String getRendererString() {
		return getImpl().getRendererString();
	}

	/**
	 * Returns the width of the DrawingArea in pixels.
	 * 
	 * @return the width of the DrawingArea in pixels.
	 */
	@Override
	public int getWidth() {
		return getImpl().getWidth(getElement());
	}

	/**
	 * Sets the width of the DrawingArea in pixels.
	 * 
	 * @param width
	 *            the new width in pixels
	 */
	@Override
	public void setWidth(int width) {
		getImpl().setWidth(getElement(), width);
	}

	/**
	 * Returns the height of the DrawingArea in pixels.
	 * 
	 * @return the height of the DrawingArea in pixels.
	 */
	@Override
	public int getHeight() {
		return getImpl().getHeight(getElement());
	}

	/**
	 * Sets the height of the DrawingArea in pixels.
	 * 
	 * @param height
	 *            the new height
	 */
	@Override
	public void setHeight(int height) {
		getImpl().setHeight(getElement(), height);
	}

	@Override
	public void setHeight(String height) {
		getImpl().setAreaHeight(getElement(), height);
	}

	@Override
	public void setWidth(String width) {
		getImpl().setAreaWidth(getElement(), width);
	}

}
