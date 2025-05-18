package ma.enset.ebanking.services;

import ma.enset.ebanking.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    CustomerDTO getCustomer(Long customerId);
    List<CustomerDTO> listCustomers();
}
