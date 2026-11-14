
package com.eduadvice.backend.service;

import com.eduadvice.backend.dto.ParcoursDTO;
import com.eduadvice.backend.dto.SemesterDTO;
import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.entity.Parcours;
import com.eduadvice.backend.entity.Semester;
import com.eduadvice.backend.entity.TeachingUnit;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.repository.TeachingUnitRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeachingUnitService {

    private final TeachingUnitRepository teachingUnitRepository;

    public TeachingUnitService(TeachingUnitRepository teachingUnitRepository) {
        this.teachingUnitRepository = teachingUnitRepository;
    }

    public List<TeachingUnitResponseDTO> getAllTeachingUnits() {
        return teachingUnitRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TeachingUnitResponseDTO getTeachingUnitById(Long id) {
        TeachingUnit teachingUnit = teachingUnitRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Teaching unit not found with id: " + id
                ));

        return convertToDTO(teachingUnit);
    }

    public SemesterDTO getSemester(Long teachingUnitId){
        TeachingUnit teachingUnit = teachingUnitRepository.findById(teachingUnitId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        "TeachingUnit not found : " + teachingUnitId
                ));

        Semester semester = teachingUnit.getSemester();

        // intialization for the parcours DTO
        Parcours parcours = semester.getParcours();

        return new SemesterDTO(
                semester.getId(),
                semester.getNumber(),
                new ParcoursDTO(
                        parcours.getId(),
                        parcours.getName(),
                        parcours.getLevel(),
                        parcours.getDiploma(),
                        parcours.getMention(),
                        parcours.getEstablishment(),
                        parcours.getDescription()
                        )
        );
    }

    public List<SubjectResponseDTO> getSubjects(Long id){
        TeachingUnit teachingUnit = teachingUnitRepository.findById(id)
                .orElseThrow(()-> new RessourceNotFoundException("TeachingUnit not found : " + id));

        return teachingUnit.getSubjects()
                .stream()
                .map(subject -> new SubjectResponseDTO(
                        subject.getId(),
                        subject.getTeachingUnit().getId(),
                        subject.getName(),
                        subject.getDescription(),
                        subject.getContent(),
                        subject.getEcts()
                ))
                .toList();
    }

    private TeachingUnitResponseDTO convertToDTO(TeachingUnit teachingUnit) {
        return new TeachingUnitResponseDTO(
                teachingUnit.getId(),
                teachingUnit.getSemester().getId(),
                teachingUnit.getName(),
                teachingUnit.getCategory(),
                teachingUnit.getEcts()
        );
    }
}
