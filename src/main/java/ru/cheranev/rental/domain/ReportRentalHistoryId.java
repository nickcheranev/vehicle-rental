package ru.cheranev.rental.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Cheranev N.
 * created on 26.05.2019.
 */
@Embeddable
public class ReportRentalHistoryId implements Serializable {
    private Long rentalId;
    private Long vehicleTypeId;
    private Long vehicleModelId;
    private Long customerId;
}
