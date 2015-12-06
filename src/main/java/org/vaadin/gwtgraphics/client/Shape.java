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

/**
 * Shape is an abstract upper-class for VectorObjects that support filling,
 * stroking and positioning.
 * 
 * @author Henri Kerola
 * 
 */
public abstract class Shape extends AbstractDrawing implements Strokeable, Positionable, Animatable {

	/**
	 * This constructor defines initial fill and stroke properties, which are
	 * common for all subclasses. These properties are:
	 * 
	 * <pre>
	 * setFillColor(&quot;white&quot;);
	 * setFillOpacity(1);
	 * setStrokeColor(&quot;black&quot;);
	 * setStrokeOpacity(1);
	 * setStrokeWidth(1);
	 * </pre>
	 */
	public Shape() {
		setFillColor("white");
		setFillOpacity(1);
		setStrokeColor("black");
		setStrokeOpacity(1);
		setStrokeWidth(1);
	}

	@Override
	public int getX() {
		return getImpl().getX(getElement());
	}

	@Override
	public void setX(int x) {
		getImpl().setX(getElement(), x, isAttached());
	}

	@Override
	public int getY() {
		return getImpl().getY(getElement());
	}

	@Override
	public void setY(int y) {
		getImpl().setY(getElement(), y, isAttached());
	}

	/**
	 * Returns the current fill color of the element.
	 */
	public String getFillColor() {
		return getImpl().getFillColor(getElement());
	}

	/**
	 * Sets the fill color of the element. The color value is specified using
	 * one of the CSS2 color notations. For example, the following values are
	 * legal:
	 * <ul>
	 * <li>red
	 * <li>#ff0000
	 * <li>#f00
	 * <li>rgb(255, 0, 0)
	 * <li>rgb(100%, 0%, 0%)
	 * </ul>
	 * 
	 * Setting the color to null disables elements filling.
	 * 
	 * @see <a href="http://www.w3.org/TR/CSS2/syndata.html#value-def-color">
	 *      http://www.w3.org/TR/CSS2/syndata.html#value-def-color</a>
	 * @param color
	 *            the new fill color
	 */
	public void setFillColor(String color) {
		getImpl().setFillColor(getElement(), color);
	}

	/**
	 * Returns the fill opacity of the Shape element.
	 * 
	 * @return the current fill opacity
	 */
	public double getFillOpacity() {
		return getImpl().getFillOpacity(getElement());
	}

	/**
	 * Sets the fill opacity of the Shape element. The initial value 1.0 means
	 * fully opaque fill. On the other hand, value 0.0 means fully transparent
	 * paint.
	 * 
	 * @param opacity
	 *            the new fill opacity
	 */
	public void setFillOpacity(double opacity) {
		getImpl().setFillOpacity(getElement(), opacity);
	}

	/**
	 * Returns the opacity of the Shape element.
	 * 
	 * @return the current opacity
	 */
	public double getOpacity() {
		return getImpl().getOpacity(getElement());
	}

	/**
	 * Sets the opacity of the Shape element. The initial value 1.0 means fully
	 * opaque shape. On the other hand, value 0.0 means fully transparent paint.
	 * 
	 * @param opacity
	 *            the new opacity
	 */
	public void setOpacity(double opacity) {
		getImpl().setOpacity(getElement(), opacity);
	}

	@Override
	public String getStrokeColor() {
		return getImpl().getStrokeColor(getElement());
	}

	@Override
	public void setStrokeColor(String color) {
		getImpl().setStrokeColor(getElement(), color);
	}

	@Override
	public int getStrokeWidth() {
		return getImpl().getStrokeWidth(getElement());
	}

	@Override
	public void setStrokeWidth(int width) {
		getImpl().setStrokeWidth(getElement(), width, isAttached());
	}

	@Override
	public double getStrokeOpacity() {
		return getImpl().getStrokeOpacity(getElement());
	}

	@Override
	public void setStrokeOpacity(double opacity) {
		getImpl().setStrokeOpacity(getElement(), opacity);
	}

	@Override
	public void setPropertyDouble(String property, double value) {
		property = property.toLowerCase();
		if ("x".equals(property)) {
			setX((int) value);
		} else if ("y".equals(property)) {
			setY((int) value);
		} else if ("fillopacity".equals(property)) {
			setFillOpacity(value);
		} else if ("strokeopacity".equals(property)) {
			setStrokeOpacity(value);
		} else if ("strokewidth".equals(property)) {
			setStrokeWidth((int) value);
		} else if ("opacity".equals(property)) {
			setOpacity(value);
		} else if ("rotation".equals(property)) {
			setRotation((int) value);
		}
	}
}
