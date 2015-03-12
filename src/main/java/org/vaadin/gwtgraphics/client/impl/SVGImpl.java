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
package org.vaadin.gwtgraphics.client.impl;

import java.util.List;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.AbstractDrawing;
import org.vaadin.gwtgraphics.client.impl.util.NumberUtil;
import org.vaadin.gwtgraphics.client.impl.util.SVGBBox;
import org.vaadin.gwtgraphics.client.impl.util.SVGUtil;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Ellipse;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;
import org.vaadin.gwtgraphics.client.shape.path.Arc;
import org.vaadin.gwtgraphics.client.shape.path.ClosePath;
import org.vaadin.gwtgraphics.client.shape.path.CurveTo;
import org.vaadin.gwtgraphics.client.shape.path.LineTo;
import org.vaadin.gwtgraphics.client.shape.path.MoveTo;
import org.vaadin.gwtgraphics.client.shape.path.PathStep;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * This class contains the SVG implementation module of GWT Graphics.
 * 
 * @author Henri Kerola
 * 
 */
public class SVGImpl extends DrawImpl {

	@Override
	public String getRendererString() {
		return "SVG";
	}

	@Override
	public String getStyleSuffix() {
		return "svg";
	}

	/*
	 * public Element createDrawingArea(Element container, int width, int
	 * height) { Element root = SVGUtil.createSVGElementNS("svg"); // IE9 needs
	 * this to crop overflowing content root.setAttribute("overflow", "hidden");
	 * container.appendChild(root); setWidth(root, width); setHeight(root,
	 * height);
	 * 
	 * Element defs = SVGUtil.createSVGElementNS("defs");
	 * root.appendChild(defs);
	 * 
	 * return root; }
	 */

	@Override
	public Element createElement(Class<? extends AbstractDrawing> type) {
		Element element = null;
		if (type == DrawingArea.class) {
			element = SVGUtil.createSVGElementNS("svg");
			// IE9 needs this to crop overflowing content
			element.setAttribute("overflow", "hidden");

			Element defs = SVGUtil.createSVGElementNS("defs");
			element.appendChild(defs);
		} else if (type == Rectangle.class) {
			element = SVGUtil.createSVGElementNS("rect");
		} else if (type == Circle.class) {
			element = SVGUtil.createSVGElementNS("circle");
		} else if (type == Ellipse.class) {
			element = SVGUtil.createSVGElementNS("ellipse");
		} else if (type == Path.class) {
			element = SVGUtil.createSVGElementNS("path");
		} else if (type == Text.class) {
			element = SVGUtil.createSVGElementNS("text");
			element.setAttribute("text-anchor", "start");
		} else if (type == Image.class) {
			element = SVGUtil.createSVGElementNS("image");
			// Let aspect ration behave like VML's image does
			element.setAttribute("preserveAspectRatio", "none");
		} else if (type == Line.class) {
			element = SVGUtil.createSVGElementNS("line");
		} else if (type == Group.class) {
			element = SVGUtil.createSVGElementNS("g");
		}
		
		return element;
	}

	@Override
	public int getX(Element element) {
		if (element.getTagName().toLowerCase().equals("g")) {
			MatchResult r = getTranslation(element);
			return r != null && r.getGroupCount() == 4 ? NumberUtil
					.parseIntValue(r.getGroup(1), 0) : 0;
		} else {
			return NumberUtil.parseIntValue(element,
					getPosAttribute(element, true), 0);
		}
	}

	@Override
	public void setX(final Element element, final int x, final boolean attached) {
		setXY(element, true, x, attached);
	}

	@Override
	public int getY(Element element) {
		if (element.getTagName().toLowerCase().equals("g")) {
			MatchResult r = getTranslation(element);
			return r != null && r.getGroupCount() == 4 ? NumberUtil
					.parseIntValue(r.getGroup(3), 0) : 0;
		} else {
			return NumberUtil.parseIntValue(element,
					getPosAttribute(element, false), 0);
		}

	}

