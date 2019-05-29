package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cheranev.rental.domain.VehicleType;

import java.util.Optional;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByName(String type);
}
