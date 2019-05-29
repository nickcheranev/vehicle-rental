package ru.cheranev.rental.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Поддержка GIS
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public class GisUtil {

    private final static double lonMin = .0449661; // 5 km
    private final static double lonMax = .0899385; // 10 km
    private final static double latMin = .0848550; // 5 km
    private final static double latMax = .1697090; // 10 km

    /**
     * Генерация следующей координаты GPS трекера исходя из предыдущей.
     * Новая координата находится от предыдущей от 5 до 15 км
     *
     * @param wkt предыдущие координата
     * @return новые координаты
     * @throws ParseException ParseException
     */
    public static String randomNextPoint(String wkt) throws ParseException {
        double dLon = ThreadLocalRandom.current().nextDouble(lonMin, lonMax);
        double dLat = ThreadLocalRandom.current().nextDouble(latMin, latMax);
        Point point = (Point) new WKTReader().read(wkt);
        Coordinate coordinate = point.getCoordinate();
        Coordinate newCoordinate = new Coordinate();
        newCoordinate.x = round(coordinate.x + dLon * rndSign(), 6);
        newCoordinate.y = round(coordinate.y + dLat * rndSign(), 6);
        return new WKTWriter().write(new GeometryFactory().createPoint(newCoordinate));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int rndSign() {
        int n = ThreadLocalRandom.current().nextInt(-1, 1);
        return n == 0 ? 1 : n;
    }
}
