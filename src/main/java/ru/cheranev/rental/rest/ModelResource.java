package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cheranev.rental.domain.Vehicle;
import ru.cheranev.rental.domain.VehicleModel;
import ru.cheranev.rental.jpa.VehicleModelRepository;
import ru.cheranev.rental.jpa.VehicleRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * WEB CRUD Vehicle Model Resource
 *
 * @author Cheranev N.
 * created on 29.05.2019.
 */
@RestController
public class ModelResource {

    @Autowired
    private VehicleModelRepository repository;

    @GetMapping("/models")
    public List<VehicleModel> getAllVehicleModels() {
        return repository.findAll();
    }

    @GetMapping("/models/{id}")
    public VehicleModel retrieveModels(@PathVariable long id) {
        Optional<VehicleModel> vehicleModel = repository.findById(id);

        if (!vehicleModel.isPresent())
            throw new ResourceNotFoundException("Vehicle Model: id-" + id);

        return vehicleModel.get();
    }

    // {"name":"Audy","vehicleType":{"id":"1"}}
    @PostMapping("/models")
    public ResponseEntity<Object> createModel(@RequestBody VehicleModel vehicleModel) {
        VehicleModel savedModel = repository.save(vehicleModel);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedModel.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    // {"name":"Audy","vehicleType":{"id":"1"}}
    @PutMapping("/models/{id}")
    public ResponseEntity<Object> updateModel(@RequestBody VehicleModel model, @PathVariable long id) {

        Optional<VehicleModel> optionalModel = repository.findById(id);

        if (!optionalModel.isPresent())
            return ResponseEntity.notFound().build();

        model.setId(id);

        repository.save(model);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/models/{id}")
    public void deleteModel(@PathVariable long id) {
        repository.deleteById(id);
    }

}
