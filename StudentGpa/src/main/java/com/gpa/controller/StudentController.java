package com.gpa.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gpa.domain.Semester;
import com.gpa.domain.Student;
import com.gpa.domain.StudentResult;
import com.gpa.domain.User;
import com.gpa.service.ResultService;
import com.gpa.service.SemesterService;
import com.gpa.service.UserService;
import com.gpa.utility.ExcelGenerator;
import com.gpa.utility.MarkUtility;

@Controller
public class StudentController {

	@Autowired
	private UserService userService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private ResultService resultService;

	@GetMapping("/studentMark")
	public String studentMarkCurrentSemester(Model model, Principal principal) {
		Semester currentSemester = semesterService.findCurrentSemester();
		
		return viewMark(currentSemester.getName(), model, principal);

	}

	@PostMapping("/studentMark")
	public String studentMarkInSemester(@ModelAttribute("semesterName") String semesterName, Model model,
			Principal principal) {
		
		return viewMark(semesterName, model, principal);
	}

	@GetMapping("/studentAllMarks")
	public String viewMarkAllSemester(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		Student student = user.getStudent();
		model.addAttribute("student", student);
		model.addAttribute("classActiveAllMarks", true);
		model.addAttribute("classActiveMark", false);

		// find all semesters name
		List<Semester> semesters = semesterService.findAll();

		// find all result in each semester
		MarkUtility.listMarks.clear();
		for (Semester s : semesters) {
			String semesterName = s.getName();
			List<StudentResult> studentResults = resultService.findResultInSemester(student.getId(), semesterName);
			List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(),
					semesterName);

			// results
			List<Object> listResultsInSemester = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
			List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);

			listResultsInSemester.add(listGpaResultsTillNow.get(0));
			listResultsInSemester.add(listGpaResultsTillNow.get(1));
			
			/*
			 * semesterName -- 0 
			 * marks -- 1 
			 * gpaInSemester -- 2 
			 * passedCredits -- 3
			 * passedCreditsTillNow-- 4 
			 * gpaTillNow -- 5
			 */

			// put results
			MarkUtility.listMarks.add(listResultsInSemester);
		}

		model.addAttribute("listMarks", MarkUtility.listMarks);

		return "studentMark";
	}

	public String viewMark(String semesterName, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		Student student = user.getStudent();
		model.addAttribute("student", student);
		model.addAttribute("classActiveAllMarks", false);
		model.addAttribute("classActiveMark", true);

		List<StudentResult> studentResults = resultService.findResultInSemester(student.getId(), semesterName);

		List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(), semesterName);

		if (semesterService.findBySemesterName(semesterName) != null) {
			List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);

			model.addAttribute("hasResult", true);
			model.addAttribute("semesterName", listResults.get(0));
			model.addAttribute("marks", listResults.get(1));
			model.addAttribute("gpaInSemester", listResults.get(2));
			model.addAttribute("passedCredits", listResults.get(3));
			
			List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
			model.addAttribute("passedCreditsTillNow", listGpaResultsTillNow.get(0));
			model.addAttribute("gpaTillNow", listGpaResultsTillNow.get(1));
			
			listResults.add(listGpaResultsTillNow.get(0));
			listResults.add(listGpaResultsTillNow.get(1));
			
			MarkUtility.listMarks.clear();
			MarkUtility.listMarks.add(listResults);
		}

		return "studentMark";
	}

	@GetMapping("/download/studentMarks.xlsx")
	public ResponseEntity<InputStreamResource> excelMarksReport(Principal principal) throws IOException {

		if (MarkUtility.listMarks.size() == 0) {
			return null;
		}
		
		User user = userService.findByUsername(principal.getName());

		Student student = user.getStudent();
		
		ByteArrayInputStream in = ExcelGenerator.allSemesterMarksToExcel(student, MarkUtility.listMarks);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=studentMarksExport.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
}
