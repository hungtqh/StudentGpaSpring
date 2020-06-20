package com.gpa.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String code;
	
	private String name;
	
	private int numberOfCredits;
	private float mark1Percent;
	private float mark2Percent;
	private float mark3Percent;
	
	@ManyToMany
	@JoinTable(
			  name = "major_subject", 
			  joinColumns = @JoinColumn(name = "subject_id"), 
			  inverseJoinColumns = @JoinColumn(name = "major_id"))
	private List<Major> majors;
	
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

	public List<Major> getMajors() {
		return majors;
	}

	public void setMajors(List<Major> majors) {
		this.majors = majors;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
