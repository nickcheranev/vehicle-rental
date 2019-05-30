package ru.cheranev.rental.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Справочник: Пункты проката транспортных средств
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@NoArgsConstructor
public class RentalPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rental_point")
    @SequenceGenerator(name = "seq_rental_point", allocationSize = 1)
    private long id;
    /**
     * Наименование или код пункта проката
     */
    private String name;
    /**
     * Координаты WKT
     */
    private String location;

    public RentalPoint(String name, String location) {
        this.name = name;
        this.location = location;
    };
}
