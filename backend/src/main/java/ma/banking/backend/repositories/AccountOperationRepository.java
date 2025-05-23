package ma.banking.backend.repositories;

import ma.banking.backend.entities.AccountOperation;
import ma.banking.backend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  List<AccountOperation> findByBankAccountId(String accountId);
  Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
  List<AccountOperation> findTop10ByOrderByOperationDateDesc();
}
