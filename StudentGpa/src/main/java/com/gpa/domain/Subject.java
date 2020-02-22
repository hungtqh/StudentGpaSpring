package com.gpa.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Subject implements Comparable<Subject> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String code;
	
	private String name;
	
	private int numberOfCredits;
	private float mark1Percent;
	private float mark2Percent;
	private float mark3Percent;
	
	@ManyToOne
	@JoinColumn(name = "major_id", nullable = false)
	private Major major;
	
	@OneToMany(mappedBy = "subject")
	private List<Course> courses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfCredits() {
		return numberOfCredits;
	}

	public void setNumberOfCredits(int numberOfCredits) {
		this.numberOfCredits = numberOfCredits;
	}

	public float getMark1Percent() {
		return mark1Percent;
	}

	public void setMark1Percent(float mark1Percent) {
		this.mark1Percent = mark1Percent;
	}

	public float getMark2Percent() {
		return mark2Percent;
	}

	public void setMark2Percent(float mark2Percent) {
		this.mark2Percent = mark2Percent;
	}

	public float getMark3Percent() {
		return mark3Percent;
	}

	public void setMark3Percent(float mark3Percent) {
		this.mark3Percent = mark3Percent;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int compareTo(Subject s) {
		return this.name.compareTo(s.name);
	}
	
	
}
