package com.eduadvice.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "parcours_id", nullable = false)
    private Parcours parcours;

    @OneToMany(mappedBy = "semester")
    List<TeachingUnit> teachingUnits = new ArrayList<>();


    public Semester() {
    }

    public Semester(Integer number, Parcours parcours) {
        this.number = number;
        this.parcours = parcours;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public List<TeachingUnit> getTeachingUnits() {
        return teachingUnits;
    }

    public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
        this.teachingUnits = teachingUnits;
    }
}
