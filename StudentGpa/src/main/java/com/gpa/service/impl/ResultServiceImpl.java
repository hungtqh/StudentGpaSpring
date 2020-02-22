package com.gpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpa.domain.StudentResult;
import com.gpa.repository.ResultRepository;
import com.gpa.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

	@Autowired
	private ResultRepository resultRepository;
	
	@Override
	public List<StudentResult> findResultInSemester(Long studentId, String semesterName) {
		return resultRepository.findResultInSemester(studentId, semesterName);
	}

	@Override
	public List<StudentResult> findResultTillSemester(Long studentId, String semesterName) {
		return resultRepository.findResultTillSemester(studentId, semesterName);
	}

}
