package com.eduadvice.backend.service;

import com.eduadvice.backend.dto.CareerOpportunityDTO;
import com.eduadvice.backend.dto.ParcoursDTO;
import com.eduadvice.backend.dto.SemesterDTO;
import com.eduadvice.backend.entity.Parcours;
import com.eduadvice.backend.exception.RessourceNotFoundException;
import com.eduadvice.backend.repository.ParcoursRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParcoursService {

    @Autowired
    ParcoursRepository parcoursRepository;

    private ParcoursDTO toDTO(Parcours parcours) {
        ParcoursDTO dto = new ParcoursDTO(
                parcours.getId(),
                parcours.getName(),
                parcours.getLevel(),
                parcours.getDiploma(),
                parcours.getMention(),
                parcours.getEstablishment(),
                parcours.getDescription()
                );

        return dto;
    }

    public List<ParcoursDTO> getAllParcours(){
        return parcoursRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ParcoursDTO getParcoursById(Long id){
        Optional<Parcours> parcours = parcoursRepository.findById(id);

        if(parcours.isEmpty()) {
            throw new RessourceNotFoundException("Le parcours avec l'identifiant " + id + " n'existe pas.");
        }

        return toDTO(parcours.get());
    }

    public List<CareerOpportunityDTO> getCareerOpportunities(Long id) {
        Parcours parcours = parcoursRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Le parcours avec l'identifiant"  + id + " n'existe pas."));

        return parcours.getCareerOpportunities()
                .stream()
                .map(career -> new CareerOpportunityDTO(
                        career.getId(),
                        career.getTitle(),
                        career.getSector(),
                        career.getDescription(),
                        career.getRequired_skills()
                ))
                .toList();
    }

    public List<SemesterDTO> getSemesters(Long id){
        Parcours parcours = parcoursRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Le parcours avec l'identifiant"  + id + " n'existe pas."));

        return parcours.getSemesters()
                .stream()
                .map(semester -> new SemesterDTO(
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
                ))
                .toList();
    }
}
