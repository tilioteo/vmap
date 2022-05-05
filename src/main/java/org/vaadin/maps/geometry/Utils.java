package org.vaadin.maps.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;
import org.vaadin.maps.server.LonLat;
import org.vaadin.maps.server.Pixel;
import org.vaadin.maps.server.ViewWorldTransform;

/**
 * @author Kamil Morong
 */
public class Utils {

    public static String geometryToWKBHex(Geometry geometry) {
        if (geometry != null) {
            WKBWriter writer = new WKBWriter();
            byte[] bs = writer.write(geometry);

            return WKBWriter.toHex(bs);
        }

        return null;
    }

    public static Geometry wkbHexToGeometry(String wkb) throws ParseException {
        if (wkb != null) {
            WKBReader reader = new WKBReader();
            byte[] bs = WKBReader.hexToBytes(wkb);

            return reader.read(bs);
        }

        return null;
    }

    public static Geometry wktToGeometry(String wkt) throws ParseException {
        if (wkt != null) {
            WKTReader reader = new WKTReader();

            return reader.read(wkt);
        }

        return null;
    }

    public static void transformWorldToView(Geometry geometry, ViewWorldTransform viewWorldTransform) {
        Coordinate[] coordinates = geometry.getCoordinates();
        for (Coordinate coordinate : coordinates) {
            Pixel pixel = viewWorldTransform.worldToView(coordinate.x, coordinate.y);
            if (pixel != null) {
                coordinate.x = pixel.getX();
                coordinate.y = pixel.getY();
            }
        }
    }

    public static void transformViewToWorld(Geometry geometry, ViewWorldTransform viewWorldTransform) {
        Coordinate[] coordinates = geometry.getCoordinates();
        for (Coordinate coordinate : coordinates) {
            LonLat lonLat = viewWorldTransform.viewToWorld(coordinate.x, coordinate.y);
            if (lonLat != null) {
                coordinate.x = lonLat.getLon();
                coordinate.y = lonLat.getLat();
            }
        }
    }

}
