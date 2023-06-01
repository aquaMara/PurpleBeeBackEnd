package org.aquam.configuration.authentication;

import lombok.RequiredArgsConstructor;
import org.aquam.exception.RegistrationNotApprovedException;
import org.aquam.model.AppUser;
import org.aquam.service.impl.AppUserServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AppUserServiceImpl appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //UserDetails user = appUserService.loadUserByUsername(username);
        Optional<AppUser> user = appUserService.findUserByUsername(username);
        if (user.isEmpty())
            throw new EntityNotFoundException("Username does not exist");
        else {
            AppUser appUser = user.get();
            if (!appUser.getEnabled())
                throw new RegistrationNotApprovedException("Registration not approved");
            if (appUser.getLocked())
                throw new BadCredentialsException("Your account is blocked, please contact support");
            if (!bCryptPasswordEncoder.matches(password, appUser.getPassword()))
                throw new BadCredentialsException("Wrong username or password");
            return new UsernamePasswordAuthenticationToken(
                    appUser, appUser.getPassword(), appUser.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        // return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
