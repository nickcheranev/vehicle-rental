package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cheranev.rental.domain.Tracker;

import java.util.Optional;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface TrackerRepository extends JpaRepository<Tracker, Long> {
    Optional<Tracker> findFirstByOrderByIdDesc();
}
