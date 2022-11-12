package org.aquam.model.request;

import lombok.Value;

@Value
public class LoginRequest {

    String username;
    String password;
}
