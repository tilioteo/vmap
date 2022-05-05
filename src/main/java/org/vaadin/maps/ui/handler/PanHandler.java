package org.vaadin.maps.ui.handler;

import com.tilioteo.common.event.TimekeepingComponentEvent;
import com.vaadin.util.ReflectTools;
import org.vaadin.maps.shared.ui.handler.PanHandlerRpc;
import org.vaadin.maps.shared.ui.handler.PanHandlerState;
import org.vaadin.maps.ui.LayerLayout;
import org.vaadin.maps.ui.control.Control;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Kamil Morong
 */
public class PanHandler extends NavigateHandler {

    private final PanHandlerRpc rpc = new PanHandlerRpc() {
        @Override
        public void panStart(long timestamp, int x, int y) {
            fireEvent(new PanStartEvent(timestamp, PanHandler.this, x, y));
        }

        @Override
        public void panEnd(long timestamp, int deltaX, int deltaY) {
            fireEvent(new PanEndEvent(timestamp, PanHandler.this, deltaX, deltaY));
        }
    };
    protected LayerLayout layout = null;

    public PanHandler(Control control) {
        super(control);

        registerRpc(rpc);
    }

    @Override
    public void setLayout(LayerLayout layout) {
        this.layout = layout;
        getState().layout = layout;
    }

    @Override
    protected PanHandlerState getState() {
        return (PanHandlerState) super.getState();
    }

    /**
     * Adds the pan start listener.
     *
     * @param listener the Listener to be added.
     */
    public void addPanStartListener(PanStartListener listener) {
        addListener(PanStartEvent.class, listener, PanStartListener.PAN_START_METHOD);
    }

    /**
     * Removes the pan start listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removePanStartListener(PanStartListener listener) {
        removeListener(PanStartEvent.class, listener, PanStartListener.PAN_START_METHOD);
    }

    /**
     * Adds the pan end listener.
     *
     * @param listener the Listener to be added.
     */
    public void addPanEndListener(PanEndListener listener) {
        addListener(PanEndEvent.class, listener, PanEndListener.PAN_END_METHOD);
    }

    /**
     * Removes the end start listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removePanEndListener(PanEndListener listener) {
        removeListener(PanEndEvent.class, listener, PanEndListener.PAN_END_METHOD);
    }

    /**
     * Interface for listening for a {@link PanStartEvent} fired by a
     * {@link PanHandler}.
     */
    public interface PanStartListener extends Serializable {

        Method PAN_START_METHOD = ReflectTools.findMethod(PanStartListener.class, "panStart",
                PanStartEvent.class);

        /**
         * Called when panning started.
         *
         * @param event An event containing information about pan.
         */
        void panStart(PanStartEvent event);

    }

    /**
     * Interface for listening for a {@link PanEndEvent} fired by a
     * {@link PanHandler}.
     */
    public interface PanEndListener extends Serializable {

        Method PAN_END_METHOD = ReflectTools.findMethod(PanEndListener.class, "panEnd",
                PanEndEvent.class);

        /**
         * Called when panning ended.
         *
         * @param event An event containing information about pan.
         */
        void panEnd(PanEndEvent event);

    }

    public static abstract class PanEvent extends TimekeepingComponentEvent {

        private final int x;
        private final int y;

        protected PanEvent(long timestamp, PanHandler source, int x, int y) {
            super(timestamp, source);
            this.x = x;
            this.y = y;
        }

        protected int getX() {
            return x;
        }

        protected int getY() {
            return y;
        }
    }

    /**
     * This event is thrown, when the panning started.
     */
    public static class PanStartEvent extends PanEvent {

        public PanStartEvent(long timestamp, PanHandler source, int x, int y) {
            super(timestamp, source, x, y);
        }

        @Override
        public int getX() {
            return super.getX();
        }

        @Override
        public int getY() {
            return super.getY();
        }
    }

    /**
     * This event is thrown, when the panning ended.
     */
    public static class PanEndEvent extends PanEvent {

        public PanEndEvent(long timestamp, PanHandler source, int deltaX, int deltaY) {
            super(timestamp, source, deltaX, deltaY);
        }

        public int getDeltaX() {
            return getX();
        }

        public int getDeltaY() {
            return getY();
        }
    }

}
