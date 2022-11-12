package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossedRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // private int rowNumber;
    @OneToOne
    private LiveRow liveRow;
    @ManyToMany(mappedBy = "crossedRows")
    private List<AppUser> appUserList;
}
