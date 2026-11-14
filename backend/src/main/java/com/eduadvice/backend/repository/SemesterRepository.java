package com.eduadvice.backend.repository;

import com.eduadvice.backend.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

}