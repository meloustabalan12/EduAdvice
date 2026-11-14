
package com.eduadvice.backend.service;

import com.eduadvice.backend.dto.SubjectResponseDTO;
import com.eduadvice.backend.dto.TeachingUnitResponseDTO;
import com.eduadvice.backend.entity.Subject;
import com.eduadvice.backend.entity.TeachingUnit;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Subject not found with id: " + id
                ));

        return convertToDTO(subject);
    }

    public TeachingUnitResponseDTO getTeachingUnit(Long subjectId){
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RessourceNotFoundException(
                        "Subject not found: " + subjectId
                ));
        TeachingUnit teachingUnit = subject.getTeachingUnit();

        return  new TeachingUnitResponseDTO(
                teachingUnit.getId(),
                teachingUnit.getSemester().getId(),
                teachingUnit.getName(),
                teachingUnit.getCategory(),
                teachingUnit.getEcts()
        );
    }

    private SubjectResponseDTO convertToDTO(Subject subject) {
        return new SubjectResponseDTO(
                subject.getId(),
                subject.getTeachingUnit().getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getContent(),
                subject.getEcts()
        );
    }
}
