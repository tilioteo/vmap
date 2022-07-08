package org.vaadin.maps.ui.tile;

import com.tilioteo.common.event.MouseEvents;
import com.tilioteo.common.event.TimekeepingComponentEvent;
import com.vaadin.event.ConnectorEventListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ResourceReference;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.URLReference;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;
import org.vaadin.maps.shared.ui.tile.ImageSequenceTileServerRpc;
import org.vaadin.maps.shared.ui.tile.ImageSequenceTileState;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kamil Morong
 */
public class ImageSequenceTile extends AbstractTile {

    protected final ImageSequenceTileServerRpc rpc = new ImageSequenceTileServerRpc() {
        @Override
        public void load(long timestamp) {
            fireEvent(new LoadEvent(timestamp, ImageSequenceTile.this));
        }

        @Override
        public void error(long timestamp) {
            fireEvent(new ErrorEvent(timestamp, ImageSequenceTile.this));
        }

        @Override
        public void changed(long timestamp, int index) {
            fireEvent(new ChangeEvent(timestamp, ImageSequenceTile.this, index));
        }

        @Override
        public void click(long timestamp, MouseEventDetails mouseDetails, int index) {
            fireEvent(new ClickEvent(timestamp, ImageSequenceTile.this, mouseDetails, index));
        }
    };

    private final LinkedList<Resource> resources = new LinkedList<>();

    public ImageSequenceTile() {
        registerRpc(rpc);
    }

    @Override
    protected ImageSequenceTileState getState() {
        return (ImageSequenceTileState) super.getState();
    }

    public void addResource(String url) {
        ExternalResource source = new ExternalResource(url);
        addSource(source);
    }

    public void addResource(URL url) {
        ExternalResource source = new ExternalResource(url);
        addSource(source);
    }

    protected void addSource(Resource source) {
        resources.add(source);

        List<URLReference> sources = getState().sources;
        sources.add(new ResourceReference(source, this, Integer.toString(sources.size())));
        getState().sourceTypes.add(source.getMIMEType());
    }

    public void clear() {
        resources.clear();

        getState().sources.clear();
        getState().sourceTypes.clear();
    }

    public int getIndex() {
        return getState().index;
    }

    public void setIndex(int index) {
        getState().index = index;
    }

    public int getTilesCount() {
        return resources.size();
    }

    /**
     * Adds the tile load listener.
     *
     * @param listener the Listener to be added.
     */
    public void addLoadListener(LoadListener listener) {
        addListener(LoadEvent.class, listener, LoadListener.TILE_LOAD_METHOD);
    }

    /**
     * Removes the tile load listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removeLoadListener(LoadListener listener) {
        removeListener(LoadEvent.class, listener, LoadListener.TILE_LOAD_METHOD);
    }

    /**
     * Adds the tile error listener.
     *
     * @param listener the Listener to be added.
     */
    public void addErrorListener(ErrorListener listener) {
        addListener(ErrorEvent.class, listener, ErrorListener.TILE_ERROR_METHOD);
    }

    /**
     * Removes the tile error listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removeErrorListener(ErrorListener listener) {
        removeListener(ErrorEvent.class, listener, ErrorListener.TILE_ERROR_METHOD);
    }

    /**
     * Adds the tile change listener.
     *
     * @param listener the Listener to be added.
     */
    public void addChangeListener(ChangeListener listener) {
        addListener(ChangeEvent.class, listener, ChangeListener.TILE_CHANGE_METHOD);
    }

    /**
     * Removes the tile change listener.
     *
     * @param listener the Listener to be removed.
     */
    public void removeChangeListener(ChangeListener listener) {
        removeListener(ChangeEvent.class, listener, ChangeListener.TILE_CHANGE_METHOD);
    }

