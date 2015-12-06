/**
 * 
 */
package org.vaadin.maps.client.ui;

import org.vaadin.maps.shared.ui.Style;

/**
 * @author Kamil Morong
 *
 */
public abstract class AbstractDrawFeatureHandler extends AbstractHandler {

	protected Style cursorStyle;

	public Style getCursorStyle() {
		return cursorStyle;
	}

	public void setCursorStyle(Style style) {
		if (style != null) {
			cursorStyle = style;
		} else {
			cursorStyle = Style.DEFAULT_DRAW_CURSOR;
		}
	}

}
