package ru.cheranev.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cheranev.rental.domain.Customer;

/**
 * Репозиторий Заказчики
 *
 * @author Cheranev N.
 * created on 18.05.2019.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
