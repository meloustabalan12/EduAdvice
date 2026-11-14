package com.eduadvice.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Configuration class responsible for Hibernate database schema generation.
 * This class configures Hibernate to automatically create or update tables
 */
@Entity
public class Parcours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String level;

    @Basic
    @Column(nullable = false)
    private String diploma;

    private String mention;

    @Basic
    @Column(nullable = false)
    private String establishment;

    @Column(columnDefinition ="TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "parcours_careerOpportunities",
            joinColumns = @JoinColumn(name = "parcours_id"),
            inverseJoinColumns = @JoinColumn(name = "careerOpportunities_id")
    )
    private Set<CareerOpportunity> careerOpportunities = new HashSet<>();

    @OneToMany(mappedBy = "parcours")
    private List<Semester> semesters = new ArrayList<>();

    public Parcours() {
    }

    public Parcours(String name, String level, String diploma, String mention, String establishment, String description) {
        this.name = name;
        this.level = level;
        this.diploma = diploma;
        this.mention = mention;
        this.establishment = establishment;
        this.description = description;
    }

    // Helper methods for Career Opportunity
    public void addCareerOpportunity(CareerOpportunity opportunity){
        careerOpportunities.add(opportunity);
        opportunity.getParcours().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getDiploma() {
        return diploma;
    }

    public String getMention() {
        return mention;
    }

    public String getEstablishment() {
        return establishment;
    }

    public String getDescription() {
        return description;
    }

    public Set<CareerOpportunity> getCareerOpportunities() {
        return careerOpportunities;
    }

    // setter for the test
    public void setCareerOpportunities(Set<CareerOpportunity> careerOpportunities) {
        this.careerOpportunities = careerOpportunities;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }
}
