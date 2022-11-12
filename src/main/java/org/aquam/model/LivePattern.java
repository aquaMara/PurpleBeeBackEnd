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
public class LivePattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNumber;
    private String rowDescription;
    private Boolean isTitleRow;

    @OneToOne(mappedBy = "livePattern")
    private Pattern pattern;
}
