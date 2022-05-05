package org.vaadin.maps.client.ui;

/**
 * @author Kamil Morong
 */
public class VControlLayer extends AbstractLayer {

    /**
     * Class name, prefix in styling
     */
    public static final String CLASSNAME = "v-controllayer";

    public VControlLayer() {
        super();
        setStylePrimaryName(CLASSNAME);
        setVisible(false);
    }

}
