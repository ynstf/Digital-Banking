package ma.enset.ebanking.dto;

public class SavingAccountDTO extends BankAccountDTO {
    private double interestRate;

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
}
