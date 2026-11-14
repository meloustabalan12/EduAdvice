package com.eduadvice.backend.service;

import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.entity.*;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    private Parcours parcours1;
    private Semester semester1;
    private TeachingUnit teachingUnit1;
    private Subject subject1, subject2;

    @BeforeEach
    void setUp() {
        // Parcours
        parcours1 = new Parcours(
                "EI2D",
                "Master 2",
                "Master Informatique",
                "Informatique",
                "Université Sorbonne Paris Nord",
                "Exploration Informatique des Données et Décisionnel."
        );

        // Semester
        semester1 = new Semester(3, parcours1);

        // TeachingUnit
        teachingUnit1 = new TeachingUnit(
                semester1,
                "Majeure I : Apprentissage Artificiel et Science des Données",
                TeachingUnitCategory.MAJOR_1,
                new BigDecimal("12")
        );

        // Subjects
        subject1 = new Subject(
                teachingUnit1,
                "Apprentissage statistique",
                "Étude des méthodes statistiques permettant aux systèmes informatiques d'apprendre à partir de données.",
                "Apprentissage supervisé, régression, classification, estimation, généralisation et évaluation des modèles.",
                new BigDecimal("3")
        );

        subject2 = new Subject(
                teachingUnit1,
                "Deep Learning",
                "Étude des réseaux de neurones profonds utilisés pour apprendre des représentations complexes à partir de données.",
                "Réseaux de neurones, rétropropagation, fonctions d'activation, optimisation, réseaux convolutifs et architectures profondes.",
                new BigDecimal("3")
        );

        semester1.setParcours(parcours1);
        teachingUnit1.setSemester(semester1);
        teachingUnit1.setSubjects(List.of(subject1, subject2));
        subject1.setTeachingUnit(teachingUnit1);
        subject2.setTeachingUnit(teachingUnit1);
    }

    @Test
    void getAllSubjects_shouldReturnAllSubjects() {
        when(subjectRepository.findAll()).thenReturn(List.of(subject1, subject2));

        List<SubjectResponseDTO> result = subjectService.getAllSubjects();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Apprentissage statistique");
        assertThat(result.get(0).description()).isEqualTo(subject1.getDescription());
        assertThat(result.get(0).content()).isEqualTo(subject1.getContent());
        assertThat(result.get(0).ects()).isEqualByComparingTo(new BigDecimal("3"));
        assertThat(result.get(1).name()).isEqualTo("Deep Learning");

        verify(subjectRepository).findAll();
    }

    @Test
    void getAllSubjects_shouldReturnEmptyList_whenNoSubjectsExist() {
        when(subjectRepository.findAll()).thenReturn(List.of());

        List<SubjectResponseDTO> result = subjectService.getAllSubjects();

        assertThat(result).isEmpty();

        verify(subjectRepository).findAll();
    }

    @Test
    void getSubjectById_shouldReturnSubject_whenIdExists() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));

        SubjectResponseDTO result = subjectService.getSubjectById(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Apprentissage statistique");
        assertThat(result.description()).isEqualTo(subject1.getDescription());
        assertThat(result.teachingUnitId()).isEqualTo(teachingUnit1.getId());
        assertThat(result.ects()).isEqualByComparingTo(new BigDecimal("3"));

        verify(subjectRepository).findById(1L);
    }

    @Test
    void getSubjectById_shouldThrowException_whenSubjectDoesNotExist() {
        when(subjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subjectService.getSubjectById(99L)).isInstanceOf(RessourceNotFoundException.class);

        verify(subjectRepository).findById(99L);
    }

    @Test
    void getTeachingUnit_shouldReturnTeachingUnit_whenSubjectExists() {
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));

        TeachingUnitResponseDTO result = subjectService.getTeachingUnit(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(teachingUnit1.getId());
        assertThat(result.semesterId()).isEqualTo(semester1.getId());
        assertThat(result.name()).isEqualTo(teachingUnit1.getName());
        assertThat(result.category()).isEqualTo(TeachingUnitCategory.MAJOR_1);
        assertThat(result.ects()).isEqualByComparingTo(new BigDecimal("12"));

        verify(subjectRepository).findById(1L);
    }

    @Test
    void getTeachingUnit_shouldThrowException_whenSubjectDoesNotExist() {
        when(subjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subjectService.getTeachingUnit(99L)).isInstanceOf(RessourceNotFoundException.class);

        verify(subjectRepository).findById(99L);
    }
}