package org.vaadin.maps.ui.tile;

import com.vaadin.ui.AbstractComponent;
import org.vaadin.maps.server.TileResource;
import org.vaadin.maps.shared.ui.AbstractTileState;

/**
 * @author Kamil Morong
 */
public abstract class AbstractTile extends AbstractComponent implements Tile {

    @Override
    protected AbstractTileState getState() {
        return (AbstractTileState) super.getState();
    }

    /**
     * Get the object source resource.
     *
     * @return the source
     */
    protected TileResource getTileResource() {
        return (TileResource) getResource(AbstractTileState.SOURCE_RESOURCE);
    }

    /**
     * Sets the object source resource. The dimensions are assumed if possible.
     * The type is guessed from resource.
     *
     * @param source the source to set.
     */
    protected void setTileResource(TileResource source) {
        setResource(AbstractTileState.SOURCE_RESOURCE, source);
    }

}
