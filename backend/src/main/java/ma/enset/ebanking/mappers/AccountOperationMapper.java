package ma.enset.ebanking.mappers;

import ma.enset.ebanking.dto.AccountOperationDTO;
import ma.enset.ebanking.entities.AccountOperation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountOperationMapper {
    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    @Mapping(source = "type", target = "type")
    AccountOperationDTO fromAccountOperation(AccountOperation operation);
}
