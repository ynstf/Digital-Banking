package ma.enset.ebanking.dto;

public class CurrentAccountDTO extends BankAccountDTO {
    private double overdraft;

    public double getOverdraft() { return overdraft; }
    public void setOverdraft(double overdraft) { this.overdraft = overdraft; }
}
