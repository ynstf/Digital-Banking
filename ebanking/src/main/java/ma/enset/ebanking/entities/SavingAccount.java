package ma.enset.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("SAVING_ACCOUNT")
public class SavingAccount extends BankAccount {
    private double interestRate;

    public SavingAccount() {
        super();
    }

    public SavingAccount(String id, double balance, Date createdAt, Customer customer, double interestRate) {
        super(id, balance, createdAt, customer);
        this.interestRate = interestRate;
    }

    // Getter and Setter
    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
}
