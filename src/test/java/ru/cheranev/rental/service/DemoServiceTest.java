package ru.cheranev.rental.service;

import com.vividsolutions.jts.io.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.cheranev.rental.domain.VehicleModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Cheranev N.
 * created on 26.05.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTest {

    private static final Integer RENTAL_POINT_COLLECTION_SIZE = 10;
    private static final Integer VEHICLE_MODEL_COLLECTION_SIZE = 10;
    private static final Integer VEHICLE_COLLECTION_SIZE = 100;
    private static final Integer CUSTOMER_COLLECTION_SIZE = 10;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DemoService demoService;

    @Test
    public void clearAll() {
        demoService.clearAll();
    }

    @Test
    public void populateDemoExample() {

    }

    @Test
    public void populateVehicleModel() {
        demoService.clearAll();
        demoService.populateVehicleModel();
        Integer count = jdbcTemplate.queryForObject("SELECT count(id) FROM vehicle_model", Integer.class);
        assertThat(count, equalTo(VEHICLE_MODEL_COLLECTION_SIZE));
    }

    @Test
    public void populateVehicle() {
        demoService.clearAll();
        demoService.populateVehicleType();
        demoService.populateVehicleModel();
        demoService.populateVehicle();
        Integer count = jdbcTemplate.queryForObject("SELECT count(id) FROM vehicle", Integer.class);
        assertThat(count, equalTo(VEHICLE_COLLECTION_SIZE));
    }

    @Test
    public void getNextVehicleModel() {
        demoService.clearAll();
        demoService.populateVehicleType();
        demoService.populateVehicleModel();
        Set<VehicleModel> vehicleModelSet = new HashSet<>();
        for(int i=0; i<100; i++)
            vehicleModelSet.add(demoService.getRandomVehicleModel());
        assertThat(vehicleModelSet, hasSize(VEHICLE_MODEL_COLLECTION_SIZE));
    }

    @Test
    public void populateRentalPoint() {
        demoService.clearAll();
        demoService.populateRentalPoint();
        Integer count = jdbcTemplate.queryForObject("SELECT count(id) FROM rental_point", Integer.class);
        assertThat(count, equalTo(RENTAL_POINT_COLLECTION_SIZE));
    }

    @Test
    public void populateCustomer() {
        demoService.clearAll();
        demoService.populateCustomer();
        Integer count = jdbcTemplate.queryForObject("SELECT count(id) FROM customer", Integer.class);
        assertThat(count, equalTo(CUSTOMER_COLLECTION_SIZE));
    }

    @Test
    public void populateVehicleRented() throws ParseException {
        demoService.clearAll();
        demoService.populateVehicleType();
        demoService.populateVehicleModel();
        demoService.populateRentalPoint();
        demoService.populateCustomer();
        demoService.populateVehicle();

        demoService.populateVehicleRented();
    }
}