package ma.banking.backend.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long customerCount;
    private long totalAccountCount;
    private Map<String, Double> totalBalanceByType;     // e.g. {"CURRENT": 12345.0, "SAVING": 6789.0}
    private Map<String, Long> operationCountByType;     // e.g. {"DEBIT": 42, "CREDIT": 37}
}