package com.seteam3.parameters;

public class CourseResultParameters {
	private String courseID;
	private String title;
	private String description;
	
	public CourseResultParameters(String courseID) {
		
		this.courseID = courseID;
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
}
