package ma.enset.ebanking;

import ma.enset.ebanking.dto.CustomerDTO;
import ma.enset.ebanking.dto.CurrentAccountDTO;
import ma.enset.ebanking.dto.SavingAccountDTO;
import ma.enset.ebanking.services.BankAccountService;
import ma.enset.ebanking.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private CustomerService customerService;
    private BankAccountService bankAccountService;

    public DataInitializer(CustomerService customerService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create some customers
        CustomerDTO c1 = new CustomerDTO();
        c1.setName("Alice");
        c1.setEmail("alice@example.com");
        CustomerDTO savedC1 = customerService.saveCustomer(c1);

        CustomerDTO c2 = new CustomerDTO();
        c2.setName("Bob");
        c2.setEmail("bob@example.com");
        CustomerDTO savedC2 = customerService.saveCustomer(c2);

        // Create accounts
        CurrentAccountDTO account1 = bankAccountService.saveCurrentAccount(1000, 200, savedC1.getId());
        SavingAccountDTO account2 = bankAccountService.saveSavingAccount(2000, 5.5, savedC2.getId());

        // Perform some operations
        bankAccountService.credit(account1.getId(), 500, "Initial deposit");
        bankAccountService.debit(account1.getId(), 100, "ATM withdrawal");
        bankAccountService.credit(account2.getId(), 200, "Initial deposit");
    }
}
