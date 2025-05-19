package ma.banking.backend.dtos;

import lombok.Data;

@Data
public class CreateCurrentAccountRequest {
    private Long customerId;
    private double initialBalance;
    private double overDraft;
}
