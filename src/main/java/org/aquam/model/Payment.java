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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment1 = (Payment) o;
        return Objects.equals(payment, payment1.payment) && Objects.equals(appUser, payment1.appUser) && Objects.equals(pattern, payment1.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment, appUser, pattern);
    }
}
