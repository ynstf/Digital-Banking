package ma.banking.backend.web;

import ma.banking.backend.dtos.accountsOps.*;
import ma.banking.backend.dtos.opertaions.CreditDTO;
import ma.banking.backend.dtos.opertaions.DebitDTO;
import ma.banking.backend.dtos.opertaions.TransferRequestDTO;
import ma.banking.backend.exceptions.BalanceNotSufficientException;
import ma.banking.backend.exceptions.BankAccountNotFoundException;
import ma.banking.backend.exceptions.CustomerNotFoundException;
import ma.banking.backend.services.bankOps.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<BankAccountDTO> listAccounts(){
        System.out.println("newnewnewnewnew*********************************");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }


    @PostMapping("/accounts/debit")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }


    @PostMapping("/accounts/credit")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }


    @PostMapping("/accounts/transfer")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }


    @PostMapping("/accounts/current")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public CurrentBankAccountDTO createCurrentAccount(
            @RequestBody CreateCurrentAccountRequest request
    ) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(
                request.getInitialBalance(),
                request.getOverDraft(),
                request.getCustomerId()
        );
    }


    @PostMapping("/accounts/saving")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public SavingBankAccountDTO createSavingAccount(
            @RequestBody CreateSavingAccountRequest request
    ) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(
                request.getInitialBalance(),
                request.getInterestRate(),
                request.getCustomerId()
        );
    }

    @DeleteMapping("/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteAccount(@PathVariable String id) throws BankAccountNotFoundException {
        bankAccountService.deleteBankAccount(id);
    }

}
