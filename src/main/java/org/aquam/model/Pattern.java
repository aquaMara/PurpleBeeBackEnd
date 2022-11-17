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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String littleDescription;
    @ManyToOne
    private Craft craft;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Language language;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    private Double price;
    private Double avgRate;
    private String imagePath;
    private String pdfPath;
    private String abbreviations;
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private AppUser creator;
    @OneToMany(mappedBy = "pattern")
    private List<LiveRow> liveRows;
    @OneToMany(mappedBy = "pattern")
    private List<Rate> rates;
    @OneToMany(mappedBy = "pattern")
    private List<Comment> comments;
    @OneToMany(mappedBy = "pattern")
    private List<Payment> payments;

    public Pattern(String name, String littleDescription,
                   DifficultyLevel difficultyLevel, Double price) {
        this.name = name;
        this.littleDescription = littleDescription;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.avgRate = 0.0;
        this.imagePath = "";
        this.pdfPath = "";
        this.abbreviations = "";
        this.liveRows = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", littleDescription='" + littleDescription + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", avgRate=" + avgRate +
                ", imagePath='" + imagePath + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", abbreviations='" + abbreviations + '\'' +
                '}';
    }
}
