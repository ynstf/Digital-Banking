package ma.banking.backend.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BankAccountDTO {
    private String type;
    private String createdBy;
}
