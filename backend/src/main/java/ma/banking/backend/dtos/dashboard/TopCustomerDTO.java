package ma.banking.backend.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCustomerDTO {
    private String name;
    private double totalBalance;
}
