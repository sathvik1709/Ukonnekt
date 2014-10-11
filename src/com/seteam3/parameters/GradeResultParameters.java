package com.seteam3.parameters;

public class GradeResultParameters {
	private String examId;
	private String grade;
	private String maxGrade;
	
	public GradeResultParameters(String examId, String grade, String maxGrade) {
		
		this.examId = examId;
		this.grade = grade;
		this.maxGrade = maxGrade;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(String maxGrade) {
		this.maxGrade = maxGrade;
	}
	
	

}
