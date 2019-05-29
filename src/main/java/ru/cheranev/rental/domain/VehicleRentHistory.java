package ru.cheranev.rental.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
public class VehicleRentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicle_rent_history")
    @SequenceGenerator(name = "seq_vehicle_rent_history", allocationSize = 1)
    private Long id;
    /**
     * ТС в аренде
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_rented_history_fk1"))
    private VehicleRented vehicleRented;
    /**
     * Дата/время записи
     */
    private LocalDateTime eventTime;
    /**
     * Координаты ТС (WKT)
     */
    private String location;

}
