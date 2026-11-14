package com.eduadvice.backend.service;

import com.eduadvice.backend.dto.ParcoursDTO;
import com.eduadvice.backend.dto.SemesterDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.entity.Parcours;
import com.eduadvice.backend.entity.Semester;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    private SemesterDTO toDTO(Semester semester){
        // intialization for the parcours DTO
        Parcours parcours = semester.getParcours();

        SemesterDTO dto = new SemesterDTO(
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

        return dto;
    }

    public List<SemesterDTO> getAllSemesters() {
        return semesterRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public SemesterDTO getSemesterById(Long id) {
        Optional<Semester> semester =  semesterRepository.findById(id);

        if(semester.isEmpty()){
            throw new RessourceNotFoundException("Semester with the id : " + id + " not found");
        }

        return toDTO(semester.get());
    }

    public ParcoursDTO getParcours(Long semesterId){
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Semester not found: " + semesterId
                ));

        Parcours parcours = semester.getParcours();

        return new ParcoursDTO(
                        parcours.getId(),
                        parcours.getName(),
                        parcours.getLevel(),
                        parcours.getDiploma(),
                        parcours.getMention(),
                        parcours.getEstablishment(),
                        parcours.getDescription()
                );
    }

    public List<TeachingUnitResponseDTO> getTeachingUnits(Long id){
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Semester not found: " + id));

        return semester.getTeachingUnits()
                .stream()
                .map(teachingUnit -> new TeachingUnitResponseDTO(
                        teachingUnit.getId(),
                        teachingUnit.getSemester().getId(),
                        teachingUnit.getName(),
                        teachingUnit.getCategory(),
                        teachingUnit.getEcts()
                ))
                .toList();
    }
}