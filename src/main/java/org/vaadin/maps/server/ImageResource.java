package org.vaadin.maps.server;

import com.vaadin.server.ExternalResource;

import java.net.URL;

/**
 * @author Kamil Morong
 */
public class ImageResource extends ExternalResource implements TileResource {

    /**
     * Creates a new component for downloading directly from given URL.
     *
     * @param imageURL the image URL.
     */
    public ImageResource(URL imageURL) {
        super(imageURL);
    }

    /**
     * Creates a new component for downloading directly from given URL.
     *
     * @param imageURL the image URL.
     */
    public ImageResource(String imageURL) {
        super(imageURL);
    }

}
