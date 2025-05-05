package ma.enset.ebanking.dto;

import java.util.Date;
import java.util.List;

public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private String type;
    private Long customerId;
    private double interestRate;
    private double overdraft;
    private List<AccountOperationDTO> operations;

    // Getters and setters...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public double getOverdraft() { return overdraft; }
    public void setOverdraft(double overdraft) { this.overdraft = overdraft; }
    public List<AccountOperationDTO> getOperations() { return operations; }
    public void setOperations(List<AccountOperationDTO> operations) { this.operations = operations; }
}
