package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int value;
    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private Pattern pattern;

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
