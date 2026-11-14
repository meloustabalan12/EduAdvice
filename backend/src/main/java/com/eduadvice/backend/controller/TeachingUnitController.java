
package com.eduadvice.backend.controller;

import com.eduadvice.backend.dto.SemesterDTO;
import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.service.TeachingUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/teaching-units","/api/teaching-units/"})
public class TeachingUnitController {

    private final TeachingUnitService teachingUnitService;

    public TeachingUnitController(TeachingUnitService teachingUnitService) {
        this.teachingUnitService = teachingUnitService;
    }

    @GetMapping
    public List<TeachingUnitResponseDTO> getAllTeachingUnits() {
        return teachingUnitService.getAllTeachingUnits();
    }

    @GetMapping({"/{id}","/{id}/"})
    public TeachingUnitResponseDTO getTeachingUnitById(@PathVariable Long id) {
        return teachingUnitService.getTeachingUnitById(id);
    }

    @GetMapping({"/{id}/semester","/{id}/semester/"})
    public ResponseEntity<SemesterDTO> getSemester(@PathVariable Long id){
        return ResponseEntity.ok(teachingUnitService.getSemester(id));
    }

    @GetMapping({"/{id}/subjects","/{id}/subjects/"})
    public List<SubjectResponseDTO> getSubjectsByTeachingUnit(@PathVariable Long id) {
        return teachingUnitService.getSubjects(id);
    }
}
