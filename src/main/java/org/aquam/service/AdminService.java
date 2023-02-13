package org.aquam.service;

import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;

import java.util.List;

public interface AdminService {

    List<SupportLetterModel> getLetters();
    List<UserModel> getUsers();
    Boolean changeLockUser(Long userId);
}
