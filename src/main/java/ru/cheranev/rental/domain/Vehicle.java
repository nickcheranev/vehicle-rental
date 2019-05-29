package ru.cheranev.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id", name = "vehicle_pk"))
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
}
