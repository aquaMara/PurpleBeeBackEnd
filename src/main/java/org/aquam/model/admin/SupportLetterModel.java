package org.aquam.model.admin;

import lombok.Data;

@Data
public class SupportLetterModel {

    private String email;
    private String title;
    private String body;

    public SupportLetterModel() {
    }

    public SupportLetterModel(String email, String title, String body) {
        this.email = email;
        this.title = title;
        this.body = body;
    }
}
