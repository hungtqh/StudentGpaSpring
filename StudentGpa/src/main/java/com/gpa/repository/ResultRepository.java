package com.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gpa.domain.StudentResult;

public interface ResultRepository extends CrudRepository<StudentResult, Long> {

	@Query("Select result From StudentResult result, Student stu, StudentRegister sreg, Course c, Semester sm "
			+ "Where sm.name = ?2 AND result.studentRegister.id = sreg.id AND "
			+ "stu.id = sreg.student.id AND c.id = sreg.course.id AND stu.id = ?1 AND " + "sm.id = c.semester.id")
	List<StudentResult> findResultInSemester(Long studentId, String semesterName);
	
	@Query("Select result From StudentResult result, Student stu, StudentRegister sreg, Course c, Semester sm "
			+ "Where sm.name <= ?2 AND result.studentRegister.id = sreg.id AND "
			+ "stu.id = sreg.student.id AND c.id = sreg.course.id AND stu.id = ?1 AND " + "sm.id = c.semester.id")
	List<StudentResult> findResultTillSemester(Long studentId, String semsesterName);
}
