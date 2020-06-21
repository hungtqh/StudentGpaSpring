package com.gpa.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.gpa.domain.Student;
import com.gpa.domain.StudentResult;
import com.gpa.domain.Subject;
import com.gpa.domain.User;
import com.gpa.service.ResultService;
import com.gpa.service.UserService;

@SpringBootTest
@Transactional
class MarkUtilityTest {
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private UserService userService;

	@Test
	void testMarkInGpaSuccess1() {
		float mark = MarkUtility.markInGpa("A+");
		
		assertEquals(4f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess2() {
		float mark = MarkUtility.markInGpa("A");
		
		assertEquals(3.7f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess3() {
		float mark = MarkUtility.markInGpa("B+");
		
		assertEquals(3.5f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess4() {
		float mark = MarkUtility.markInGpa("B");
		
		assertEquals(3.0f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess5() {
		float mark = MarkUtility.markInGpa("C+");
		
		assertEquals(2.5f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess6() {
		float mark = MarkUtility.markInGpa("C");
		
		assertEquals(2.0f, mark);
	}

	@Test
	void testMarkInGpaSuccess7() {
		float mark = MarkUtility.markInGpa("D+");
		
		assertEquals(1.5f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess8() {
		float mark = MarkUtility.markInGpa("D");
		
		assertEquals(1f, mark);
	}
	
	@Test
	void testMarkInGpaSuccess9() {
		float mark = MarkUtility.markInGpa("F");
		
		assertEquals(0f, mark);
	}
	
	@Test
	void testMarkInGpaWrongFormat1() {
		
		assertThrows(NullPointerException.class, () -> {
			MarkUtility.markInGpa("");
		});
	}
	
	@Test
	void testMarkInGpaWrongFormat2() {
		
		assertThrows(NullPointerException.class, () -> {
			MarkUtility.markInGpa("ab2c");
		});
	}
	
	@Test
	void testMarkInGpaWrongFormat3() {
		
		assertThrows(NullPointerException.class, () -> {
			MarkUtility.markInGpa(null);
		});
	}
	
	@Test
	void testCalculateMarkInSemesterSuccess() {
		String semesterName = "20192";
		
		User user = userService.findByUsername("B16DCCN168");
		Student student = user.getStudent();
		
		List<StudentResult> studentResults = resultService.findResultInSemester(student.getId(), semesterName);
	
		// result after calculated marks
		List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		
		// semesterFullName, numbersOfSubjects, gpaInSemester, passedCredits
		String semesterFN = (String) listResults.get(0);
		int numberOfSubjects = ((Map<Subject, StudentResult>) listResults.get(1)).size();
		float gpaInSemester = (float) listResults.get(2);
		int passedCredits = (int) listResults.get(3);		
		
		assertTrue(
				semesterFN.equals("2 - Năm học 2019-2020") &&
				numberOfSubjects == 2 &&
				gpaInSemester == 3 &&
				passedCredits == 6
				);
	}
	
	@Test
	void testCalculateMarkInSemesterFail1() {
		// test with empty data
		String semesterName = "";
		
		List<StudentResult> studentResults = new ArrayList<>();
	
		// result after calculated marks
		List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		
		// semesterFullName, numbersOfSubjects, gpaInSemester, passedCredits
		String semesterFN = (String) listResults.get(0);
		int numberOfSubjects = ((Map<Subject, StudentResult>) listResults.get(1)).size();
		float gpaInSemester = (float) listResults.get(2);
		int passedCredits = (int) listResults.get(3);		
		
		assertTrue(
				semesterFN.equals("") &&
				numberOfSubjects == 0 &&
				gpaInSemester == 0 &&
				passedCredits == 0
				);				
	}
	
	@Test
	void testCalculateMarkInSemesterFail2() {
		// test with empty data
		String semesterName = null;
		
		List<StudentResult> studentResults = new ArrayList<>();
	
		// result after calculated marks
		List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		
		// semesterFullName, numbersOfSubjects, gpaInSemester, passedCredits
		String semesterFN = (String) listResults.get(0);
		int numberOfSubjects = ((Map<Subject, StudentResult>) listResults.get(1)).size();
		float gpaInSemester = (float) listResults.get(2);
		int passedCredits = (int) listResults.get(3);		
		
		assertTrue(
				semesterFN.equals("") &&
				numberOfSubjects == 0 &&
				gpaInSemester == 0 &&
				passedCredits == 0
				);				
	}
	
	@Test
	void testCalculateMarkInSemesterFail3() {
		String semesterName = "123456";
		
		List<StudentResult> studentResults = new ArrayList<>();
	
		// result after calculated marks
		List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		
		// semesterFullName, numbersOfSubjects, gpaInSemester, passedCredits
		String semesterFN = (String) listResults.get(0);
		int numberOfSubjects = ((Map<Subject, StudentResult>) listResults.get(1)).size();
		float gpaInSemester = (float) listResults.get(2);
		int passedCredits = (int) listResults.get(3);		
		
		assertTrue(
				semesterFN.equals("") &&
				numberOfSubjects == 0 &&
				gpaInSemester == 0 &&
				passedCredits == 0
				);				
	}
	
	@Test
	void testCalculateMarkInSemesterFail4() {
		String semesterName = "20191";
		
		List<StudentResult> studentResults = new ArrayList<>();
	
		// result after calculated marks
		List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		
		// semesterFullName, numbersOfSubjects, gpaInSemester, passedCredits
		String semesterFN = (String) listResults.get(0);
		int numberOfSubjects = ((Map<Subject, StudentResult>) listResults.get(1)).size();
		float gpaInSemester = (float) listResults.get(2);
		int passedCredits = (int) listResults.get(3);		
		
		assertTrue(
				semesterFN.equals("1 - Năm học 2019-2020") &&
				numberOfSubjects == 0 &&
				gpaInSemester == 0 &&
				passedCredits == 0
				);				
	}
	
	@Test
	void testCalculateMarkInSemesterFail5() {
		String semesterName = "20191";
		
		List<StudentResult> studentResults = null;
	
		assertThrows(NullPointerException.class, () -> {
			MarkUtility.calculateMarkInSemester(semesterName, studentResults);
		});	
				
	}

	@Test
	void testCalculateMarkTillSemesterSuccess1() {
		String semesterName = "20192";
		
		User user = userService.findByUsername("B16DCCN168");
		Student student = user.getStudent();
		List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(),
				semesterName);
		
		List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		int passedCreditsTillNow = (int) listGpaResultsTillNow.get(0);
		float gpaTillNow = (float) listGpaResultsTillNow.get(1);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		assertTrue(
				passedCreditsTillNow == 12 &&
				df.format(gpaTillNow).equals("2.8")
				);
	}
	
	@Test
	void testCalculateMarkTillSemesterSuccess2() {
		// test with studied again case, better result
		
		String semesterName = "20193";
		
		User user = userService.findByUsername("B16DCCN168");
		Student student = user.getStudent();
		List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(),
				semesterName);
		
		String mmtMark = "", tthcmMark = "";
		
		for (StudentResult result : resultsTillSemester) {
			Subject subject = result.getStudentRegister().getCourse().getSubject();
			if (subject.getName().equals("Mạng máy tính")) {
				mmtMark = result.getMarkToChar();
			}
			
			if (subject.getName().equals("Tư tưởng Hồ Chí Minh")) {
				tthcmMark = result.getMarkToChar();
			}
		}
		
		List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		int passedCreditsTillNow = (int) listGpaResultsTillNow.get(0);
		float gpaTillNow = (float) listGpaResultsTillNow.get(1);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		assertTrue(
				mmtMark.equals("B") &&
				tthcmMark.equals("A") &&
				passedCreditsTillNow == 15 &&
				df.format(gpaTillNow).equals("3.64")
				);
	}
	
	@Test
	void testCalculateMarkTillSemesterSuccess3() {
		// test with studied again case, worse result
	
		String semesterName = "20193";
		
		User user = userService.findByUsername("B17DCCN123");
		Student student = user.getStudent();
		List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(),
				semesterName);
		
		String tktMark = "";
		
		for (StudentResult result : resultsTillSemester) {
			Subject subject = result.getStudentRegister().getCourse().getSubject();
			if (subject.getName().equals("Toán kinh tế")) {
				tktMark = result.getMarkToChar();
				break;
			}
		}
		
		List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		int passedCreditsTillNow = (int) listGpaResultsTillNow.get(0);
		float gpaTillNow = (float) listGpaResultsTillNow.get(1);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		assertTrue(
				tktMark.equals("C") && 
				passedCreditsTillNow == 18 && 
				df.format(gpaTillNow).equals("3.19")
				);
	}
	
	@Test
	void testCalculateMarkTillSemesterFail1() {
		List<StudentResult> resultsTillSemester = new ArrayList<>();
		
		List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		int passedCreditsTillNow = (int) listGpaResultsTillNow.get(0);
		float gpaTillNow = (float) listGpaResultsTillNow.get(1);
		
		assertTrue(
				passedCreditsTillNow == 0 &&
				gpaTillNow == 0
				);
	}
	
	@Test
	void testCalculateMarkTillSemesterFail2() {
		List<StudentResult> resultsTillSemester = null;
		
		assertThrows(NullPointerException.class, () -> {
			MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		});
	}

}
