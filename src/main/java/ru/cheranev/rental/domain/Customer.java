package ru.cheranev.rental.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Справочник Заказчики (Арендатор)
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer")
    @SequenceGenerator(name = "seq_customer", allocationSize = 1)
    private Long id;
    /**
     * ФИО заказчика
     */
    private String name;
}
