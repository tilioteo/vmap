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

import org.vaadin.gwtgraphics.client.animation.Animatable;

/**
 * Image represents a raster image that can be embedded into DrawingArea.
 * 
 * @author Henri Kerola
 * 
 */
public class Image extends AbstractDrawing implements Sizeable, Positionable,
		Animatable {

	/**
	 * Create a new Image with the given properties.
	 * 
	 * @param x
	 *            the x-coordinate position of the top-left corner of the image
	 *            in pixels
	 * @param y
	 *            the y-coordinate position of the top-left corner of the image
	 *            in pixels
	 * @param width
	 *            the width of the image in pixels
	 * @param height
	 *            the height of the image in pixels
	 * @param href
	 *            URL to an image to be shown.
	 */
	public Image(int x, int y, int width, int height, String href) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setHref(href);
	}

	@Override
	protected Class<? extends AbstractDrawing> getType() {
		return Image.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Positionable#getX()
	 */
	public int getX() {
		return getImpl().getX(getElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Positionable#setX(int)
	 */
	public void setX(int x) {
		getImpl().setX(getElement(), x, isAttached());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Positionable#getY()
	 */
	public int getY() {
		return getImpl().getY(getElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vaadin.gwtgraphics.client.Positionable#setY(int)
	 */
	public void setY(int y) {
		getImpl().setY(getElement(), y, isAttached());
	}

	/**
	 * Returns the URL of the image currently shown.
	 * 
	 * @return URL of the image
	 */
	public String getHref() {
		return getImpl().getImageHref(getElement());
	}

	/**
	 * Sets the URL of the image to be shown.
	 * 
	 * @param href
	 *            URL of the image to be shown
	 */
	public void setHref(String href) {
		getImpl().setImageHref(getElement(), href);
	}

	/**
	 * Returns the width of the Image in pixels.
	 * 
	 * @return the width of the Image in pixels
	 */
	public int getWidth() {
		return getImpl().getWidth(getElement());
	}

	/**
	 * Sets the width of the Image in pixels.
	 * 
	 * @param width
	 *            the new width in pixels
	 */
	public void setWidth(int width) {
		getImpl().setWidth(getElement(), width);
	}

	@Override
	public void setWidth(String width) {
		boolean successful = false;
		if (width != null && width.endsWith("px")) {
			try {
				setWidth(Integer
						.parseInt(width.substring(0, width.length() - 2)));
				successful = true;
			} catch (NumberFormatException e) {
			}
		}
		if (!successful) {
			throw new IllegalArgumentException(
					"Only pixel units (px) are supported");
		}
	}

	/**
	 * Returns the height of the Image in pixels.
	 * 
	 * @return the height of the Image in pixels
	 */
	public int getHeight() {
		return getImpl().getHeight(getElement());
	}

	/**
	 * Sets the height of the Image in pixels.
	 * 
	 * @param height
	 *            the new height in pixels
	 */
	public void setHeight(int height) {
		getImpl().setHeight(getElement(), height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vaadin.gwtgraphics.client.VectorObject#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		boolean successful = false;
		if (height != null && height.endsWith("px")) {
			try {
				setHeight(Integer.parseInt(height.substring(0,
						height.length() - 2)));
				successful = true;
			} catch (NumberFormatException e) {
			}
		}
		if (!successful) {
			throw new IllegalArgumentException(
					"Only pixel units (px) are supported");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vaadin.gwtgraphics.client.animation.Animatable#setPropertyDouble(
	 * java.lang.String, double)
	 */
	public void setPropertyDouble(String property, double value) {
		property = property.toLowerCase();
		if ("x".equals(property)) {
			setX((int) value);
		} else if ("y".equals(property)) {
			setY((int) value);
		} else if ("width".equals(property)) {
			setWidth((int) value);
		} else if ("height".equals(property)) {
			setHeight((int) value);
		} else if ("rotation".equals(property)) {
			setRotation((int) value);
		}
	}
}