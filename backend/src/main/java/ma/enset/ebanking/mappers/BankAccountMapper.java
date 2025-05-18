package ma.enset.ebanking.mappers;

import ma.enset.ebanking.dto.BankAccountDTO;
import ma.enset.ebanking.entities.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountOperationMapper.class)
public interface BankAccountMapper {
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(target = "type", expression = "java(bankAccount.getClass().getSimpleName())")
    @Mapping(source = "accountOperations", target = "operations")
    BankAccountDTO fromBankAccount(BankAccount bankAccount);
}
