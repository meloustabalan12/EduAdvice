
package com.eduadvice.backend.controller;

import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/subjects","/api/subjects/"})
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping({"/{id}","/{id}/"})
    public SubjectResponseDTO getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping({"/{id}/teaching-unit", "/{id}/teaching-unit/"})
    public ResponseEntity<TeachingUnitResponseDTO> getTeachingUnit(@PathVariable Long id){
        return ResponseEntity.ok(subjectService.getTeachingUnit(id));
    }
}
