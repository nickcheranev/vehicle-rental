package ru.cheranev.rental.service;

import ru.cheranev.rental.domain.Customer;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.Status;
import ru.cheranev.rental.domain.VehicleRented;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс бизнес операций
 *
 * @author Cheranev N.
 * created on 02.06.2019.
 */
public interface RentalService {
    /**
     * Выдать ТС в аренду
     *
     * @param vehicleId  идентификатор ТС
     * @param customerId арендатор
     * @param dateTime   дата/время выдачи
     * @return запись об аренде ТС
     */
    VehicleRented pushToRent(Long vehicleId, Long customerId, LocalDateTime dateTime);

    /**
     * Получить ТС из аренды
     *
     * @param vehicleId     ТС
     * @param rentalPointId точка проката приема из аренды
     * @param dateTime      дата/время приема
     * @return запись об аренде ТС
     */
    VehicleRented pullFromRent(Long vehicleId, Long rentalPointId, LocalDateTime dateTime);

    /**
     * Получить все точки проката
     *
     * @return список точек проката
     */
    List<RentalPoint> findAllRentalPoints();

    /**
     * Получить всех арендаторов
     *
     * @return список арендаторов
     */
    List<Customer> findAllCustomers();

    /**
     * Получить все ТС с определенным статусом
     *
     * @param status целевой статус
     * @return список ТС
     */
    List<VehicleRented> findAllVehicleRentedByStatus(Status status);
}
