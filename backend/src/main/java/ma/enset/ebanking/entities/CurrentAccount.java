package ma.enset.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CURRENT_ACCOUNT")
public class CurrentAccount extends BankAccount {
    private double overdraft;

    public CurrentAccount() {
        super();
    }

    public CurrentAccount(String id, double balance, Date createdAt, Customer customer, double overdraft) {
        super(id, balance, createdAt, customer);
        this.overdraft = overdraft;
    }

    // Getter and Setter
    public double getOverdraft() { return overdraft; }
    public void setOverdraft(double overdraft) { this.overdraft = overdraft; }
}
