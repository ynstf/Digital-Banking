package ma.banking.backend.services.dashboard;

import lombok.AllArgsConstructor;
import ma.banking.backend.dtos.dashboard.DashboardSummaryDTO;
//import ma.banking.backend.enums.OperationType;
import ma.banking.backend.repositories.AccountOperationRepository;
import ma.banking.backend.repositories.BankAccountRepository;
import ma.banking.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final CustomerRepository customerRepo;
    private final BankAccountRepository accountRepo;
    private final AccountOperationRepository opRepo;

    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        long customerCount = customerRepo.count();
        long totalAccountCount = accountRepo.count();

        // sum balances grouped by discriminator TYPE column
        Map<String, Double> totalBalanceByType = accountRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        acc -> acc.getClass().getSimpleName(),     // "CurrentAccount" or "SavingAccount"
                        Collectors.summingDouble(a -> a.getBalance()))
                );

        // count operations by OperationType enum
        Map<String, Long> operationCountByType = opRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        op -> op.getType().name(),
                        Collectors.counting()
                ));

        return new DashboardSummaryDTO(
                customerCount,
                totalAccountCount,
                totalBalanceByType,
                operationCountByType
        );
    }
}