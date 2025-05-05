package ma.enset.ebanking.dto;

import java.util.Date;

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String type;
    private String description;
    private String bankAccountId;

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getOperationDate() { return operationDate; }
    public void setOperationDate(Date operationDate) { this.operationDate = operationDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBankAccountId() { return bankAccountId; }
    public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }
}
