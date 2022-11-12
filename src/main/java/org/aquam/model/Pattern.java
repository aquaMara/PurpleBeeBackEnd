package org.aquam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Craft craft;
    @ManyToOne
    private Category category;    // Accessories, Clothing, Home, Pet, Toys
    private String title;
    private String body;
    private String imageDescription; // size, materials from the photo
    private String language;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    private String price;
    // private String currency;

    // live pattern
    @OneToOne
    private LivePattern livePattern;

    // + pdf
    // from drive
    // + image
    private String imageDirectory;

}
