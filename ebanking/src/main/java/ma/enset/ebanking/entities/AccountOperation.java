package ma.enset.ebanking.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    public AccountOperation() { }

    public AccountOperation(Date operationDate, double amount, OperationType type, String description, BankAccount bankAccount) {
        this.operationDate = operationDate;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.bankAccount = bankAccount;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public Date getOperationDate() { return operationDate; }
    public void setOperationDate(Date operationDate) { this.operationDate = operationDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public OperationType getType() { return type; }
    public void setType(OperationType type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BankAccount getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
}
