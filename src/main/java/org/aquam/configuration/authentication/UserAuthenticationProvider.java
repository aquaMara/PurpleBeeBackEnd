package org.aquam.configuration.authentication;

import lombok.RequiredArgsConstructor;
import org.aquam.service.impl.AppUserServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AppUserServiceImpl appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = appUserService.loadUserByUsername(username);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Wrong username or password");

        return new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        // return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
