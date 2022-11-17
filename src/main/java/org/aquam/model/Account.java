package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double sumTotal;
    private Double sumAvailable;
    @OneToOne(mappedBy = "account")
    private AppUser user;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", sumTotal=" + sumTotal +
                ", sumAvailable=" + sumAvailable +
                '}';
    }
}
