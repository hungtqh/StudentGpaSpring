package com.gpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.gpa.domain.Semester;

public interface SemesterRepository extends CrudRepository<Semester, Long> {

	Semester findByActiveTrue();

	Semester findByName(String semesterName);
}
