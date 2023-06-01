package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.AppUser;
import org.aquam.model.SupportLetter;
import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;
import org.aquam.model.dto.SupportLetterDto;
import org.aquam.repository.AppUserRepository;
import org.aquam.repository.SupportLetterRepository;
import org.aquam.service.AdminService;
import org.aquam.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    private final EmailService emailService;

    @Override
    public List<SupportLetterModel> getLetters() {
        return supportLetterRepository.findAll().stream()
                .map(e -> new SupportLetterModel(e.getId(), e.getEmail(), e.getTitle(), e.getBody()))
                .collect(Collectors.toList());
    }

    @Override
    public SupportLetter findById(Long id) {
        return supportLetterRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Support Letter with id: " + " not found")
        );
    }

    @Override
    public Boolean answerLetter(Long id, SupportLetterDto supportLetterDto) {
        SupportLetter supportLetter = findById(id);
        emailService.sendSupportLetter(supportLetter.getEmail(), supportLetterDto.getBody());
        supportLetterRepository.delete(supportLetter);
        return true;
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
