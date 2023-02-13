package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String body;
    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private Pattern pattern;

    public Comment(String body, Pattern pattern) {
        this.body = body;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(body, comment.body) && Objects.equals(appUser, comment.appUser) && Objects.equals(pattern, comment.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, appUser, pattern);
    }
}
