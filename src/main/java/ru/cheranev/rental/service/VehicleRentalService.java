package ru.cheranev.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cheranev.rental.common.AppException;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.Vehicle;
import ru.cheranev.rental.domain.VehicleRentHistory;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.jpa.RentalPointRepository;
import ru.cheranev.rental.jpa.VehicleRentHistoryRepository;
import ru.cheranev.rental.jpa.VehicleRentedRepository;
import ru.cheranev.rental.jpa.VehicleRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static ru.cheranev.rental.common.Const.*;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@Service
public class VehicleRentalService {

    @Autowired
    private VehicleRentedRepository vehicleRentedRepository;
    @Autowired
    private VehicleRentHistoryRepository historyRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RentalPointRepository rentalPointRepository;

    /**
     * Выдать в аренду ТС
     * 1. Установить время выдачи
     * 2. Добавить в историю запись о выдаче.
     *
     * @param vehicleId идентификатор ТС
     * @return арендованное ТС
     */
    @Transactional
    public VehicleRented pushToRent(Long vehicleId) {
        // Найдем ТС по ид
        Optional<Vehicle> optVehicle = vehicleRepository.findById(vehicleId);
        if (!optVehicle.isPresent())
            throw new AppException(MSG_NOT_FOUND_VEHICLE_BY_ID);
        Vehicle vehicle = optVehicle.get();
        // последняя запись о сдаче в аренду, содержит пункт проката. где находится ТС
        VehicleRented vehicleRented = vehicleRentedRepository.findFirstByVehicleOrderByIdDesc(vehicle);
        if (vehicleRented == null)
            throw new AppException(MSG_NOT_FOUND_RECORD_OF_VEHICLE_RENTAL);
        // Если ТС  в аренде - выдать исключение
        if (vehicleRented.isRented())
            throw new AppException(MSG_VEHICLE_NOT_AVAILABLE_FOR_RENTAL);
        VehicleRented rented = new VehicleRented();
        // Установить время выдачи
        rented.setBeginRentTime(LocalDateTime.now());
        rented.setVehicle(vehicle);
        // пункт проката выдачи
        rented.setBeginRentalPoint(vehicleRented.getEndRentalPoint());
        vehicleRentedRepository.save(rented);
        // Добавить в историю запись о выдаче в прокат
        VehicleRentHistory history = new VehicleRentHistory();
        history.setVehicleRented(rented);
        history.setEventTime(LocalDateTime.now());
        history.setLocation(vehicleRented.getBeginRentalPoint().getLocation());
        historyRepository.save(history);
        return rented;
    }

    /**
     * Получить ТС из аренды
     *
     * @param vehicleId     ТС
     * @param rentalPointId точка проката
     * @return ТС полученное из аренды
     */
    @Transactional
    public VehicleRented pullFromRent(Long vehicleId, Long rentalPointId) {
        // Найдем ТС по ид
        Optional<Vehicle> optVehicle = vehicleRepository.findById(vehicleId);
        if (!optVehicle.isPresent())
            throw new AppException(MSG_NOT_FOUND_VEHICLE_BY_ID);
        Vehicle vehicle = optVehicle.get();
        Optional<RentalPoint> optRentalPoint = rentalPointRepository.findById(rentalPointId);
        if (!optRentalPoint.isPresent())
            throw new AppException(MSG_NOT_FOUND_RENTAL_POINT_BY_ID);
        RentalPoint rentalPoint = optRentalPoint.get();
        // Прокатный пункт прибытия ТС
        VehicleRented rented = vehicleRentedRepository.findFirstByVehicleOrderByIdDesc(vehicle);
        rented.setEndRentTime(LocalDateTime.now());
        rented.setEndRentalPoint(rentalPoint);
        vehicleRentedRepository.save(rented);
        // Добавить в историю запись о приеме из проката
        VehicleRentHistory history = new VehicleRentHistory();
        history.setVehicleRented(rented);
        history.setEventTime(LocalDateTime.now());
        history.setLocation(rentalPoint.getLocation());
        historyRepository.save(history);
        return rented;
    }

    /**
     * Получить список ТС доступных к выдаче в Точке проката
     *
     * @param rentalPoint точка проката
     * @return список доступных ТС
     */
    public Set<VehicleRented> getAvailableForRent(RentalPoint rentalPoint) {
        return vehicleRentedRepository.getAvailableOnRentalPoint(rentalPoint);
    }
}
