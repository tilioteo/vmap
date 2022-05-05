package org.vaadin.maps.client;

import java.util.Date;

/**
 * @author Kamil Morong
 */
public class DateUtility {

    public static long getTimestamp() {
        Date date = new Date();
        return date.getTime();
    }

}
