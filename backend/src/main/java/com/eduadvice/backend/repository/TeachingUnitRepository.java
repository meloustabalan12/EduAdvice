
package com.eduadvice.backend.repository;

import com.eduadvice.backend.entity.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingUnitRepository
        extends JpaRepository<TeachingUnit, Long> {

    List<TeachingUnit> findBySemesterId(Long semesterId);
}
