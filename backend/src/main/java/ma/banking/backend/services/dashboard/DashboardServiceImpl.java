package ma.banking.backend.services.dashboard;

import lombok.AllArgsConstructor;
import ma.banking.backend.dtos.AccountOperationDTO;
import ma.banking.backend.dtos.dashboard.DashboardSummaryDTO;
//import ma.banking.backend.enums.OperationType;
import ma.banking.backend.dtos.dashboard.TopCustomerDTO;
import ma.banking.backend.entities.BankAccount;
import ma.banking.backend.mappers.BankAccountMapperImpl;
import ma.banking.backend.repositories.AccountOperationRepository;
import ma.banking.backend.repositories.BankAccountRepository;
import ma.banking.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final CustomerRepository customerRepo;
    private final BankAccountRepository accountRepo;
    private final AccountOperationRepository opRepo;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public List<AccountOperationDTO> getRecentOperations() {
        return accountOperationRepository.findTop10ByOrderByOperationDateDesc()
                .stream()
                .map(dtoMapper::fromAccountOperation)
                .collect(Collectors.toList());
    }

    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        long customerCount     = customerRepo.count();
        long totalAccountCount = accountRepo.count();

        Map<String, Long> accountCountByType = accountRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        acc -> acc.getClass().getSimpleName(),
                        Collectors.counting()
                ));

        Map<String, Double> totalBalanceByType = accountRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        acc -> acc.getClass().getSimpleName(),
                        Collectors.summingDouble(a -> a.getBalance())
                ));

        Map<String, Long> operationCountByType = opRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        op -> op.getType().name(),
                        Collectors.counting()
                ));

        // ← NEW: group accounts created per month
        Map<String, Long> accountsCreatedByMonth = accountRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        acc -> new SimpleDateFormat("yyyy-MM").format(acc.getCreatedAt()),
                        TreeMap::new,                    // keep chronological order
                        Collectors.counting()
                ));

        // ← NEW: operations by day of week
        Map<String, Long> raw = opRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        op -> new SimpleDateFormat("EEEE", Locale.ENGLISH).format(op.getOperationDate()),
                        Collectors.counting()
                ));

        // Ensure order Monday → Sunday
        Map<String, Long> opsByDayOfWeek = new LinkedHashMap<>();
        for (DayOfWeek dow : DayOfWeek.values()) {
            String name = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            opsByDayOfWeek.put(name, raw.getOrDefault(name, 0L));
        }


        // ← NEW: Top 5 customers by sum of their account balances
        List<TopCustomerDTO> topCustomers = customerRepo.findAll().stream()
                .map(cust -> {
                    double sum = cust.getBankAccounts().stream()
                            .mapToDouble(BankAccount::getBalance)
                            .sum();
                    return new TopCustomerDTO(cust.getName(), sum);
                })
                .sorted((a, b) -> Double.compare(b.getTotalBalance(), a.getTotalBalance()))
                .limit(5)
                .collect(Collectors.toList());

        // ← NEW: average balance per type
        Map<String, Double> avgBalanceByType = accountRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        acc -> acc.getClass().getSimpleName(),
                        Collectors.averagingDouble(a -> a.getBalance())
                ));


        return new DashboardSummaryDTO(
                customerCount,
                totalAccountCount,
                accountCountByType,
                totalBalanceByType,
                operationCountByType,
                accountsCreatedByMonth,
                opsByDayOfWeek,
                topCustomers,
                avgBalanceByType,
                getRecentOperations()
        );
    }

}