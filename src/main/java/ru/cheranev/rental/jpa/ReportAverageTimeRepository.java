package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.cheranev.rental.domain.ReportAverageTime;
import ru.cheranev.rental.domain.ReportAverageTimeId;

/**
 * Репозиторий отчета Среднее время проката
 *
 * @author Cheranev N.
 * created on 25.05.2019.
 */
public interface ReportAverageTimeRepository extends JpaRepository<ReportAverageTime, ReportAverageTimeId> {
}
