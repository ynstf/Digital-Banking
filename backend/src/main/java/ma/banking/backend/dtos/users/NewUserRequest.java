package ma.banking.backend.dtos.users;

import lombok.Data;
import java.util.List;

@Data
public class NewUserRequest {
    private String username;
    private String password;
    private List<String> roles; // e.g. ["USER"], ["USER", "ADMIN"]
}