	@Override
	public void setY(final Element element, final int y, final boolean attached) {
		setXY(element, false, y, attached);
	}

	private void setXY(final Element element, boolean x, final int value,
			final boolean attached) {
		if (element.getTagName().toLowerCase().equals("g")) {
			int other = x ? getY(element) : getX(element);
			StringBuilder sb = new StringBuilder("translate(");
			if (x) {
				sb.append(value).append(",").append(other);
			} else {
				sb.append(other).append(",").append(value);
			}
			sb.append(")");
			RegExp p = RegExp.compile("translate\\(.+\\)");
			String xform = element.getAttribute("transform");
			if (xform != null && xform.length() > 0) {
				sb.append(" ");
				MatchResult r = p.exec(xform);
				if (r.getGroupCount() > 0) {
					p.replace(xform, sb.toString());
				} else {
					xform = sb.append(xform).toString();
				}

			}
			element.setAttribute("transform", sb.toString().trim());
		} else {
			final int rotation = getRotation(element);
			final String posAttr = getPosAttribute(element, x);
			SVGUtil.setAttributeNS(element, posAttr, value);
			if (rotation != 0) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					public void execute() {
						SVGUtil.setAttributeNS(element, "transform", "");
						SVGUtil.setAttributeNS(element, posAttr, value);
						setRotateTransform(element, rotation, attached);
					}
				});
			}
		}
	}

	private String getPosAttribute(Element element, boolean x) {
		String tagName = element.getTagName();
		String attr = "";
		if (tagName.equals("rect") || tagName.equals("text")
				|| tagName.equals("image")) {
			attr = x ? "x" : "y";
		} else if (tagName.equals("circle") || tagName.equals("ellipse")) {
			attr = x ? "cx" : "cy";
		} else if (tagName.equals("path")) {

		} else if (tagName.equals("line")) {
			attr = x ? "x1" : "y1";
		}
		return attr;
	}

	private MatchResult getTranslation(Element e) {
		String xform = e.getAttribute("transform");
		return xform != null ? RegExp.compile(
				"translate\\(\\s*([-+]?\\d+)\\s*(,\\s*([-+]?\\d+))?\\s*\\)", "i").exec(
				xform) : null;
	}

	@Override
	public String getFillColor(Element element) {
		String fill = element.getAttribute("fill");
		return fill.equals("none") ? null : fill;
	}

	@Override
	public void setFillColor(Element element, String color) {
		if (color == null) {
			color = "none";
		}
		SVGUtil.setAttributeNS(element, "fill", color);
	}

	@Override
	public double getFillOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("fill-opacity"), 1);
	}

	@Override
	public void setFillOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "fill-opacity", "" + opacity);
	}

	@Override
	public String getStrokeColor(Element element) {
		String stroke = element.getAttribute("stroke");
		return stroke.equals("none") ? null : stroke;
	}

	@Override
	public void setStrokeColor(Element element, String color) {
		SVGUtil.setAttributeNS(element, "stroke", color);
	}

	@Override
	public int getStrokeWidth(Element element) {
		return NumberUtil.parseIntValue(element, "stroke-width", 0);
	}

	@Override
	public void setStrokeWidth(Element element, int width, boolean attached) {
		SVGUtil.setAttributeNS(element, "stroke-width", width);
	}

	@Override
	public double getStrokeOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("stroke-opacity"), 1);
	}

	@Override
	public void setStrokeOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "stroke-opacity", "" + opacity);
	}

	@Override
	public double getOpacity(Element element) {
		return NumberUtil.parseDoubleValue(
				element.getAttribute("opacity"), 1);
	}

	@Override
	public void setOpacity(Element element, double opacity) {
		SVGUtil.setAttributeNS(element, "opacity", "" + opacity);
	}

	@Override
	public int getWidth(Element element) {
		return NumberUtil.parseIntValue(element, "width", 0);
	}

	@Override
	public void setWidth(Element element, int width) {
		SVGUtil.setAttributeNS(element, "width", width);
		/*if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle().setPropertyPx("width", width);
		}*/
	}

	@Override
	public void setAreaWidth(Element element, String value) {
		if (element.getTagName().equalsIgnoreCase("svg")) {
			SVGUtil.setAttributeNS(element, "width", value);
		}
	}

	@Override
	public int getHeight(Element element) {
		return NumberUtil.parseIntValue(element, "height", 0);
	}

	@Override
	public void setHeight(Element element, int height) {
		SVGUtil.setAttributeNS(element, "height", height);
		/*if (element.getTagName().equalsIgnoreCase("svg")) {
			element.getParentElement().getStyle().setPropertyPx("height", height);
		}*/
	}

	@Override
	public void setAreaHeight(Element element, String value) {
		if (element.getTagName().equalsIgnoreCase("svg")) {
			SVGUtil.setAttributeNS(element, "height", value);
		}
	}

	@Override
	public int getCircleRadius(Element element) {
		return NumberUtil.parseIntValue(element, "r", 0);
	}

	@Override
	public void setCircleRadius(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "r", radius);
	}

	@Override
	public int getEllipseRadiusX(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	@Override
	public void setEllipseRadiusX(Element element, int radiusX) {
		SVGUtil.setAttributeNS(element, "rx", radiusX);
	}

	@Override
	public int getEllipseRadiusY(Element element) {
		return NumberUtil.parseIntValue(element, "ry", 0);
	}

	@Override
	public void setEllipseRadiusY(Element element, int radiusY) {
		SVGUtil.setAttributeNS(element, "ry", radiusY);
	}

	@Override
	public void drawPath(Element element, List<PathStep> steps) {
		StringBuilder path = new StringBuilder();
		for (PathStep step : steps) {
			appendPathStep(path, step);
		}
		SVGUtil.setAttributeNS(element, "d", path.toString());
	}

	/**
	 * Builds the string for single step Needed when appending step to the end
	 * of path
	 * 
	 * @param element
	 * 
	 * @param step
	 *            step which string we need
	 * @return path step string
	 */
	@Override
	public void getPathStepString(Element element, PathStep step) {
		StringBuilder path = new StringBuilder();
		appendPathStep(path, step);
		element.getAttribute("d").concat(path.toString());
	}

	private void appendPathStep(StringBuilder path, PathStep step) {
		if (step instanceof Arc) {
			Arc arc = (Arc) step;
			path.append(arc.isRelativeCoords() ? " a" : " A");
			path.append(arc.getRx()).append(",").append(arc.getRy());
			path.append(" ").append(arc.getxAxisRotation());
			path.append(" ").append(arc.isLargeArc() ? "1" : "0").append(",")
					.append(arc.isSweep() ? "1" : "0");
			path.append(" ").append(arc.getX()).append(",").append(arc.getY());
		} else if (step instanceof CurveTo) {
			CurveTo curve = (CurveTo) step;
			path.append(curve.isRelativeCoords() ? " c" : " C");
			path.append(curve.getX1()).append(" ").append(curve.getY1());
			path.append(" ").append(curve.getX2()).append(" ")
					.append(curve.getY2());
			path.append(" ").append(curve.getX()).append(" ")
					.append(curve.getY());
		} else if (step instanceof LineTo) {
			LineTo lineTo = (LineTo) step;
			path.append(lineTo.isRelativeCoords() ? " l" : " L")
					.append(lineTo.getX()).append(" ").append(lineTo.getY());
		} else if (step instanceof MoveTo) {
			MoveTo moveTo = (MoveTo) step;
			path.append(moveTo.isRelativeCoords() ? " m" : " M")
					.append(moveTo.getX()).append(" ").append(moveTo.getY());
		} else if (step instanceof ClosePath) {
			path.append(" z");
		}
	}

	@Override
	public String getText(Element element) {
		return element.getInnerText();
	}

	@Override
	public void setText(Element element, String text, boolean attached) {
		element.setInnerText(text);
	}

	@Override
	public String getTextFontFamily(Element element) {
		return element.getAttribute("font-family");
	}

	@Override
	public void setTextFontFamily(Element element, String family,
			boolean attached) {
		SVGUtil.setAttributeNS(element, "font-family", family);
	}

	@Override
	public int getTextFontSize(Element element) {
		return NumberUtil.parseIntValue(element, "font-size", 0);
	}

	@Override
	public void setTextFontSize(Element element, int size, boolean attached) {
		SVGUtil.setAttributeNS(element, "font-size", size);
	}

	@Override
	public String getImageHref(Element element) {
		return element.getAttribute("href");
	}

	@Override
	public void setImageHref(Element element, String src) {
		SVGUtil.setAttributeNS(SVGUtil.XLINK_NS, element, "href", src);
	}

	@Override
	public int getRectangleRoundedCorners(Element element) {
		return NumberUtil.parseIntValue(element, "rx", 0);
	}

	@Override
	public void setRectangleRoundedCorners(Element element, int radius) {
		SVGUtil.setAttributeNS(element, "rx", radius);
		SVGUtil.setAttributeNS(element, "ry", radius);
	}

	@Override
	public int getLineX2(Element element) {
		return NumberUtil.parseIntValue(element, "x2", 0);
	}

	@Override
	public void setLineX2(Element element, int x2) {
		SVGUtil.setAttributeNS(element, "x2", x2);

	}

	@Override
	public int getLineY2(Element element) {
		return NumberUtil.parseIntValue(element, "y2", 0);
	}

	@Override
	public void setLineY2(Element element, int y2) {
		SVGUtil.setAttributeNS(element, "y2", y2);

	}

	@Override
	public void add(Element root, Element element, boolean attached) {
		root.appendChild(element);
	}

	@Override
	public void insert(Element root, Element element, int beforeIndex,
			boolean attached) {
		if ("defs".equals(root.getChildNodes().getItem(0).getNodeName())) {
			beforeIndex++;
		}
		Element e = root.getChildNodes().getItem(beforeIndex).cast();
		root.insertBefore(element, e);
	}

	@Override
	public void remove(Element root, Element element) {
		root.removeChild(element);
	}

	@Override
	public void bringToFront(Element root, Element element) {
		root.appendChild(element);
	}

	@Override
	public void clear(Element root) {
		while (root.hasChildNodes()) {
			root.removeChild(root.getLastChild());
		}
	}

	@Override
	public void setStyleName(Element element, String name) {
		SVGUtil.setClassName(element, name); 
				//+ "-" + getStyleSuffix());
	}

	@Override
	public void setStyleName(Element element, String name, boolean add) {
		SVGUtil.setClassName(element, name, add); 
	}

	@Override
	public void setRotation(final Element element, final int degree,
			final boolean attached) {
		element.setPropertyInt("_rotation", degree);
		if (degree == 0) {
			SVGUtil.setAttributeNS(element, "transform", "");
			return;
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				setRotateTransform(element, degree, attached);
			}
		});
	}

	private void setRotateTransform(Element element, int degree,
			boolean attached) {
		SVGBBox box = SVGUtil.getBBBox(element, attached);
		int x = box.getX() + box.getWidth() / 2;
		int y = box.getY() + box.getHeight() / 2;
		SVGUtil.setAttributeNS(element, "transform", "rotate(" + degree + " "
				+ x + " " + y + ")");
	}

	@Override
	public int getRotation(Element element) {
		return element.getPropertyInt("_rotation");
	}

	@Override
	public void onAttach(Element element, boolean attached) {
	}

	@Override
	public void initCanvasSize(Element element, int width, int height) {
		setWidth(element, width);
		setHeight(element, height);
	}

	@Override
	public void setPathFillEvenOdd(Element element) {
		element.setAttribute("fill-rule", "evenodd");
	}
}
