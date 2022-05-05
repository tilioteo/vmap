package org.vaadin.maps.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.StyleConstants;
import com.vaadin.client.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Morong
 */
public class VGridLayout extends ComplexPanel implements CanShift {

    /**
     * Class name, prefix in styling
     */
    public static final String CLASSNAME = "v-gridlayout";
    protected final Element container = Document.get().createDivElement();
    protected final Map<Widget, GridWrapper> widgetGridWrappers = new HashMap<>();
    /**
     * For internal use only. May be removed or replaced in the future.
     */
    public ApplicationConnection client;
    private int shiftX = 0;
    private int shiftY = 0;

    private int measuredWidth = 0;
    private int measuredHeight = 0;

    public VGridLayout() {
        setElement(Document.get().createDivElement());
        setupElement();

        getElement().appendChild(container);
        setupContainer(container);
    }

    private void setupElement() {
        Style style = getElement().getStyle();
        style.setPosition(Position.RELATIVE);
        setStyleName(CLASSNAME);
    }

    private void setupContainer(Element container) {
        Style style = container.getStyle();
        style.setLeft(0, Unit.PX);
        style.setTop(0, Unit.PX);
        style.setWidth(100, Unit.PCT);
        style.setHeight(100, Unit.PCT);
        style.setPosition(Position.ABSOLUTE);
    }

    @Override
    public void add(Widget child) {
        GridWrapper wrapper = new GridWrapper(child);
        // wrapper.updateStyleNames();
        widgetGridWrappers.put(child, wrapper);
        super.add(wrapper.getWidget(), container);
    }

    @Override
    public boolean remove(Widget w) {
        GridWrapper wrapper = getChildWrapper(w);
        if (wrapper != null) {
            widgetGridWrappers.remove(w);
            return super.remove(w);
        }
        return super.remove(w);
    }

    /**
     * Does this layout contain a widget
     *
     * @param widget The widget to check
     * @return Returns true if the widget is in this layout, false if not
     */
    public boolean contains(Widget widget) {
        return getChildWrapper(widget) != null;
    }

    @Override
    public Widget getWidget(int index) {
        for (int i = 0, j = 0; i < super.getWidgetCount(); i++) {
            Widget w = super.getWidget(i);
            if (widgetGridWrappers.get(w) != null) {
                if (j == index) {
                    return w;
                } else {
                    j++;
                }
            }
        }
        return null;
    }

    @Override
    public int getWidgetCount() {
        int counter = 0;
        for (int i = 0; i < super.getWidgetCount(); i++) {
            if (widgetGridWrappers.get(super.getWidget(i)) != null) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public int getWidgetIndex(Widget child) {
        for (int i = 0, j = 0; i < super.getWidgetCount(); i++) {
            Widget w = super.getWidget(i);
            if (widgetGridWrappers.get(w) != null) {
                if (child == w) {
                    return j;
                } else {
                    j++;
                }
            }
        }
        return -1;
    }

    /**
     * Set the position of the widget in the layout. The position is a CSS
     * property string using properties such as top,left,right,top
     *
     * @param child The child widget to set the position for
     * @param left  The left position
     * @param top   The top position
     */
    public void setWidgetPosition(Widget child, int left, int top) {
        GridWrapper wrapper = getChildWrapper(child);
        if (wrapper != null) {
            wrapper.setPosition(left, top);
        }
    }

    /**
     * Get the wrapper for a widget
     *
     * @param child The child to get the wrapper for
     * @return
     */
    public GridWrapper getChildWrapper(Widget child) {
        if (getChildren().contains(child)) {
            return widgetGridWrappers.get(child);
        }
        return null;
    }

    @Override
    public void setStylePrimaryName(String style) {
        updateStylenames(style);
    }

    @Override
    public void setStyleName(String style) {
        super.setStyleName(style);
        updateStylenames(style);
        addStyleName(StyleConstants.UI_LAYOUT);
    }

    /**
     * Updates all style names contained in the layout
     *
     * @param primaryStyleName The style name to use as primary
     */
    protected void updateStylenames(String primaryStyleName) {
        super.setStylePrimaryName(primaryStyleName);
        container.setClassName(getStylePrimaryName() + "-container");
    }

    /**
     * Cleanup old wrappers which have been left empty by other inner layouts
     * moving the widget from the wrapper into their own hierarchy. This usually
     * happens when a call to setWidget(widget) is done in an inner layout which
     * automatically detaches the widget from the parent, in this case the
     * wrapper, and re-attaches it somewhere else. This has to be done in the
     * layout phase since the order of the hierarchy events are not defined.
     */
    public void cleanupWrappers() {
        ArrayList<Widget> dirtyWidgets = new ArrayList<>();
        WidgetCollection children = getChildren();
        for (GridWrapper wrapper : widgetGridWrappers.values()) {
            Widget w = wrapper.getWidget();
            if (!children.contains(w)) {
                dirtyWidgets.add(w);
            }
        }

        for (Widget w : dirtyWidgets) {
            widgetGridWrappers.remove(w);
        }
    }

    public int getMeasuredWidth() {
        return measuredWidth;
    }

    public int getMeasuredHeight() {
        return measuredHeight;
    }

    public void setMeasuredSize(int width, int height) {
        this.measuredWidth = width;
        this.measuredHeight = height;
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

        Style style = getElement().getStyle();
        style.setLeft(x, Unit.PX);
        style.setTop(y, Unit.PX);
    }

    /**
     * Returns the deepest nested child component which contains "element". The
     * child component is also returned if "element" is part of its caption.
     * <p>
     * For internal use only. May be removed or replaced in the future.
     *
     * @param element An element that is a nested sub element of the root element in
     *                this layout
     * @return The Paintable which the element is a part of. Null if the element
     * belongs to the layout and not to a child.
     * @deprecated As of 7.2, call or override {@link #getComponent(Element)}
     * instead
     */
    @Deprecated
    public ComponentConnector getComponent(com.google.gwt.user.client.Element element) {
        return Util.getConnectorForElement(client, this, element);

    }

    /**
     * Returns the deepest nested child component which contains "element". The
     * child component is also returned if "element" is part of its caption.
     * <p>
     * For internal use only. May be removed or replaced in the future.
     *
     * @param element An element that is a nested sub element of the root element in
     *                this layout
     * @return The Paintable which the element is a part of. Null if the element
     * belongs to the layout and not to a child.
     * @since 7.2
     */
    public ComponentConnector getComponent(Element element) {
        return getComponent(DOM.asOld(element));
    }

    /**
     * Internal wrapper for wrapping widgets in the Grid layout
     */
    protected static class GridWrapper {
        private Widget widget;

        private int left;
        private int top;

        /**
         * Constructor
         *
         * @param child The child to wrap
         */
        public GridWrapper(Widget child) {
            setWidget(child);

            left = 0;
            top = 0;
        }

        public Widget getWidget() {
            return widget;
        }

        public void setWidget(Widget w) {
            // Validate
            if (w == widget) {
                return;
            }

            widget = w;
        }

        /**
         * Set the position for the wrapper in the layout
         *
         * @param position The position string
         */
        public void setPosition(int left, int top) {
            this.left = left;
            this.top = top;
            // ensure new values
            Style style = widget.getElement().getStyle();
            style.setLeft(left, Unit.PX);
            style.setTop(top, Unit.PX);

            // tiles must be positioned absolutely
            style.setPosition(Position.ABSOLUTE);
        }

        public int getLeft() {
            return left;
        }

        public int getTop() {
            return top;
        }
    }
}
