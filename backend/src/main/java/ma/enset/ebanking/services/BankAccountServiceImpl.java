package ma.enset.ebanking.services;

import ma.enset.ebanking.dto.AccountOperationDTO;
import ma.enset.ebanking.dto.BankAccountDTO;
import ma.enset.ebanking.dto.CurrentAccountDTO;
import ma.enset.ebanking.dto.SavingAccountDTO;
import ma.enset.ebanking.entities.*;
import ma.enset.ebanking.exceptions.BankAccountNotFoundException;
import ma.enset.ebanking.exceptions.BalanceNotSufficientException;
import ma.enset.ebanking.mappers.BankAccountMapper;
import ma.enset.ebanking.mappers.AccountOperationMapper;
import ma.enset.ebanking.repositories.BankAccountRepository;
import ma.enset.ebanking.repositories.AccountOperationRepository;
import ma.enset.ebanking.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapper bankAccountMapper;
    private AccountOperationMapper accountOperationMapper;

    public BankAccountServiceImpl(CustomerRepository customerRepository,
                                  BankAccountRepository bankAccountRepository,
                                  AccountOperationRepository accountOperationRepository,
                                  BankAccountMapper bankAccountMapper,
                                  AccountOperationMapper accountOperationMapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.accountOperationMapper = accountOperationMapper;
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, double overdraft, Long customerId)
            throws BankAccountNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BankAccountNotFoundException("Customer not found"));
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setOverdraft(overdraft);
        bankAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        // Map to DTO
        BankAccountDTO bankDTO = bankAccountMapper.fromBankAccount(savedBankAccount);
        CurrentAccountDTO currentDTO = new CurrentAccountDTO();
        currentDTO.setId(bankDTO.getId());
        currentDTO.setBalance(bankDTO.getBalance());
        currentDTO.setCreatedAt(bankDTO.getCreatedAt());
        currentDTO.setType(bankDTO.getType());
        currentDTO.setCustomerId(bankDTO.getCustomerId());
        currentDTO.setOverdraft(bankDTO.getOverdraft());
        return currentDTO;
    }

    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId)
            throws BankAccountNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BankAccountNotFoundException("Customer not found"));
        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setInterestRate(interestRate);
        bankAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        // Map to DTO
        BankAccountDTO bankDTO = bankAccountMapper.fromBankAccount(savedBankAccount);
        SavingAccountDTO savingDTO = new SavingAccountDTO();
        savingDTO.setId(bankDTO.getId());
        savingDTO.setBalance(bankDTO.getBalance());
        savingDTO.setCreatedAt(bankDTO.getCreatedAt());
        savingDTO.setType(bankDTO.getType());
        savingDTO.setCustomerId(bankDTO.getCustomerId());
        savingDTO.setInterestRate(bankDTO.getInterestRate());
        return savingDTO;
    }

    @Override
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountRepository.findAll()
                .stream()
                .map(bankAccountMapper::fromBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        return bankAccountMapper.fromBankAccount(bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation operation = new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description)
            throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        AccountOperation operation = new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException {
        // Verify account exists
        bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        return accountOperationRepository.findByBankAccountId(accountId)
                .stream()
                .map(accountOperationMapper::fromAccountOperation)
                .collect(Collectors.toList());
    }
}
