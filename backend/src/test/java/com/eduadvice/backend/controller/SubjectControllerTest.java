package com.eduadvice.backend.controller;

import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.entity.TeachingUnitCategory;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubjectService subjectService;

    private SubjectResponseDTO subject;
    private TeachingUnitResponseDTO teachingUnit;

    @BeforeEach
    void setUp() {
        subject = new SubjectResponseDTO(
                1L,
                10L,
                "Deep Learning",
                "Introduction au Deep Learning",
                "Neural Networks, CNN, RNN",
                new BigDecimal("3")
        );

        teachingUnit = new TeachingUnitResponseDTO(
                10L,
                3L,
                "Apprentissage Artificiel",
                TeachingUnitCategory.MAJOR_1,
                new BigDecimal("12")
        );
    }

    @Test
    void getAllSubjects_shouldReturnSubjects() throws Exception {
        when(subjectService.getAllSubjects()).thenReturn(List.of(subject));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Deep Learning"))
                .andExpect(jsonPath("$[0].ects").value(3))
                .andDo(print());

        verify(subjectService).getAllSubjects();
    }

    @Test
    void getAllSubjects_shouldReturnEmptyList_whenNoSubjectsExist() throws Exception {
        when(subjectService.getAllSubjects()).thenReturn(List.of());

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());

        verify(subjectService).getAllSubjects();
    }

    @Test
    void ShouldReturn404_whenPathDoesNotExist() throws Exception {

        mockMvc.perform(get("/api/subj"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getSubjectById_shouldReturnSubject() throws Exception {
        when(subjectService.getSubjectById(1L)).thenReturn(subject);

        mockMvc.perform(get("/api/subjects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.teachingUnitId").value(10))
                .andExpect(jsonPath("$.name").value("Deep Learning"))
                .andExpect(jsonPath("$.ects").value(3))
                .andDo(print());

        verify(subjectService).getSubjectById(1L);
    }

    @Test
    void getSubjectById_shouldReturn404_whenNotExists() throws Exception {
        when(subjectService.getSubjectById(99L)).thenThrow(new RessourceNotFoundException("Subject not found"));

        mockMvc.perform(get("/api/subjects/99"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(subjectService).getSubjectById(99L);
    }


    @Test
    void getTeachingUnit_shouldReturnTeachingUnit() throws Exception {
        when(subjectService.getTeachingUnit(1L)).thenReturn(teachingUnit);

        mockMvc.perform(get("/api/subjects/1/teaching-unit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.semesterId").value(3))
                .andExpect(jsonPath("$.name").value("Apprentissage Artificiel"))
                .andExpect(jsonPath("$.ects").value(12))
                .andDo(print());

        verify(subjectService).getTeachingUnit(1L);
    }
}