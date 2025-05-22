package ma.banking.backend.dtos.accountsOps;

import lombok.Data;

@Data
public class CreateCurrentAccountRequest {
    private Long customerId;
    private double initialBalance;
    private double overDraft;
}
