package org.aquam.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
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
}
