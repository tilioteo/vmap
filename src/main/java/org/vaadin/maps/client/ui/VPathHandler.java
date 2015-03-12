/**
 * 
 */
package org.vaadin.maps.client.ui;

import java.util.Stack;

import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.path.LineTo;
import org.vaadin.maps.client.drawing.Utils;
import org.vaadin.maps.client.geometry.Coordinate;
import org.vaadin.maps.client.geometry.LineString;
import org.vaadin.maps.shared.ui.Style;

import com.google.gwt.core.client.Duration;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.vaadin.shared.MouseEventDetails;

/**
 * @author kamil
 *
 */
public class VPathHandler extends VPointHandler {
	
	public enum FinishStrategy {
		AltClick,
		DoubleClick
	}

	public static final String CLASSNAME = "v-pathhandler";
	
	protected static final int DOUBLE_CLICK_THRESHOLD = 400; // milliseconds
	
	/**
	 * Representation of line start point, 
	 */
	protected Circle startPoint = null;
	protected Style startPointStyle = Style.DEFAULT_DRAW_START_POINT;
	
	/**
	 * Stack of line vertices, excluding first and last vertex
	 */
	protected Stack<Circle> vertices = new Stack<Circle>();
	protected Style vertexStyle = Style.DEFAULT_DRAW_VERTEX;
	
	protected Path line = null;
	protected Style lineStyle = Style.DEFAULT_DRAW_LINE;
	
	protected FinishStrategy strategy = FinishStrategy.AltClick;
	protected Duration clickDuration = null;
	
	/**
	 * line actually drawn
	 */
	protected LineString lineString = null;
	
	public VPathHandler() {
		super();
		setStyleName(CLASSNAME);
	}

	private void addStartPoint(int x, int y) {
		startPoint = new Circle(x, y, 0);
		updateStartPointStyle();
		container.add(startPoint);
	}

	private void updateStartPointStyle() {
		if (startPoint != null && startPointStyle != null) {
			Utils.updateDrawingStyle(startPoint, startPointStyle);
		}
	}

	private void removeStartPoint() {
		container.remove(startPoint);
		startPoint = null;
	}
	
	private void addLine(int x, int y) {
		line = new Path(x, y);
		line.setFillAllowed(false);
		line.lineTo(x, y);
		updateLineStyle();
		container.add(line);
	}
	
	private void updateLineStyle() {
		if (line != null && lineStyle != null) {
			Utils.updateDrawingStyle(line, lineStyle);
		}
	}

	private void removeLine() {
		container.remove(line);
		line = null;
		removeVertices();
	}
	
	private void addVertex(int x, int y) {
		Circle vertex = new Circle(x, y, 0);
		updateDrawVertexStyle(vertex);
		vertices.add(vertex);
		container.add(vertex);
	}
	
	private void updateDrawVertexStyle(Circle vertex) {
		if (vertex != null && vertexStyle != null) {
			Utils.updateDrawingStyle(vertex, vertexStyle);
		}
	}
	
	private void updateVerticesStyle() {
		for (Circle vertex : vertices) {
			updateDrawVertexStyle(vertex);
		}
	}

	private void removeVertices() {
		while (!vertices.isEmpty()) {
			Circle vertex = vertices.pop();
			container.remove(vertex);
		}
	}

	protected void removeLastVertex() {
		if (!vertices.isEmpty()) {
			Circle vertex = vertices.pop();
			container.remove(vertex);
		}
	}

	protected void removeLastLineStringVertex() {
		lineString.getCoordinateSequence().removeLast();
	}

	protected void addLineSegment(int x, int y) {
		addVertex(x, y);
		
		line.lineTo(x, y);
	}
	
	protected void addLineStringVertex(int[] xy) {
		lineString.getCoordinateSequence().add(createCoordinate(xy));
	}

	private void updateLineSegment(int x, int y) {
		if (line != null) {
			LineTo lastStep = (LineTo) line.getStep(line.getStepCount() - 1);
			lastStep.setX(x);
			lastStep.setY(y);
			line.issueRedraw(true);
		}
	}
	
	protected void prepareDrawing(int x, int y) {
		addStartPoint(x, y);
		addLine(x, y);
	}
	
	protected void prepareLineString(int[] xy) {
		lineString = new LineString(createCoordinate(xy));
	}
	
	/*private void finishLineString(int[] xy) {
		lineString.getCoordinateSequence().add(createWorldCoordinate(xy));
	}*/
	
	protected void closeLineString() {
		lineString.close();
	}

	protected void cleanDrawing() {
		removeVertices();
		removeStartPoint();
		removeLine();
	}
	
	protected void cleanLineString() {
		lineString = null;
	}
	
	public Style getStartPointStyle() {
		return startPointStyle;
	}
	
	public void setStartPointStyle(Style style) {
		if (style != null) {
			startPointStyle = style;
		} else {
			startPointStyle = Style.DEFAULT_DRAW_CURSOR;
		}
		
		updateStartPointStyle();
	}

	public Style getLineStyle() {
		return lineStyle;
	}
	
	public void setLineStyle(Style style) {
		if (style != null) {
			lineStyle = style;
		} else {
			lineStyle = Style.DEFAULT_DRAW_LINE;
		}
		
		updateLineStyle();
	}

	public Style getVertexStyle() {
		return vertexStyle;
	}
	
