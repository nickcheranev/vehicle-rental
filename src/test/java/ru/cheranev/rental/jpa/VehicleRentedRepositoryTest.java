package ru.cheranev.rental.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.cheranev.rental.domain.VehicleRented;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VehicleRentedRepositoryTest {

    @Autowired
    private VehicleRentedRepository vehicleRentedRepository;

    @Test
    public void findAllByParkedIsTrue() {
        Set<VehicleRented> tester = vehicleRentedRepository.findAllByParkedIsTrue();
        assertThat(tester, is(not(empty())));
    }
}