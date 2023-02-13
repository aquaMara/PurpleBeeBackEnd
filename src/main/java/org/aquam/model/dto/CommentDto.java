package org.aquam.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long patternId;
    private String body;

    @Override
    public String toString() {
        return "CommentDto{" +
                "patternId=" + patternId +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(patternId, that.patternId) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patternId, body);
    }
}
