package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.AppUser;
import org.aquam.model.dto.AppUserDto;
import org.aquam.model.request.RegistrationRequest;
import org.aquam.repository.AppUserRepository;
import org.aquam.service.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username: %d not found", username)));
    }

    @Override
    public Optional<AppUser> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    /*
    public AppUserDto save(AppUser user) {
        AppUser saved = userRepository.save(user);
        AppUserDto savedDto = modelMapper.map(saved, AppUserDto.class);
        return saved;
    }
     */

    /*
    public AppUser toUser(@Valid UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validationExceptions = validator.validate(userDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        return user;
    }
     */
}
