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

import java.util.Set;

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
    private VehicleRentalService vehicleRentedService;

    @Test
    public void pushToRent() {
        VehicleRented rented = vehicleRentedService.pushToRent(14L);
        assertThat(rented, is(notNullValue()));
        RentalPoint currentRentalPoint =  rented.getBeginRentalPoint();
        assertThat(currentRentalPoint, is(notNullValue()));
    }

    @Test
    public void pullFromRent() {
        VehicleRented rented = vehicleRentedService.pullFromRent(14L, 4L);
        assertThat(rented, is(notNullValue()));
    }

    @Test
    public void getAvailableForRent() {
        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(4L);
        Set<VehicleRented> vehicleRentedSet = vehicleRentedService.getAvailableForRent(rentalPoint);
        assertThat(vehicleRentedSet, hasSize(equalTo(1)));
    }
}