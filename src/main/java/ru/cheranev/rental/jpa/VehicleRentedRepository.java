package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.cheranev.rental.domain.Status;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.Vehicle;
import ru.cheranev.rental.domain.VehicleRented;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Репозиторий ТС в прокате
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface VehicleRentedRepository extends JpaRepository<VehicleRented, Long> {
    /**
     * Получить перечень ТС в аренде в точке проката
     *
     * @param rentalPoint точка проката
     * @return ТС
     */
    Set<VehicleRented> getAllByBeginRentTimeIsNotNullAndBeginRentalPoint(RentalPoint rentalPoint);

    /**
     * Получить перечень ТС в парке в точке проката
     *
     * @param rentalPoint точка проката
     * @return ТС
     */
    Set<VehicleRented> getAllByBeginRentTimeIsNullAndEndRentalPoint(RentalPoint rentalPoint);

    /**
     * Получить перечень ТС доступных к выдаче в точке проката
     *
     * @param rentalPoint точка проката
     * @return доступные ТС
     */
    @Query("select r from VehicleRented r where r.id in (select max(q.id) from VehicleRented q where q.endRentalPoint = ?1 group by vehicle) and r.endRentTime is not null")
    Set<VehicleRented> getAvailableOnRentalPoint(RentalPoint rentalPoint);

    /**
     * Получить все соответствующие определенному статусу
     * @return список ТС
     */
    List<VehicleRented> findAllByStatus(Status status);

    /**
     * Наити ТС с определенным статусом
     *
     * @param vehicle ТС
     * @return запись о прокате ТС с определенным статусом
     */
    Optional<VehicleRented> findByVehicleAndStatus(Vehicle vehicle, Status status);
}
