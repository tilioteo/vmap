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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import org.vaadin.gwtgraphics.client.impl.DrawImpl;

/**
 * An abstract base class for drawing objects.
 * <p>
 * renamed from VectorObject
 *
 * @author Henri Kerola
 * @author Kamil Morong
 */
public abstract class AbstractDrawing extends Widget implements Drawing {

    protected static final DrawImpl impl = GWT.create(DrawImpl.class);
    private Widget parent;

    protected AbstractDrawing() {
        setElement(impl.createElement(getType()));
    }

    protected DrawImpl getImpl() {
        return impl;
    }

    public int getRotation() {
        return getImpl().getRotation(getElement());
    }

    public void setRotation(int degree) {
        getImpl().setRotation(getElement(), degree, isAttached());
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public Widget getParent() {
        return parent;
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

    /**
     * Removes this drawing from its parent drawing, if one exists.
     *
     * @throws IllegalStateException if this widget's parent does not support removal
     */
    @Override
    public void removeFromParent() {
        // nop
    }

    @Override
    public String getStylePrimaryName() {
        return "";
    }
}