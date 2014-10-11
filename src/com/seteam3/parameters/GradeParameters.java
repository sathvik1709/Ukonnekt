package com.seteam3.parameters;

public class GradeParameters {
	private String studentId;
	private String courseId;
		
	public GradeParameters(String studentId, String courseId) {
		
		this.studentId = studentId;
		this.courseId = courseId;
		}
	
	public String getstudentId() {
		return studentId;
	}

	public String getcourseId() {
		return courseId;
	}
}
