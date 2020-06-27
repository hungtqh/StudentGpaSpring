package com.gpa.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.gpa.domain.StudentResult;

@SpringBootTest
class ResultServiceTest {

	@Autowired
	private ResultService resultService;
	
	/* TestFindResultInSemester */
	
	@Test
	void testFindResultInSemesterSuccess() {
		List<StudentResult> results = resultService.findResultInSemester(2L, "20191");
		
		assertEquals(3, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound1() {
		List<StudentResult> results = resultService.findResultInSemester(100L, "20191");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound2() {
		List<StudentResult> results = resultService.findResultInSemester(2L, "FakeSemster");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound3() {
		List<StudentResult> results = resultService.findResultInSemester(100L, "FakeSemster");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound4() {
		List<StudentResult> results = resultService.findResultInSemester(null, null);
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound5() {
		List<StudentResult> results = resultService.findResultInSemester(null, "20191");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound6() {
		List<StudentResult> results = resultService.findResultInSemester(2L, null);
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound7() {
		List<StudentResult> results = resultService.findResultInSemester(null, "FakeSemester");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultInSemesterNotFound8() {
		List<StudentResult> results = resultService.findResultInSemester(100L, null);
		
		assertEquals(0, results.size());
	}
	
	/* TestFindResultTillSemester */
	
	@Test
	void testFindResultTillSemester() {
		List<StudentResult> results = resultService.findResultTillSemester(2L, "20193");
		
		assertEquals(9, results.size());
	}

	@Test
	void testFindResultTillSemesterNotFound1() {
		List<StudentResult> results = resultService.findResultTillSemester(100L, "20192");
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultTillSemesterNotFound2() {
		List<StudentResult> results = resultService.findResultTillSemester(2L, null);
		
		assertEquals(0, results.size());
	}

	@Test
	void testFindResultTillSemesterNotFound3() {
		List<StudentResult> results = resultService.findResultTillSemester(100L, null);
		
		assertEquals(0, results.size());
	}
	
	@Test
	void testFindResultTillSemesterNotFound4() {
		// this should fail
		List<StudentResult> results = resultService.findResultTillSemester(2L, "FakeSemester");
		
		assertEquals(0, results.size());
	}
}
