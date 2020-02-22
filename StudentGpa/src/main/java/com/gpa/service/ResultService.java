package com.gpa.service;

import java.util.List;

import com.gpa.domain.StudentResult;

public interface ResultService {

	List<StudentResult> findResultInSemester(Long studentId, String semesterName);

	List<StudentResult> findResultTillSemester(Long studentId, String semesterName);

}
