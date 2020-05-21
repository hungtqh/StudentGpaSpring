package com.gpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gpa.domain.Semester;

public interface SemesterRepository extends CrudRepository<Semester, Long> {

	@Query("select s from Semester s where s.active = true")
	Semester findCurrentSemester();

	Semester findByName(String semesterName);
}
