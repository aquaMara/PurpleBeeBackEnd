package org.aquam.model.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class SupportLetterDto {

    private Long id;
    private String email;
    private String title;
    private String body;

    @Override
    public String toString() {
        return "SupportLetterDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupportLetterDto that = (SupportLetterDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(title, that.title) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, title, body);
    }
}
