package ma.banking.backend.dtos.accountsOps;

import lombok.Data;
import ma.banking.backend.enums.OperationType;


import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    private String createdBy;

    private String accountId;
    private String customerName;
}

