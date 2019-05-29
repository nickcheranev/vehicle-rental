package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.Vehicle;
import ru.cheranev.rental.domain.VehicleRented;

import java.util.Set;

/**
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

    // VehicleRented findFirstByIdOrderByIdDesc();

    VehicleRented findFirstByVehicle(Vehicle vehicle);

    /**
     * Найти актуальную запись о ТС
     * @param vehicle ТС
     * @return запись о ТС в точке ее текущего расположения
     */
    VehicleRented findFirstByVehicleOrderByIdDesc(Vehicle vehicle);

    /**
     * Получить перечень ТС доступных к выдаче в точке проката
     *
     * @param rentalPoint точка проката
     * @return доступные ТС
     */
    @Query("select r from VehicleRented r where r.id in (select max(q.id) from VehicleRented q where q.endRentalPoint = ?1 group by vehicle) and r.endRentTime is not null")
    Set<VehicleRented> getAvailableOnRentalPoint(RentalPoint rentalPoint);

    /**
     * Получить все доступные выдаче
     * @return список ТС
     */
    Set<VehicleRented> findAllByParkedIsTrue();
}
