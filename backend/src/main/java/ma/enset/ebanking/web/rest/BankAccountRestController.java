package ma.enset.ebanking.web.rest;

import ma.enset.ebanking.dto.AccountOperationDTO;
import ma.enset.ebanking.dto.BankAccountDTO;
import ma.enset.ebanking.dto.CurrentAccountDTO;
import ma.enset.ebanking.dto.SavingAccountDTO;
import ma.enset.ebanking.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankAccounts")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public List<BankAccountDTO> bankAccounts() {
        return bankAccountService.listBankAccounts();
    }

    @GetMapping("/{id}")
    public BankAccountDTO getAccount(@PathVariable String id) throws Exception {
        return bankAccountService.getBankAccount(id);
    }

    @PostMapping("/current")
    public CurrentAccountDTO saveCurrentAccount(@RequestBody CreateCurrentAccountRequest request)
            throws Exception {
        return bankAccountService.saveCurrentAccount(
                request.getInitialBalance(), request.getOverdraft(), request.getCustomerId());
    }

    @PostMapping("/saving")
    public SavingAccountDTO saveSavingAccount(@RequestBody CreateSavingAccountRequest request)
            throws Exception {
        return bankAccountService.saveSavingAccount(
                request.getInitialBalance(), request.getInterestRate(), request.getCustomerId());
    }

    @PutMapping("/{id}/debit")
    public void debit(@PathVariable String id, @RequestBody AccountOperationRequest request) throws Exception {
        bankAccountService.debit(id, request.getAmount(), request.getDescription());
    }

    @PutMapping("/{id}/credit")
    public void credit(@PathVariable String id, @RequestBody AccountOperationRequest request) throws Exception {
        bankAccountService.credit(id, request.getAmount(), request.getDescription());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest request) throws Exception {
        bankAccountService.transfer(
                request.getAccountSource(), request.getAccountDestination(), request.getAmount());
    }

    @GetMapping("/{id}/operations")
    public List<AccountOperationDTO> getAccountHistory(@PathVariable String id) throws Exception {
        return bankAccountService.accountHistory(id);
    }

    // DTO classes for request bodies
    static class CreateCurrentAccountRequest {
        private double initialBalance;
        private double overdraft;
        private Long customerId;
        // Getters and setters...
        public double getInitialBalance() { return initialBalance; }
        public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
        public double getOverdraft() { return overdraft; }
        public void setOverdraft(double overdraft) { this.overdraft = overdraft; }
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
    }
    static class CreateSavingAccountRequest {
        private double initialBalance;
        private double interestRate;
        private Long customerId;
        // Getters and setters...
        public double getInitialBalance() { return initialBalance; }
        public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
        public double getInterestRate() { return interestRate; }
        public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
    }
    static class AccountOperationRequest {
        private double amount;
        private String description;
        // Getters and setters...
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    static class TransferRequest {
        private String accountSource;
        private String accountDestination;
        private double amount;
        // Getters and setters...
        public String getAccountSource() { return accountSource; }
        public void setAccountSource(String accountSource) { this.accountSource = accountSource; }
        public String getAccountDestination() { return accountDestination; }
        public void setAccountDestination(String accountDestination) { this.accountDestination = accountDestination; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }
}
