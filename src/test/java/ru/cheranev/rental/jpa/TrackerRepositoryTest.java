package ru.cheranev.rental.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import ru.cheranev.rental.domain.Tracker;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

/**
 * @author Cheranev N.
 * created on 26.05.2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrackerRepositoryTest {

    @Autowired
    private TrackerRepository trackerRepository;

    @Test
    public void findFirstByOrderById() {
        Optional<Tracker> last = trackerRepository.findFirstByOrderByIdDesc();
        Assert.isTrue(last.isPresent());
    }
}