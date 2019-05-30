package ru.cheranev.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cheranev.rental.common.ResourceNotFoundException;
import ru.cheranev.rental.domain.*;
import ru.cheranev.rental.jpa.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
    private CustomerRepository customerRepository;
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
    public VehicleRented pushToRent(Long vehicleId, Long customerId) {
        // Найдем ТС по ид
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (!optionalVehicle.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_VEHICLE_BY_ID);
        Vehicle vehicle = optionalVehicle.get();

        // Найдем Арендатора по ид
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_CUSTOMER_BY_ID);
        Customer customer = optionalCustomer.get();

        // последняя запись о сдаче в аренду, содержит пункт проката. где находится ТС
        Optional<VehicleRented> optionalVehicleRented = vehicleRentedRepository.findByVehicleAndParkedIsTrue(vehicle);
        if (!optionalVehicleRented.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_RECORD_OF_VEHICLE_RENTAL_IN_PARK);
        VehicleRented oldRented = optionalVehicleRented.get();

        // Новая запись об аренде
        VehicleRented newRented = new VehicleRented();
        // Установить время ТС, выдачи, заказчика
        newRented.setBeginRentTime(LocalDateTime.now());
        newRented.setVehicle(vehicle);
        newRented.setCustomer(customer);
        // пункт проката выдачи
        newRented.setBeginRentalPoint(oldRented.getEndRentalPoint());
        newRented.setParked(false);
        vehicleRentedRepository.save(newRented);

        // для предыдущей записи об аренде снять признак парковки
        oldRented.setParked(false);
        vehicleRentedRepository.save(oldRented);

        // Добавить в историю запись о выдаче в прокат
        VehicleRentHistory history = new VehicleRentHistory();
        history.setVehicleRented(newRented);
        history.setEventTime(LocalDateTime.now());
        history.setLocation(oldRented.getBeginRentalPoint().getLocation());
        historyRepository.save(history);
        return newRented;
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
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (!optionalVehicle.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_VEHICLE_BY_ID);
        Vehicle vehicle = optionalVehicle.get();

        // Пункт проката прибытия
        Optional<RentalPoint> optionalRentalPoint = rentalPointRepository.findById(rentalPointId);
        if (!optionalRentalPoint.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_RENTAL_POINT_BY_ID);
        RentalPoint rentalPoint = optionalRentalPoint.get();

        // Последняя запись об аренде ТС
        Optional<VehicleRented> optionalRented = getLastByVehicleAndParkedIsFalse(vehicle);
        if (!optionalRented.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_RECORD_OF_VEHICLE_RENTAL_IN_RENT);
        VehicleRented rented = optionalRented.get();

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
     * Получить последнюю запись ТС в аренде
     *
     * @param vehicle ТС
     * @return последняя запись об аренде ТС
     */
    private Optional<VehicleRented> getLastByVehicleAndParkedIsFalse(Vehicle vehicle) {
        List<VehicleRented> rented = vehicleRentedRepository.findByVehicleAndParkedIsFalseOrderByIdDesc(vehicle);
        return rented.size() > 0 ? Optional.of(rented.get(0)) : Optional.empty();
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
