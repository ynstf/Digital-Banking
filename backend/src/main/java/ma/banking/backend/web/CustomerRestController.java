package ma.banking.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.banking.backend.dtos.BankAccountDTO;
import ma.banking.backend.dtos.CustomerDTO;
import ma.banking.backend.entities.Customer;
import ma.banking.backend.exceptions.BankAccountNotFoundException;
import ma.banking.backend.exceptions.CustomerNotFoundException;
import ma.banking.backend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }


    @GetMapping("/customers/search")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }


    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }


    @PostMapping("/customers")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }


    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> getAccountsByCustomer(@PathVariable Long customerId)
            throws BankAccountNotFoundException {
        return bankAccountService.getAccountsByCustomer(customerId);
    }


}
