package org.aquam.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private Double payment;
    private String username;
    private Long patternId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return Objects.equals(payment, that.payment) && Objects.equals(username, that.username) && Objects.equals(patternId, that.patternId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment, username, patternId);
    }
}
