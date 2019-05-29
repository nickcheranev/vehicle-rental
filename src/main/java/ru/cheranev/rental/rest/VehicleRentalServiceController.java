package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.domain.VehicleRented;
import ru.cheranev.rental.service.RentalHelper;
import ru.cheranev.rental.service.VehicleRentalService;

import java.util.Set;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@RestController
@RequestMapping(value = "/rental")
public class VehicleRentalServiceController {

    @Autowired
    private VehicleRentalService vehicleRentedService;
    @Autowired
    private RentalHelper rentalHelper;

    @GetMapping("/push")
    @ResponseBody
    public VehicleRented pushToRent(@RequestParam Long vehicleId) {
        return vehicleRentedService.pushToRent(vehicleId);
    }

    @GetMapping("/pull")
    @ResponseBody
    public VehicleRented pullFromRent(@RequestParam Long vehicleId, @RequestParam Long rentalPointId) {
        return vehicleRentedService.pullFromRent(vehicleId, rentalPointId);
    }

    @GetMapping("/available")
    @ResponseBody
    public Set<VehicleRented> getAvailable() {
        RentalPoint home = rentalHelper.getHomeRentalPoint();
        return vehicleRentedService.getAvailableForRent(home);
    }

}
