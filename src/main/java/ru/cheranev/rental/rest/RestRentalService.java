package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cheranev.rental.domain.Customer;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.Status;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.service.RentalService;
import ru.cheranev.rental.service.RentalServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST для основных бизнес операций
 *
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@RestController
@RequestMapping(value = "/rental")
public class RestRentalService implements RentalService {

    @Autowired
    private RentalServiceImpl service;

    @Override
    @GetMapping("/push")
    public VehicleRented pushToRent(@RequestParam Long vehicleId, @RequestParam Long customerId, @RequestParam(required = false) LocalDateTime dateTime) {
        return service.pushToRent(vehicleId, customerId, dateTime);
    }

    @Override
    @GetMapping("/pull")
    public VehicleRented pullFromRent(@RequestParam Long vehicleId, @RequestParam Long rentalPointId, @RequestParam(required = false) LocalDateTime dateTime) {
        return service.pullFromRent(vehicleId, rentalPointId, dateTime);
    }

    @Override
    @GetMapping("/points")
    public List<RentalPoint> findAllRentalPoints() {
        return service.findAllRentalPoints();
    }

    @Override
    @GetMapping("/customers")
    public List<Customer> findAllCustomers() {
        return service.findAllCustomers();
    }

    @Override
    @GetMapping("/rentedbystatus")
    public List<VehicleRented> findAllVehicleRentedByStatus(@RequestParam Status status) {
        return service.findAllVehicleRentedByStatus(status);
    }
}
