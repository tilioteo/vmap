/**
 * 
 */
package org.vaadin.maps.client.ui;

import java.util.Iterator;

import org.vaadin.gwtgraphics.client.Drawing;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.shape.Path;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Kamil Morong
 *
 */
public class VVectorFeatureContainer extends DrawingArea implements CanShift {

	public static final String CLASSNAME = "v-vectorfeaturecontainer";

	private Element drawingElement = null;

	private Group container = new Group();
	private Group hiddenContainer = new Group();
	private Path ieTridentPath = null;

	private int shiftX = 0;
	private int shiftY = 0;

	private CanShift canShiftSlave = null;

	public VVectorFeatureContainer() {
		super(1, 1);

		setStyleName(CLASSNAME);

		addIETridentHack();

		super.add(container);

		hiddenContainer.setOpacity(0);
		super.add(hiddenContainer);
	}

	@Override
	public Class<? extends Drawing> getType() {
		return null;
	}

	private Element ensureDrawingElement() {
		if (null == drawingElement) {
			drawingElement = getImpl().createElement(super.getType());
			super.getElement().appendChild(drawingElement);
		}

		return drawingElement;
	}

	@Override
	public com.google.gwt.user.client.Element getElement() {
		return DOM.asOld(ensureDrawingElement());
	}

	@Override
	public Drawing addDrawing(Drawing drawing) {
		if (drawing != null) {
			add(drawing.asWidget());

			return drawing;
		}

		return null;
	}

	@Override
	public void add(Widget child) {
		if (child != null && child instanceof VVectorFeature) {
			VVectorFeature feature = (VVectorFeature) child;
			feature.setShift(shiftX, shiftY);

			if (feature.isHidden()) {
				hiddenContainer.add(child);
			} else {
				container.add(feature);
			}
		}
	}

	@Override
	public Drawing removeDrawing(Drawing drawing) {
		if (drawing != null) {
			return remove(drawing.asWidget()) ? drawing : null;
		}

		return null;
	}

	@Override
	public boolean remove(Widget child) {
		if (child != null && child instanceof VVectorFeature) {
			Widget parent = child.getParent();

			if (parent == container) {
				return container.remove(child);
			} else if (parent == hiddenContainer) {
				return hiddenContainer.remove(child);
			}
		}

		return false;
	}

	private void addIETridentHack() {
		String userAgent = Window.Navigator.getUserAgent().toLowerCase();
		if (userAgent.contains("trident")) {
			ieTridentPath = new Path(-10, -10);
			ieTridentPath.lineRelativelyTo(10000, 0);
			ieTridentPath.lineRelativelyTo(0, 10000);
			ieTridentPath.lineRelativelyTo(-10000, 0);
			ieTridentPath.close();
			ieTridentPath.setOpacity(0.0);
			ieTridentPath.setFillOpacity(0.0);
			ieTridentPath.setStrokeOpacity(0.0);
			ieTridentPath.setStrokeWidth(0);

			super.add(ieTridentPath);
		}
	}

	@Override
	public int getShiftX() {
		return shiftX;
	}

	@Override
	public int getShiftY() {
		return shiftY;
	}

	@Override
	public void setShift(int x, int y) {
		shiftX = x;
		shiftY = y;

		setChildrenShift();
		if (canShiftSlave != null) {
			canShiftSlave.setShift(x, y);
		}
	}

	private void setChildrenShift() {
		Iterator<Drawing> iterator;
		for (iterator = container.drawingIterator(); iterator.hasNext();) {
			Drawing drawing = iterator.next();
			if (drawing instanceof CanShift) {
				((CanShift) drawing).setShift(shiftX, shiftY);
			}
		}

		for (iterator = hiddenContainer.drawingIterator(); iterator.hasNext();) {
			Drawing drawing = iterator.next();
			if (drawing instanceof CanShift) {
				((CanShift) drawing).setShift(shiftX, shiftY);
			}
		}
	}

	public CanShift getCanShiftSlave() {
		return canShiftSlave;
	}

	public void setCanShiftSlave(CanShift canShiftSlave) {
		this.canShiftSlave = canShiftSlave;
	}
}