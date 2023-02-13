package org.aquam.model.admin;

import lombok.Data;

@Data
public class UserModel {

    private Long id;
    private String username;
    private String email;
    private Boolean isLocked;
    private Boolean isEnabled;

    public UserModel() {
    }

    public UserModel(Long id, String username, String email, Boolean isLocked, Boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
    }
}
