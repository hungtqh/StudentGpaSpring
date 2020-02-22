package com.gpa.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gpa.utility.MarkUtility;

@Controller
@RequestMapping("/student")
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
	
	@GetMapping("/viewAllMarks")
	public String viewMarkAllSemester(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		Student student = user.getStudent();
		model.addAttribute("student", student);
		
		// find all semesters name
		List<Semester> semesters = semesterService.findAll();
		
		
		// find all result in each semester
		List<List<Object>> listMarks = new ArrayList<>();
		for (Semester s : semesters) {
			String semesterName = s.getName();
			List<StudentResult> studentResults = resultService.findResultInSemester(student.getId(), semesterName);
			List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(), semesterName);
			
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
			
			//put results 
			listMarks.add(listResultsInSemester);
		}
		
		model.addAttribute("listMarks", listMarks);
		
		return "studentAllMark";
	}
	

	public String viewMark(String semesterName, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		Student student = user.getStudent();
		model.addAttribute("student", student);

		List<StudentResult> studentResults = resultService.findResultInSemester(student.getId(), semesterName);

		List<StudentResult> resultsTillSemester = resultService.findResultTillSemester(student.getId(), semesterName);

		if (studentResults.size() > 0) {
			List<Object> listResults = MarkUtility.calculateMarkInSemester(semesterName, studentResults);
			
			model.addAttribute("hasResult", true);
			model.addAttribute("semesterName", listResults.get(0));
			model.addAttribute("marks", listResults.get(1)); 
			model.addAttribute("gpaInSemester", listResults.get(2));
			model.addAttribute("passedCredits", listResults.get(3));	
		}

		List<Object> listGpaResultsTillNow = MarkUtility.calculateMarkTillSemester(resultsTillSemester);
		model.addAttribute("passedCreditsTillNow", listGpaResultsTillNow.get(0));
		model.addAttribute("gpaTillNow", listGpaResultsTillNow.get(1));
		
		return "studentMark";
	}
}
