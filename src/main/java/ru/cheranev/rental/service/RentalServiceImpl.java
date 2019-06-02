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

import static ru.cheranev.rental.common.Const.*;

/**
 * Сервис бизнес операций
 *
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@Service
public class RentalServiceImpl implements RentalService {

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
     * 1. Установить заказчика
     * 2. Установить время выдачи
     * 3. Добавить в историю запись о выдаче.
     *
     * @param vehicleId идентификатор ТС
     * @return арендованное ТС
     */
    @Transactional
    public VehicleRented pushToRent(Long vehicleId, Long customerId, LocalDateTime beginDateTime) {
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

        // ТС в парке
        Optional<VehicleRented> optionalVehicleRented = vehicleRentedRepository.findByVehicleAndStatus(vehicle, Status.PARKED);
        if (!optionalVehicleRented.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_RECORD_OF_VEHICLE_RENTAL_IN_PARK);
        VehicleRented oldRented = optionalVehicleRented.get();

        // Новая запись об аренде
        VehicleRented newRented = new VehicleRented();
        // Установить время ТС, выдачи, заказчика
        newRented.setBeginRentTime(beginDateTime == null ? LocalDateTime.now() : beginDateTime);
        newRented.setVehicle(vehicle);
        newRented.setCustomer(customer);
        // пункт проката выдачи
        newRented.setBeginRentalPoint(oldRented.getEndRentalPoint());
        newRented.setStatus(Status.RENTED);
        vehicleRentedRepository.save(newRented);

        // закрыть предыдущую запись об аренде
        oldRented.setStatus(Status.CLOSED);
        vehicleRentedRepository.save(oldRented);

        // Добавить в историю запись о выдаче в прокат
        VehicleRentHistory history = new VehicleRentHistory();
        history.setVehicleRented(newRented);
        history.setEventTime(LocalDateTime.now());
        history.setLocation(oldRented.getEndRentalPoint().getLocation());
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
    public VehicleRented pullFromRent(Long vehicleId, Long rentalPointId, LocalDateTime endDateTime) {

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

        // Запись об аренде ТС
        Optional<VehicleRented> optionalRented = vehicleRentedRepository.findByVehicleAndStatus(vehicle, Status.RENTED);
        if (!optionalRented.isPresent())
            throw new ResourceNotFoundException(MSG_NOT_FOUND_RECORD_OF_VEHICLE_RENTAL_IN_RENT);
        VehicleRented rented = optionalRented.get();

        rented.setEndRentTime(endDateTime == null ? LocalDateTime.now() : endDateTime);
        rented.setEndRentalPoint(rentalPoint);
        rented.setStatus(Status.PARKED);
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
     * Получить все ТС с определенным статусом
     *
     * @param status статус ТС
     * @return список ТС
     */
    public List<VehicleRented> findAllVehicleRentedByStatus(Status status) {
        return vehicleRentedRepository.findAllByStatus(status);
    }

    /**
     * Получить всех зарегистрированных арендаторов
     *
     * @return список арендаторов
     */
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Получить все пункты проката
     *
     * @return список пунктов проката
     */
    public List<RentalPoint> findAllRentalPoints() {
        return rentalPointRepository.findAll();
    }
}
