package ru.cheranev.rental.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Реестр: ТС в аренде
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
@Entity
@Data
public class VehicleRented {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehicle_rented")
    @SequenceGenerator(name = "seq_vehicle_rented", allocationSize = 1)
    private Long id;
    /**
     * Заказчик
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_rented_fk1"))
    private Customer customer;
    /**
     * Эксплуатируемое ТС
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_rented_fk2"))
    private Vehicle vehicle;
    /**
     * Точка проката выдачи
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_rented_fk3"))
    private RentalPoint beginRentalPoint;
    /**
     * Точка проката получения ТС
     */
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name = "vehicle_rented_fk4"))
    private RentalPoint endRentalPoint;
    /**
     * Дата/время выдачи в прокат
     */
    private LocalDateTime beginRentTime;
    /**
     * Дата/время возврата
     */
    private LocalDateTime endRentTime;

    /**
     * Признак парковки ТС в точке проката endRentalPoint
     * Для бизнес реализации необходимо заменить на статусы ТС, например: в парке, в аренде, на обслуживании, относится к другому прокату и т.п.
     */
    boolean parked;
}
