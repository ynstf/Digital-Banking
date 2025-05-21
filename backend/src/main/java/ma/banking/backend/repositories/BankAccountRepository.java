package ma.banking.backend.repositories;

import ma.banking.backend.entities.BankAccount;
import ma.banking.backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    Collection<Object> findByCustomerId(Long customerId);
    List<BankAccount> findByCustomer_Id(Long customerId);  // Note the underscore in `customer_Id`

}
