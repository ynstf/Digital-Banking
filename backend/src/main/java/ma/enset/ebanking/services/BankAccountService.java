package ma.enset.ebanking.services;

import ma.enset.ebanking.dto.AccountOperationDTO;
import ma.enset.ebanking.dto.BankAccountDTO;
import ma.enset.ebanking.dto.CurrentAccountDTO;
import ma.enset.ebanking.dto.SavingAccountDTO;
import ma.enset.ebanking.exceptions.BankAccountNotFoundException;
import ma.enset.ebanking.exceptions.BalanceNotSufficientException;
import java.util.List;

public interface BankAccountService {
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overdraft, Long customerId)
            throws BankAccountNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId)
            throws BankAccountNotFoundException;
    List<BankAccountDTO> listBankAccounts();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description)
            throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException;
}
