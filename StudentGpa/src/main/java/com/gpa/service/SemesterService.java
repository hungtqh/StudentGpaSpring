package com.gpa.service;

import java.util.List;

import com.gpa.domain.Semester;

public interface SemesterService {

	Semester findCurrentSemester();

	List<Semester> findAll();

}
