package com.seteam3.parameters;

public class PostAnnouncementParameters {
	private String courseID;
	private String dueDate;
	private String title;
	private String description;
	
	public PostAnnouncementParameters(String courseID, String title, String description) {
		
		this.courseID = courseID;
		this.title = title;
		this.description = description;
		}
	
	public PostAnnouncementParameters(String courseID, String dueDate, String title, String description) {
		
		this.courseID = courseID;
		this.dueDate = dueDate;
		this.title = title;
		this.description = description;
		}
	
	public String getDueDate() {
		return dueDate;
	}

	public String gettitle() {
		return title;
	}

	public String getdescription() {
		return description;
	}

	public String getCourseID() {
		return courseID;
	}

}
