
package com.eduadvice.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "subjects",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"teaching_unit_id", "name"}
        )
    }
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teaching_unit_id", nullable = false)
    private TeachingUnit teachingUnit;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(precision = 4, scale = 1)
    private BigDecimal ects;

    public Subject() {
    }

    public Subject(
            TeachingUnit teachingUnit,
            String name,
            String description,
            String content,
            BigDecimal ects
    ) {
        this.teachingUnit = teachingUnit;
        this.name = name;
        this.description = description;
        this.content = content;
        this.ects = ects;
    }

    public Long getId() {
        return id;
    }

    public TeachingUnit getTeachingUnit() {
        return teachingUnit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public BigDecimal getEcts() {
        return ects;
    }

    public void setTeachingUnit(TeachingUnit teachingUnit) {
        this.teachingUnit = teachingUnit;
    }

    public void setName(String name) {
        this.name = name;
    }
}
