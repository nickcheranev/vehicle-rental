package ru.cheranev.rental.util;

import com.vividsolutions.jts.io.ParseException;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author Cheranev N.
 * created on 25.05.2019.
 */
public class GisUtilTest {

    @Test
    public void randomNextPoint() throws ParseException {

        String coord = "POINT (56.252489 58.020296)";
        System.out.println(coord);

        for (int i = 0; i < 10; i++) {
            coord = GisUtil.randomNextPoint(coord);
            System.out.println(coord);
        }
    }

    @Test
    public void rndSing() {
        int s = 0;
        for (int i = 0; i < 1000; i++) {
            s += GisUtil.rndSign();
        }
        Assert.isTrue(s > -100 && s < 100);
    }
}