    /**
     * Add a click listener to the component. The listener is called whenever
     * the user clicks inside the component. Depending on the content the event
     * may be blocked and in that case no event is fired.
     * <p>
     * Use {@link #removeClickListener(ClickListener)} to remove the listener.
     *
     * @param listener The listener to add
     */
    public void addClickListener(ClickListener listener) {
        addListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class, listener, ClickListener.clickMethod);
    }

    /**
     * Remove a click listener from the component. The listener should earlier
     * have been added using {@link #addClickListener(ClickListener)}.
     *
     * @param listener The listener to remove
     */
    public void removeClickListener(ClickListener listener) {
        removeListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class, listener);
    }

    /**
     * Interface for listening for a {@link LoadEvent} fired by a
     * {@link ImageSequenceTile}.
     */
    public interface LoadListener extends Serializable {

        Method TILE_LOAD_METHOD = ReflectTools.findMethod(LoadListener.class, "load",
                LoadEvent.class);

        /**
         * Called when a {@link ImageSequenceTile} has been loaded. A reference
         * to the tile is given by {@link LoadEvent#getTile()}.
         *
         * @param event An event containing information about the click.
         */
        void load(LoadEvent event);

    }

    /**
     * Interface for listening for a {@link ErrorEvent} fired by a
     * {@link ImageSequenceTile}.
     */
    public interface ErrorListener extends Serializable {

        Method TILE_ERROR_METHOD = ReflectTools.findMethod(ErrorListener.class, "error",
                ErrorEvent.class);

        /**
         * Called when a {@link ImageSequenceTile} loading failed. A reference
         * to the tile is given by {@link ErrorEvent#getTile()}.
         *
         * @param event An event containing information about the error.
         */
        void error(ErrorEvent event);

    }

    /**
     * Interface for listening for a {@link ChangeEvent} fired by a
     * {@link ImageSequenceTile}.
     */
    public interface ChangeListener extends Serializable {

        Method TILE_CHANGE_METHOD = ReflectTools.findMethod(ChangeListener.class, "change",
                ChangeEvent.class);

        /**
         * Called when an image of {@link ImageSequenceTile} has been changed. A
         * reference to the tile is given by {@link ChangeEvent#getTile()}.
         *
         * @param event An event containing information about the click.
         */
        void change(ChangeEvent event);

    }

    public interface ClickListener extends ConnectorEventListener {

        Method clickMethod = ReflectTools.findMethod(ClickListener.class, "click",
                ClickEvent.class);

        /**
         * Called when a {@link Component} has been clicked. A reference to the
         * component is given by {@link ClickEvent#getComponent()}.
         *
         * @param event An event containing information about the click.
         */
        void click(ClickEvent event);
    }

    public interface HasIndex {
        int getIndex();
    }

    /**
     * Load event. This event is thrown, when the image sequence is loaded.
     */
    public static class LoadEvent extends TimekeepingComponentEvent {

        /**
         * New instance of tile load event.
         *
         * @param source the Source of the event.
         */
        public LoadEvent(long timestamp, ImageSequenceTile source) {
            super(timestamp, source);
        }

        /**
         * Gets the ImageSequenceTile where the event occurred.
         *
         * @return the Source of the event.
         */
        public ImageSequenceTile getTile() {
            return (ImageSequenceTile) getSource();
        }

    }

    /**
     * Error event. This event is thrown, when the image sequence loading
     * failed.
     */
    public static class ErrorEvent extends TimekeepingComponentEvent {

        /**
         * New instance of tile error event.
         *
         * @param source the Source of the event.
         */
        public ErrorEvent(long timestamp, ImageSequenceTile source) {
            super(timestamp, source);
        }

        /**
         * Gets the ImageSequenceTile where the event occurred.
         *
         * @return the Source of the event.
         */
        public ImageSequenceTile getTile() {
            return (ImageSequenceTile) getSource();
        }

    }

    /**
     * Change event. This event is thrown, when the image has changed.
     */
    public static class ChangeEvent extends TimekeepingComponentEvent implements HasIndex {

        private final int index;

        /**
         * New instance of tile change event.
         *
         * @param source the Source of the event.
         */
        public ChangeEvent(long timestamp, ImageSequenceTile source, int index) {
            super(timestamp, source);
            this.index = index;
        }

        /**
         * Gets the ImageSequenceTile where the event occurred.
         *
         * @return the Source of the event.
         */
        public ImageSequenceTile getTile() {
            return (ImageSequenceTile) getSource();
        }

        @Override
        public int getIndex() {
            return index;
        }

    }

    public static class ClickEvent extends MouseEvents.ClickEvent implements HasIndex {

        private final int index;

        public ClickEvent(long timestamp, ImageSequenceTile source, MouseEventDetails mouseEventDetails, int index) {
            super(timestamp, source, mouseEventDetails);
            this.index = index;
        }

        /**
         * Gets the ImageSequenceTile where the event occurred.
         *
         * @return the Source of the event.
         */
        public ImageSequenceTile getTile() {
            return (ImageSequenceTile) getSource();
        }

        @Override
        public int getIndex() {
            return index;
        }

    }

}
