package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveRowState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long liveRowId;
    private Long appUserId;

    public LiveRowState(Long liveRowId, Long appUserId) {
        this.liveRowId = liveRowId;
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveRowState that = (LiveRowState) o;
        return Objects.equals(liveRowId, that.liveRowId) && Objects.equals(appUserId, that.appUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(liveRowId, appUserId);
    }
}
