package ma.banking.backend.web;

import lombok.AllArgsConstructor;
import ma.banking.backend.dtos.dashboard.DashboardSummaryDTO;
import ma.banking.backend.services.dashboard.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public DashboardSummaryDTO getSummary() {
        return dashboardService.getDashboardSummary();
    }
}