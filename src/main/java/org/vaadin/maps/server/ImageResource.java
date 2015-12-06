/**
 * 
 */
package org.vaadin.maps.server;

import java.net.URL;

import com.vaadin.server.ExternalResource;

/**
 * @author Kamil Morong
 *
 */
@SuppressWarnings("serial")
public class ImageResource extends ExternalResource implements TileResource {

	/**
	 * Creates a new component for downloading directly from given URL.
	 * 
	 * @param imageURL
	 *            the image URL.
	 */
	public ImageResource(URL imageURL) {
		super(imageURL);
	}

	/**
	 * Creates a new component for downloading directly from given URL.
	 * 
	 * @param imageURL
	 *            the image URL.
	 */
	public ImageResource(String imageURL) {
		super(imageURL);
	}

}
