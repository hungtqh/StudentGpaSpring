package com.gpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpa.domain.Semester;
import com.gpa.repository.SemesterRepository;
import com.gpa.service.SemesterService;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;
	
	@Override
	public Semester findCurrentSemester() {
		return semesterRepository.findCurrentSemester();
	}

	@Override
	public List<Semester> findAll() {
		return (List<Semester>) semesterRepository.findAll();
	}

}
