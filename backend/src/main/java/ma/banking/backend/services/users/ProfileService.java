package ma.banking.backend.services.users;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {
    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(InMemoryUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }


    public void updatePassword(String username, String oldPassword, String newPassword) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        // Validate old password
        if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Update to new password
        UserDetails updatedUser = User.withUserDetails(userDetails)
                .password(passwordEncoder.encode(newPassword))
                .build();
        userDetailsManager.updateUser(updatedUser);
    }


    public void updateUsername(String oldUsername, String newUsername) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(oldUsername);
        UserDetails newUserDetails = User.withUserDetails(userDetails)
                .username(newUsername)
                .build();
        userDetailsManager.deleteUser(oldUsername);
        userDetailsManager.createUser(newUserDetails);
    }
}
