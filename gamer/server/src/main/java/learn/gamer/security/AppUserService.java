package learn.gamer.security;

import learn.gamer.data.AppUserJdbcTemplateRepository;
import learn.gamer.domain.Result;
import learn.gamer.domain.ResultType;
import learn.gamer.models.AppUser;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserJdbcTemplateRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserJdbcTemplateRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);
        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return appUser;
    }

    public Result<AppUser> create(String username, String password) {
        Result<AppUser> result = validate(username, password);
        if(!result.isSuccess()) {
            return result;
        }
        String hashedPassword = encoder.encode(password);
        AppUser appUser = new AppUser(0, username, hashedPassword, true, List.of("USER"));

        try {
            appUser = repository.create(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException ex) {
            result.addMessage("The provided username already exists.", ResultType.INVALID);
        }
        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage("Username is required.", ResultType.INVALID);
            return result;
        }
        if (password == null || password.isBlank()) {
            result.addMessage("Password is required.", ResultType.INVALID);
            return result;
        }
        if (username.length() > 255) {
            result.addMessage("Username must be less than 255 characters.", ResultType.INVALID);
        }

        if (repository.findByUsername(username) != null) {
            result.addMessage("The provided username already exists.", ResultType.DUPLICATE);
        }

        if (!isValidPassword(password)) {
            result.addMessage("Password must be contain at least 8 characters, and at least one of a number, a letter, and a special character.", ResultType.INVALID);
        }
        return result;
    }

    private boolean isValidPassword(String password) {
        if(password.length() < 8) {
            return false;
        }
        int numbers = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                numbers++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }
        return (numbers > 0) && (letters > 0) && (others > 0);
    }
}
