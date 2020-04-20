package com.gpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class StudentResult {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private float mark1;
	
	private float mark2;
	
	private float mark3;
	
	private float averageMark;
	
	private String markToChar;
	
//	private boolean pass;
	
	@OneToOne
	@JoinColumn(name = "student_register_id", nullable = false)
	private StudentRegister studentRegister;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getMark1() {
		return mark1;
	}

	public void setMark1(float mark1) {
		this.mark1 = mark1;
	}

	public float getMark2() {
		return mark2;
	}

	public void setMark2(float mark2) {
		this.mark2 = mark2;
	}

	public float getMark3() {
		return mark3;
	}

	public void setMark3(float mark3) {
		this.mark3 = mark3;
	}

	public float getAverageMark() {
		return averageMark;
	}

	public void setAverageMark(float averageMark) {
		this.averageMark = averageMark;
	}

	

	public String getMarkToChar() {
		return markToChar;
	}

	public void setMarkToChar(String markToChar) {
		this.markToChar = markToChar;
	}

//	public boolean isPass() {
//		return pass;
//	}
//
//	public void setPass(boolean pass) {
//		this.pass = pass;
//	}

	public StudentRegister getStudentRegister() {
		return studentRegister;
	}

	public void setStudentRegister(StudentRegister studentRegister) {
		this.studentRegister = studentRegister;
	}

	@Override
	public String toString() {
		return "StudentResult [id=" + id + ", mark1=" + mark1 + ", mark2=" + mark2 + ", mark3=" + mark3
				+ ", averageMark=" + averageMark + ", markToChar=" + markToChar 
				+ ", studentRegister=" + studentRegister + "]";
	}
	
	
}
