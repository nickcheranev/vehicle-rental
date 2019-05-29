package ru.cheranev.rental.jpa;

//import com.vividsolutions.jts.geom.Point;
//import com.vividsolutions.jts.io.ParseException;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.util.GisUtil;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RentalPointRepositoryTest {

    @Autowired
    private RentalPointRepository rentalPointRepository;

    @Test
    public void getOne() {
        RentalPoint tester = rentalPointRepository.getOne(4L);
        MatcherAssert.assertThat(tester, is(notNullValue()));
    }

    @Test
    @Commit
    public void saveAndGetOne() /*throws ParseException */ {
        String testPoint = "POINT (1 1)"; // Perm 1
        RentalPoint point = new RentalPoint();
        point.setLocation(testPoint /*(Point) GisUtil.wktToGeometry(testPoint)*/); // X - долгота, Y - широта
        RentalPoint point1 = rentalPointRepository.saveAndFlush(point);
        assertThat(testPoint, equalToIgnoringCase(testPoint /*GisUtil.geometryToWkt(point1.getLocation())*/));
    }

}