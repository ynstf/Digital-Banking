package ma.banking.backend.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.banking.backend.dtos.accountsOps.AccountOperationDTO;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long customerCount;
    private long totalAccountCount;
    private Map<String, Long> accountCountByType;
    private Map<String, Double> totalBalanceByType;
    private Map<String, Long> operationCountByType;
    private Map<String, Long> accountsCreatedByMonth;
    private Map<String, Long> opsByDayOfWeek;
    private List<TopCustomerDTO> topCustomers;
    private Map<String, Double> avgBalanceByType;

    private List<AccountOperationDTO> recentOperations;
}