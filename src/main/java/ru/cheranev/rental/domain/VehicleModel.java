package ru.cheranev.rental.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
@NoArgsConstructor
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicle_model")
    @SequenceGenerator(name = "seq_vehicle_model", allocationSize = 1)
    private Long id;
    /**
     * Наименование модели ТС
     */
    private String name;
    /**
     * Тип транспортного средства
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_model_fk1"))
    private VehicleType vehicleType;

    public VehicleModel(String name, VehicleType vehicleType) {
        this.name = name;
        this.vehicleType = vehicleType;
    }
}
