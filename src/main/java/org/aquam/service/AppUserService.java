package org.aquam.service;

import org.aquam.model.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findUserByUsername(String username);
    AppUser findByUsername(String username);
    AppUser findById(Long id);
}
