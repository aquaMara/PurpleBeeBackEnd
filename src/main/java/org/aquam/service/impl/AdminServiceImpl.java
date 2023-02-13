package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.AppUser;
import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;
import org.aquam.repository.AppUserRepository;
import org.aquam.repository.SupportLetterRepository;
import org.aquam.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SupportLetterRepository supportLetterRepository;
    private final AppUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SupportLetterModel> getLetters() {
        List<SupportLetterModel> letters = supportLetterRepository.findAll().stream()
                .map(e -> new SupportLetterModel(e.getAppUser().getEmail(), e.getTitle(), e.getBody()))
                .collect(Collectors.toList());
        return letters;
    }

    @Override
    public List<UserModel> getUsers() {
        List<UserModel> users = userRepository.findAll().stream()
                .map(e -> new UserModel(
                        e.getId(),
                        e.getUsername(),
                        e.getEmail(),
                        e.getLocked(),
                        e.getEnabled()
                ))
                .collect(Collectors.toList());
        return users;
    }

    @Override
    public Boolean changeLockUser(Long userId) {
        AppUser user = userRepository.findById(userId).get();
        user.setLocked(!user.getLocked());
        userRepository.save(user);
        return true;
    }
}
