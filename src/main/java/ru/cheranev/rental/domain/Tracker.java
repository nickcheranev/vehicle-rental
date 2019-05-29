package ru.cheranev.rental.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Справочник: Трекеры
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tracker")
    @SequenceGenerator(name = "seq_tracker", allocationSize = 1)
    private Long id;
    /**
     * Идентификатор трекера
     */
    private String identifier;
}
