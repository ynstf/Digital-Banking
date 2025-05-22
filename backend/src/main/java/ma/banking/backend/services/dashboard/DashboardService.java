package ma.banking.backend.services.dashboard;


import ma.banking.backend.dtos.accountsOps.AccountOperationDTO;
import ma.banking.backend.dtos.dashboard.DashboardSummaryDTO;

import java.util.List;

public interface DashboardService {
    DashboardSummaryDTO getDashboardSummary();
    List<AccountOperationDTO> getRecentOperations();

}