package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cheranev.rental.domain.VehicleModel;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
}
