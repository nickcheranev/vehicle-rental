package ru.cheranev.rental.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.VehicleRented;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "ru.cheranev.rental.service")
public class VehicleRentedServiceTest {

    @Autowired
    private RentalServiceImpl vehicleRentedService;

    @Test
    public void pushToRent() {
        VehicleRented rented = vehicleRentedService.pushToRent(14L, 1L, null);
        assertThat(rented, is(notNullValue()));
        RentalPoint currentRentalPoint =  rented.getBeginRentalPoint();
        assertThat(currentRentalPoint, is(notNullValue()));
    }

    @Test
    public void pullFromRent() {
        VehicleRented rented = vehicleRentedService.pullFromRent(14L, 4L, null);
        assertThat(rented, is(notNullValue()));
    }
}