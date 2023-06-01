package org.aquam.service;

import org.aquam.model.SupportLetter;
import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;
import org.aquam.model.dto.SupportLetterDto;

import java.util.List;

public interface AdminService {

    List<SupportLetterModel> getLetters();
    SupportLetter findById(Long id);
    Boolean answerLetter(Long id, SupportLetterDto supportLetterDto);
    List<UserModel> getUsers();
    Boolean changeLockUser(Long userId);
}
