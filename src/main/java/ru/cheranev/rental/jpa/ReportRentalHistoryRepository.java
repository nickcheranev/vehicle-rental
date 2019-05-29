package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.cheranev.rental.domain.ReportRentalHistory;
import ru.cheranev.rental.domain.ReportRentalHistoryId;

/**
 * @author Cheranev N.
 * created on 25.05.2019.
 */
public interface ReportRentalHistoryRepository extends JpaRepository<ReportRentalHistory, ReportRentalHistoryId> {
}
