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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double payment;
    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private Pattern pattern;

    public Payment(Double payment, AppUser appUser, Pattern pattern) {
        this.payment = payment;
        this.appUser = appUser;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payment=" + payment +
                '}';
    }
}
