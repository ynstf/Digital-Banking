package ma.banking.backend.services;

import ma.banking.backend.dtos.users.NewUserRequest;
import ma.banking.backend.dtos.users.UserInfoDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
public class UserManagementService {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

    public UserManagementService(InMemoryUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserInfoDTO> listUsers() {
        return users.values().stream()
                .map(user -> new UserInfoDTO(
                        user.getUsername(),
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public void createUser(NewUserRequest newUserRequest) {

        String username = newUserRequest.getUsername();
        String password = newUserRequest.getPassword();
        List<String> roles = newUserRequest.getRoles();

        if (userDetailsManager.userExists(username)) {
            throw new IllegalArgumentException("User already exists.");
        }
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(roles.toArray(new String[0]))
                .build();
        userDetailsManager.createUser(user);
        users.put(username, user);
    }
}
