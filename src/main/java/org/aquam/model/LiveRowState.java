package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
