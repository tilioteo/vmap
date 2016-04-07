/**
 * 
 */
package org.vaadin.gwtgraphics.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vaadin.gwtgraphics.client.impl.DrawImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Kamil Morong
 * 
 */
public abstract class AbstractDrawingContainer extends Panel implements Drawing, HasDrawings, Orderable {

	protected static final DrawImpl impl = GWT.create(DrawImpl.class);

	private List<Drawing> children = new ArrayList<>();

	public AbstractDrawingContainer() {
		setElement(impl.createElement(getType()));
	}

	protected DrawImpl getImpl() {
		return impl;
	}

	@Override
	public void add(Widget child) {
		if (child instanceof Drawing) {
			getImpl().add(getElement(), child.getElement(), child.isAttached());
			adopt(child);
			children.add((Drawing) child);
		}
	}

	@Override
	public Drawing addDrawing(Drawing drawing) {
		add(drawing.asWidget());

		return drawing;
	}

	@Override
	public Iterator<Drawing> drawingIterator() {
		return children.iterator();
	}

	@Override
	public Iterator<Widget> iterator() {
		List<Widget> widgets = new ArrayList<>();
		for (Drawing drawing : children) {
			widgets.add(drawing.asWidget());
		}
		return widgets.iterator();
	}

	@Override
	public boolean remove(Widget child) {
		if (child instanceof Drawing) {
			getElement().removeChild(child.getElement());
			Widget parent = child.getParent();
			if (parent == this) {
				orphan(child);
			}
			children.remove(child);
			return true;
		}

		return false;
	}

	@Override
	public Drawing removeDrawing(Drawing drawing) {
		return remove(drawing.asWidget()) ? drawing : null;
	}

	@Override
	public void clear() {
		for (Drawing drawing : children) {
			getElement().removeChild(drawing.asWidget().getElement());
			Widget parent = drawing.asWidget().getParent();
			if (parent == this) {
				orphan(drawing.asWidget());
			}
		}

		children.clear();
	}

	@Override
	public Drawing insert(Drawing drawing, int beforeIndex) {
		if (beforeIndex < 0 || beforeIndex > getCount()) {
			throw new IndexOutOfBoundsException();
		}

		if (children.contains(drawing)) {
			children.remove(drawing);
		}

		getImpl().insert(getElement(), drawing.asWidget().getElement(), beforeIndex, drawing.asWidget().isAttached());
		adoptOnIndex(drawing, beforeIndex);

		return drawing;
	}

	private void adoptOnIndex(Drawing drawing, int beforeIndex) {
		adopt(drawing.asWidget());
		children.add(beforeIndex, drawing);
	}

	@Override
	public Drawing getDrawing(int index) {
		if (index >= 0 && index < children.size()) {
			return children.get(index);
		}

		return null;
	}

	@Override
	public int getCount() {
		return children.size();
	}

	@Override
	public void setStyleName(String style) {
		getImpl().setStyleName(getElement(), style);
	}

	@Override
	public void setStyleName(String style, boolean add) {
		getImpl().setStyleName(getElement(), style, add);
	}

	@Override
	public void setHeight(String height) {
		// nop
	}

	@Override
	public void setWidth(String width) {
		// nop
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return addDomHandler(handler, DoubleClickEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return addDomHandler(handler, MouseUpEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return addDomHandler(handler, MouseWheelEvent.getType());
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		getImpl().onAttach(getElement(), isAttached());
	}

	@Override
	public void removeFromParent() {
		// nop
	}

	@Override
	public String getStylePrimaryName() {
		return "";
	}

}
