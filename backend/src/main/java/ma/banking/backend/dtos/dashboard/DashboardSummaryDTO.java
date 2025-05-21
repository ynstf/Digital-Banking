package ma.banking.backend.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.banking.backend.dtos.AccountOperationDTO;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long customerCount;
    private long totalAccountCount;
    private Map<String, Long> accountCountByType;       // ← new
    private Map<String, Double> totalBalanceByType;     // e.g. {"CURRENT": 12345.0, "SAVING": 6789.0}
    private Map<String, Long> operationCountByType;     // e.g. {"DEBIT": 42, "CREDIT": 37}
    private Map<String, Long> accountsCreatedByMonth;
    private Map<String, Long> opsByDayOfWeek;
    private List<TopCustomerDTO> topCustomers;
    private Map<String, Double> avgBalanceByType;

    private List<AccountOperationDTO> recentOperations;
}