package org.aquam.model.admin;

import lombok.Data;

@Data
public class SupportLetterModel {

    private Long id;
    private String email;
    private String title;
    private String body;

    public SupportLetterModel(Long id, String email, String title, String body) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.body = body;
    }

    public SupportLetterModel(String email, String title, String body) {
        this.email = email;
        this.title = title;
        this.body = body;
    }
}
