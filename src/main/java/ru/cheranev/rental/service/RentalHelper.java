package ru.cheranev.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cheranev.rental.domain.RentalPoint;
import ru.cheranev.rental.jpa.RentalPointRepository;

/**
 * @author Cheranev N.
 * created on 19.05.2019.
 */
@Service
public class RentalHelper {

    @Autowired
    private RentalPointRepository rentalPointRepository;

    public RentalPoint getHomeRentalPoint() {
        return rentalPointRepository.findByName("Пермь №1");
    }
}
