package ru.cheranev.rental.domain;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Отчет История эксплуатации ТС
 *
 * @author Cheranev N.
 * created on 25.05.2019.
 */
@Entity
@Data
@Table(name = "RENTAL_HISTORY_VIEW")
@Immutable
public class ReportRentalHistory {

    @EmbeddedId
    private ReportRentalHistoryId id;

    private String typeName;
    private String modelName;
    private String regNumber;
    private String customerName;
    private LocalDateTime beginRentTime;
    private LocalDateTime endRentTime;
    private String history;
}
