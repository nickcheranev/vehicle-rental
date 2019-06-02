package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cheranev.rental.common.ResourceNotFoundException;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.jpa.RentalPointRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST Vehicle Resource
 *
 * @author Cheranev N.
 * created on 29.05.2019.
 */
@RestController
public class RentalPointResource {

    @Autowired
    private RentalPointRepository repository;

    @GetMapping("/points")
    public List<RentalPoint> getAllRentalPoints() {
        return repository.findAll();
    }

    @GetMapping("/points/{id}")
    public RentalPoint retrieveVehicle(@PathVariable long id) {
        Optional<RentalPoint> point = repository.findById(id);

        if (!point.isPresent())
            throw new ResourceNotFoundException("Vehicle: id-" + id);

        return point.get();
    }

    // {"name":"Пермь","location":"Point( 1 1)"}}
    @PostMapping("/points")
    public ResponseEntity<Object> createPoint(@RequestBody RentalPoint point) {
        RentalPoint savedPoint = repository.save(point);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPoint.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    // {"name":"Пермь","location":"Point( 1 1)"}}
    @PutMapping("/points/{id}")
    public ResponseEntity<Object> updatePoint(@RequestBody RentalPoint point, @PathVariable long id) {

        Optional<RentalPoint> optionalPoint = repository.findById(id);

        if (!optionalPoint.isPresent())
            return ResponseEntity.notFound().build();

        point.setId(id);

        repository.save(point);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/points/{id}")
    public void deletePoint(@PathVariable long id) {
        repository.deleteById(id);
    }

}
