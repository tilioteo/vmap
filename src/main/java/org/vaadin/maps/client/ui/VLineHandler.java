/**
 * 
 */
package org.vaadin.maps.client.ui;

import com.google.gwt.dom.client.Element;
import com.vaadin.shared.MouseEventDetails;

/**
 * @author kamil
 *
 */
public class VLineHandler extends VPathHandler {
	
	public static final String CLASSNAME = "v-linehandler";
	
	public VLineHandler() {
		super();
		setStyleName(CLASSNAME);
	}

	@Override
	protected void syntheticClick(MouseEventDetails details, Element relativeElement) {
		if (!active) {
			return;
		}
		
		int[] xy = getMouseEventXY(details, relativeElement);

		// first click
		// add start point and begin line drawing
		// create and insert start point
		if (null == startPoint) {
			prepareDrawing(xy[0], xy[1]);
			prepareLineString(xy);
		} else {
			// finish drawing
			// append last vertex
			addLineStringVertex(xy);
			fireEvent(new GeometryEvent(VLineHandler.this, lineString));
			cleanDrawing();
			cleanLineString();
		}
	}

}
