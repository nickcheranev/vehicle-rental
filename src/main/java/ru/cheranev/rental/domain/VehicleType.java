package ru.cheranev.rental.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Справочник: Тип транспортного средства
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@NoArgsConstructor
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicle_type")
    @SequenceGenerator(name = "seq_vehicle_type", allocationSize = 1)
    private Long id;
    /**
     * Наименование типа ТС
     */
    private String name;

    public VehicleType(String name) {
        this.name = name;
    }
}
