package ma.banking.backend.services.dashboard;


import ma.banking.backend.dtos.dashboard.DashboardSummaryDTO;

public interface DashboardService {
    DashboardSummaryDTO getDashboardSummary();
}