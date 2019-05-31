package ru.cheranev.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Справочник Конкретное транспортное средство
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id", name = "vehicle_pk"))
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicle")
    @SequenceGenerator(name = "seq_vehicle", allocationSize = 1)
    private Long id;
    /**
     * Гос. номер
     */
    private String regNumber;
    /**
     * Модель ТС
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_fk1"))
    private VehicleModel vehicleModel;
    /**
     * GPS трекер
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_fk2"))
    private Tracker tracker;

    public Vehicle(String regNumber, VehicleModel vehicleModel, Tracker tracker) {
        this.regNumber = regNumber;
        this.vehicleModel = vehicleModel;
        this.tracker = tracker;
    }
}