	public void setVertexStyle(Style style) {
		if (style != null) {
			vertexStyle = style;
		} else {
			vertexStyle = Style.DEFAULT_DRAW_VERTEX;
		}
		
		updateVerticesStyle();
	}

	/*@Override
	public void onClick(ClickEvent event) {
		if (!active) {
			return;
		}
		
		boolean finish = false;
		boolean isDoubleClick = false;
		
		if (null == clickDuration) {
			clickDuration = new Duration();
			
		} else {
			if (clickDuration.elapsedMillis() <= DOUBLE_CLICK_THRESHOLD) {
				isDoubleClick = true;
				clickDuration = null;
			} else {
				clickDuration = new Duration();
			}
		}

		if (isDoubleClick && !FinishStrategy.DoubleClick.equals(strategy)) {
			return;
		}
		
		int[] xy = getMouseEventXY(event);

		// first click
		// add start point and begin line drawing
		// create and insert start point
		if (null == startPoint) {
			prepareDrawing(xy[0], xy[1]);
			prepareLineString(xy);
		} else {
			if (event.isShiftKeyDown() && (FinishStrategy.AltClick.equals(strategy) || isDoubleClick)) {
				// finish drawing with closing line if shift key has been pressed
				// and the click is in start point's circle
				if (isWithinCircle(startPoint.getX(), startPoint.getY(), startPoint.getRadius(), xy[0], xy[1])) {
					finish = true;
					if (isDoubleClick) {
						// remove last vertex from everywhere
						removeLastLineStringVertex();
						removeLastVertex();
					}
					// close line
					closeLineString();
				}
			} else if ((FinishStrategy.AltClick.equals(strategy) && event.isAltKeyDown())) {
				// finish drawing when strategy conditions pass
				finish = true;
				// append last vertex
				addLineStringVertex(xy);

			} else if (FinishStrategy.DoubleClick.equals(strategy) && isDoubleClick) {
				finish = true;
			}
			
			if (finish) {
				fireEvent(new GeometryEvent(VPathHandler.this, lineString));
				cleanDrawing();
				cleanLineString();
			} else {
				addLineSegment(xy[0], xy[1]);
				// append vertex
				addLineStringVertex(xy);
			}
		}
	}*/

	
	@Override
	protected void syntheticClick(MouseEventDetails details, Element relativeElement) {
		if (!active) {
			return;
		}
		
		boolean finish = false;
		boolean isDoubleClick = false;
		
		if (null == clickDuration) {
			clickDuration = new Duration();
			
		} else {
			if (clickDuration.elapsedMillis() <= DOUBLE_CLICK_THRESHOLD) {
				isDoubleClick = true;
				clickDuration = null;
			} else {
				clickDuration = new Duration();
			}
		}

		if (isDoubleClick && !FinishStrategy.DoubleClick.equals(strategy)) {
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
			if (details.isShiftKey() && (FinishStrategy.AltClick.equals(strategy) || isDoubleClick)) {
				// finish drawing with closing line if shift key has been pressed
				// and the click is in start point's circle
				if (isWithinCircle(startPoint.getX(), startPoint.getY(), startPoint.getRadius(), xy[0], xy[1])) {
					finish = true;
					if (isDoubleClick) {
						// remove last vertex from everywhere
						removeLastLineStringVertex();
						removeLastVertex();
					}
					// close line
					closeLineString();
				}
			} else if ((FinishStrategy.AltClick.equals(strategy) && details.isAltKey())) {
				// finish drawing when strategy conditions pass
				finish = true;
				// append last vertex
				addLineStringVertex(xy);

			} else if (FinishStrategy.DoubleClick.equals(strategy) && isDoubleClick) {
				finish = true;
			}
			
			if (finish) {
				fireEvent(new GeometryEvent(VPathHandler.this, lineString));
				cleanDrawing();
				cleanLineString();
			} else {
				addLineSegment(xy[0], xy[1]);
				// append vertex
				addLineStringVertex(xy);
			}
		}
	}

	protected boolean isWithinCircle(int circleX, int circleY, int radius, int pointX, int pointY) {
		return Math.sqrt(
				Math.pow(pointX - circleX, 2) +
				Math.pow(pointY - circleY, 2)
				) <= radius;
	}
	
	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (!active) {
			return;
		}
		
		super.onMouseMove(event);
		
		int[] xy = getMouseEventXY(event);
		
		// update line segment to current position
		updateLineSegment(xy[0], xy[1]);
	}

	public void setStrategyFromCode(int code) {
		for (FinishStrategy finishStrategy : FinishStrategy.values()) {
			if (code == finishStrategy.ordinal()) {
				strategy = finishStrategy;
				return;
			}
		}
		strategy = FinishStrategy.AltClick;
	}

	@Override
	protected void updateDrawings(int deltaX, int deltaY) {
		if (startPoint != null ) {
			startPoint.setX(startPoint.getX() + deltaX);
			startPoint.setY(startPoint.getY() + deltaY);
		}
		if (line != null) {
			line.setX(line.getX() + deltaX);
			line.setY(line.getY() + deltaY);
		}
		for (Circle vertex : vertices) {
			vertex.setX(vertex.getX() + deltaX);
			vertex.setY(vertex.getY() + deltaY);
		}
		if (lineString != null) {
			Coordinate[] coordinates = lineString.getCoordinates();
			for (Coordinate coordinate : coordinates) {
				coordinate.x += deltaX;
				coordinate.y += deltaY;
			}
		}
	}


}
