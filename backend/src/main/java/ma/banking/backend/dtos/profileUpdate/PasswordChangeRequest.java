package ma.banking.backend.dtos.profileUpdate;


import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
