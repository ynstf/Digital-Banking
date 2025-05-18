package ma.banking.backend.repositories;

import ma.banking.backend.entities.BankAccount;
import ma.banking.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    Collection<Object> findByCustomerId(Long customerId);
}
