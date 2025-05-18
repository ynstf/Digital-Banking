package ma.banking.backend.repositories;

import ma.banking.backend.entities.BankAccount;
import ma.banking.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
