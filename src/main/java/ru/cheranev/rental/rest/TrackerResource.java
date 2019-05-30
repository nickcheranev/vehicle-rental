package ru.cheranev.rental.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.cheranev.rental.common.ResourceNotFoundException;
import ru.cheranev.rental.domain.Tracker;
import ru.cheranev.rental.jpa.TrackerRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * WEB CRUD Tracker Resource
 *
 * @author Cheranev N.
 * created on 29.05.2019.
 */
@RestController
public class TrackerResource {

    @Autowired
    private TrackerRepository repository;

    @GetMapping("/trackers")
    public List<Tracker> getAllTrackers() {
        return repository.findAll();
    }

    @GetMapping("/trackers/{id}")
    public Tracker retrieveTracker(@PathVariable long id) {
        Optional<Tracker> tracker = repository.findById(id);

        if (!tracker.isPresent())
            throw new ResourceNotFoundException("Tracker: id-" + id);

        return tracker.get();
    }

    @PostMapping("/trackers")
    public ResponseEntity<Object> createTracker(@RequestBody Tracker tracker) {
        Tracker savedTracker = repository.save(tracker);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTracker.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/trackers/{id}")
    public ResponseEntity<Object> updateTracker(@RequestBody Tracker tracker, @PathVariable long id) {

        Optional<Tracker> trackerOptional = repository.findById(id);

        if (!trackerOptional.isPresent())
            return ResponseEntity.notFound().build();

        tracker.setId(id);

        repository.save(tracker);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/trackers/{id}")
    public void deleteTracker(@PathVariable long id) {
        repository.deleteById(id);
    }

}
