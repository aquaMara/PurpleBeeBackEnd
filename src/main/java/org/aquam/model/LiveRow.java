package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNumber;
    private String schema;
    private Boolean isTitleRow;
    private Boolean isInfoRow;
    @ManyToOne
    private Pattern pattern;

    @Override
    public String toString() {
        return "LiveRow{" +
                "id=" + id +
                ", rowNumber=" + rowNumber +
                ", schema='" + schema + '\'' +
                ", isTitleRow=" + isTitleRow +
                ", isInfoRow=" + isInfoRow +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveRow liveRow = (LiveRow) o;
        return rowNumber == liveRow.rowNumber && Objects.equals(schema, liveRow.schema) && Objects.equals(isTitleRow, liveRow.isTitleRow) && Objects.equals(isInfoRow, liveRow.isInfoRow) && Objects.equals(pattern, liveRow.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, schema, isTitleRow, isInfoRow, pattern);
    }
}
