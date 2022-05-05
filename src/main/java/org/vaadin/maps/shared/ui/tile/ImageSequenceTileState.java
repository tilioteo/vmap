package org.vaadin.maps.shared.ui.tile;

import com.vaadin.shared.communication.URLReference;
import org.vaadin.maps.shared.ui.AbstractTileState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Morong
 */
public class ImageSequenceTileState extends AbstractTileState {
    public List<URLReference> sources = new ArrayList<>();
    public List<String> sourceTypes = new ArrayList<>();
    public int index = 0;

    {
        primaryStyleName = "v-imagesequencetile";
    }

}
