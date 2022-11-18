package org.aquam.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserModel {

    String username;
    String email;
    String instagram;
    LocalDateTime registrationDate;
}
