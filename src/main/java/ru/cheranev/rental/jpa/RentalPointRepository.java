package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cheranev.rental.domain.RentalPoint;

/**
 * Репозиторий Точки проката
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface RentalPointRepository extends JpaRepository<RentalPoint, Long> {
    RentalPoint findByName(String name);
}
