package com.gpa.utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gpa.domain.StudentResult;
import com.gpa.domain.Subject;

public class MarkUtility {
	
	public static List<List<Object>> listMarks = new ArrayList<>();;
	
	public static float markInGpa(String markByChar) {
		float result = 0;
		switch (markByChar) {
		
			case "A+":
				result = 4;
				break;
			case "A":
				result = 3.7f;
				break;
			case "B+":
				result = 3.5f;
				break;
			case "B":
				result = 3;
				break;
			case "C+":
				result = 2.5f;
				break;	
			case "C":
				result = 2;
				break;	
			case "D+":
				result = 1.5f;
				break;	
			case "D":
				result = 1;
				break;
			case "F":
				result = 0;
				break;	
		}
		
		return result;
	}

	public static List<Object> calculateMarkInSemester(String semesterName, List<StudentResult> studentResults) {
		Map<Subject, StudentResult> marks = new LinkedHashMap<>();

		for (StudentResult s : studentResults) {
			Subject subject = s.getStudentRegister().getCourse().getSubject();

			marks.put(subject, s);
		}

		float gpaInSemester = 0;
		int creditsInSemester = 0;
		int passedCredits = 0;

		for (Map.Entry<Subject, StudentResult> entry : marks.entrySet()) {
			int subjectCredits = entry.getKey().getNumberOfCredits();
			String charMark = entry.getValue().getMarkToChar();

			if (charMark.compareTo("F") != 0) {
				passedCredits += subjectCredits;
			}
			creditsInSemester += subjectCredits;
			gpaInSemester += MarkUtility.markInGpa(charMark) * subjectCredits;
		}

		gpaInSemester /= creditsInSemester;
		
		String semesterFullName = semesterName.charAt(4) + " - Năm học ";
		int semesterInt = Integer.parseInt(semesterName.substring(0, 4));
		semesterFullName += String.valueOf(semesterInt) + '-' + String.valueOf(semesterInt + 1);
		
		List<Object> listResults = new ArrayList<>();
		listResults.add(semesterFullName);
		listResults.add(marks);
		listResults.add(gpaInSemester);
		listResults.add(passedCredits);
		
		return listResults;
	}

	public static List<Object> calculateMarkTillSemester(List<StudentResult> resultsTillSemester) {
		/* Calculate gpa till the given semester */
		Map<Subject, StudentResult> marksTillNow = new LinkedHashMap<>();
		for (StudentResult currentResult : resultsTillSemester) {

			/* if the student studied again, check and choose the better result */
			Subject subject = currentResult.getStudentRegister().getCourse().getSubject();

			if (marksTillNow.containsKey(subject)) {
				StudentResult lastResult = marksTillNow.get(subject);

				if (currentResult.getAverageMark() >= lastResult.getAverageMark()) {
					marksTillNow.put(subject, currentResult);
				}
			} else {
				marksTillNow.put(subject, currentResult);
			}
		}
		
		/* Iterate and get gpa till now */
		int passedCreditsTillNow = 0;
		int totalCreditsTillNow = 0;
		float gpaTillNow = 0;
		
		for (Map.Entry<Subject, StudentResult> entry : marksTillNow.entrySet()) {
			int subjectCredits = entry.getKey().getNumberOfCredits();
			String charMark = entry.getValue().getMarkToChar();

			if (charMark.compareTo("F") != 0) {
				passedCreditsTillNow += subjectCredits;
			}
			totalCreditsTillNow += subjectCredits;
			gpaTillNow += MarkUtility.markInGpa(charMark) * subjectCredits;
		}
		
		gpaTillNow /= totalCreditsTillNow;
		
		List<Object> listGpaResultsTillNow = new ArrayList<>();
		
		listGpaResultsTillNow.add(passedCreditsTillNow);
		listGpaResultsTillNow.add(gpaTillNow);
		
		return listGpaResultsTillNow;
		
	}
}
