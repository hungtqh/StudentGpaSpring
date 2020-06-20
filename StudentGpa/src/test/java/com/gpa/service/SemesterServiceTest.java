package com.gpa.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.gpa.domain.Semester;

@SpringBootTest
@Transactional
class SemesterServiceTest {

	@Autowired
	private SemesterService semesterService;
	
	/* TestFindCurentSemester */
	
	@Test
	void testFindCurrentSemester() {
		Semester semester = semesterService.findCurrentSemester();
		
		assertEquals("20192", semester.getName());
	}

	/* TestFindAllSemester */
	
	@Test
	void testFindAll() {
		List<Semester> semesters = semesterService.findAll();
		
		assertEquals(3, semesters.size());
	}
	
	/* TestFindBySemesterName */

	@Test
	void testFindBySemesterNameSuccess() {
		Semester semester = semesterService.findBySemesterName("20191");
		
		assertEquals("20191", semester.getName());
	}
	
	@Test
	void testFindBySemesterNameNotFound1() {
		Semester semester = semesterService.findBySemesterName("FakeSemester");
		
		assertNull(semester);
	}
	
	@Test
	void testFindBySemesterNameNotFound2() {
		Semester semester = semesterService.findBySemesterName("");
		
		assertNull(semester);
	}
	
	@Test
	void testFindBySemesterNameNotFound3() {
		Semester semester = semesterService.findBySemesterName(null);
		
		assertNull(semester);
	}
}
