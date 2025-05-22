package ma.banking.backend.web;

import ma.banking.backend.dtos.users.NewUserRequest;
import ma.banking.backend.dtos.users.UserInfoDTO;
import ma.banking.backend.services.users.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public List<UserInfoDTO> listUsers() {
        return userManagementService.listUsers();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody NewUserRequest userDTO) {
        try {
            userManagementService.createUser(userDTO);
            return ResponseEntity.ok().body(Map.of("message", "User created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

}
