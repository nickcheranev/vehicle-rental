package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cheranev.rental.common.ResourceNotFoundException;
import ru.cheranev.rental.domain.Vehicle;
import ru.cheranev.rental.jpa.VehicleRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * WEB CRUD Vehicle Resource
 *
 * @author Cheranev N.
 * created on 29.05.2019.
 */
@RestController
public class VehicleResource {

    @Autowired
    private VehicleRepository repository;

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle retrieveVehicle(@PathVariable long id) {
        Optional<Vehicle> vehicle = repository.findById(id);

        if (!vehicle.isPresent())
            throw new ResourceNotFoundException("Vehicle: id-" + id);

        return vehicle.get();
    }

    // {"regNumber":"А001АА59","vehicleModel":{"id":"1"},"tracker":{"id":"1"}}
    @PostMapping("/vehicles")
    public ResponseEntity<Object> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = repository.save(vehicle);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVehicle.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    // {"regNumber":"А001АА59","vehicleModel":{"id":"1"},"tracker":{"id":"1"}}
    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Object> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable long id) {

        Optional<Vehicle> vehicleOptional = repository.findById(id);

        if (!vehicleOptional.isPresent())
            return ResponseEntity.notFound().build();

        vehicle.setId(id);

        repository.save(vehicle);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable long id) {
        repository.deleteById(id);
    }

}
