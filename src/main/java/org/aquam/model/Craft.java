package org.aquam.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Craft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "craft")
    Set<Pattern> patterns;

    @Override
    public String toString() {
        return "Craft{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Craft craft = (Craft) o;
        return Objects.equals(id, craft.id) && Objects.equals(name, craft.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
