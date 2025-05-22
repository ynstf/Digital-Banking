package ma.banking.backend.dtos.accountsOps;

import lombok.Data;

@Data
public class CreateSavingAccountRequest {
    private double initialBalance;
    private double interestRate;
    private Long customerId;
}