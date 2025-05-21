package ma.banking.backend.web;


import ma.banking.backend.dtos.profileUpdate.PasswordChangeRequest;
import ma.banking.backend.dtos.profileUpdate.UsernameChangeRequest;
import ma.banking.backend.services.ProfileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/password")
    public String changePassword(@RequestBody PasswordChangeRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        profileService.updatePassword(username, request.getOldPassword(), request.getNewPassword());
        return "Password updated";
    }

    @PutMapping("/username")
    public String changeUsername(@RequestBody UsernameChangeRequest request) {
        String oldUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        profileService.updateUsername(oldUsername, request.getNewUsername());
        return "Username updated";
    }
}
