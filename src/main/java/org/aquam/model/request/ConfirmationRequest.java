package org.aquam.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationRequest {

    private String username;
    private String email;
    private String confirmationLink;

    public ConfirmationRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
