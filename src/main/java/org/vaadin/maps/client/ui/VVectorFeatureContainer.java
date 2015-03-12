/**
 * 
 */
package org.vaadin.maps.client.ui;

import java.util.Iterator;

import org.vaadin.gwtgraphics.client.AbstractDrawing;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.shape.Path;

import com.google.gwt.user.client.Window;

/**
 * @author kamil
 *
 */
public class VVectorFeatureContainer extends DrawingArea implements CanShift {
	
	public static final String CLASSNAME = "v-vectorfeaturecontainer";
	
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
	public AbstractDrawing add(AbstractDrawing drawing) {
		if (drawing != null) {
			VVectorFeature feature = null;
			if (drawing instanceof VVectorFeature) {
				feature = (VVectorFeature) drawing;
				feature.setShift(shiftX, shiftY);
			}
			if (feature != null && feature.isHidden()) {
				return hiddenContainer.add(feature);
			} else {
				return container.add(drawing);
			}
		}
		return null;
		/*if (drawing != null) {
			if (drawing instanceof VVectorFeature && ((VVectorFeature) drawing).isHidden()) {
				return hiddenContainer.add(drawing);
			} else {
				return container.add(drawing);
			}
		}
		return null;*/
	}

	@Override
	public AbstractDrawing remove(AbstractDrawing drawing) {
		if (drawing != null) {
			if (drawing.getParent() == container) {
				return container.remove(drawing);
			} else if (drawing.getParent() == hiddenContainer) {
				return hiddenContainer.remove(drawing);
			}
		}
		return null;
	}

	/*@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub
		super.setHeight(height);
	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub
		super.setWidth(width);
	}*/
	
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
		Iterator<AbstractDrawing> iterator;
		for (iterator = container.iterator(); iterator.hasNext();) {
			AbstractDrawing drawing = iterator.next();
			if (drawing instanceof CanShift) {
				((CanShift)drawing).setShift(shiftX, shiftY);
			}
		}

		for (iterator = hiddenContainer.iterator(); iterator.hasNext();) {
			AbstractDrawing drawing = iterator.next();
			if (drawing instanceof CanShift) {
				((CanShift)drawing).setShift(shiftX, shiftY);
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