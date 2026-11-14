
package com.eduadvice.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teaching_units")
public class TeachingUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TeachingUnitCategory category;

    @Column(precision = 4, scale = 1)
    private BigDecimal ects;

    @OneToMany(mappedBy = "teachingUnit")
    private List<Subject> subjects = new ArrayList<>();

    public TeachingUnit() {
    }

    public TeachingUnit(
            Semester semester,
            String name,
            TeachingUnitCategory category,
            BigDecimal ects
    ) {
        this.semester = semester;
        this.name = name;
        this.category = category;
        this.ects = ects;
    }

    public Long getId() {
        return id;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getName() {
        return name;
    }

    public TeachingUnitCategory getCategory() {
        return category;
    }

    public BigDecimal getEcts() {
        return ects;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
