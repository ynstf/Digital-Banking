package ma.enset.ebanking.mappers;

import ma.enset.ebanking.dto.CustomerDTO;
import ma.enset.ebanking.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);
}
