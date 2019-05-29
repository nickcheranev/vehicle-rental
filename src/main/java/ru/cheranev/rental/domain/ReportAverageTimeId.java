package ru.cheranev.rental.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Cheranev N.
 * created on 25.05.2019.
 */
@Embeddable
public class ReportAverageTimeId implements Serializable{
    private Long rpId;
    private Long vtId;
    private Long vmId;
}
