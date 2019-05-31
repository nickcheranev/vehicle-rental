package ru.cheranev.rental.domain;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Отчет Среднее время проката
 *
 * @author Cheranev N.
 * created on 25.05.2019.
 */
@Entity
@Data
@Table(name = "AVG_TIME_VIEW")
@Immutable
public class ReportAverageTime {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    ReportAverageTimeId id;

    private String rpName;
    private String vtName;
    private String vmName;
    private Integer avgTime;
}
