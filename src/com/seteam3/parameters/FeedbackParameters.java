package com.seteam3.parameters;

public class FeedbackParameters {

	private String netId;
	private String courseId;
	private Float teaching;
	private Float courseWork;
	private Float grading;
	private Float gtaSupport;
	private String comment;

	public FeedbackParameters(String netId, String courseId, Float teaching,
			Float courseWork, Float grading, Float gtaSupport, String comment) {

		this.netId = netId;
		this.courseId = courseId;
		this.teaching = teaching;
		this.courseWork = courseWork;
		this.grading = grading;
		this.gtaSupport = gtaSupport;
		this.comment = comment;
	}
	
	public FeedbackParameters(String courseId){
		this.courseId = courseId;
	}

	public String getNetId() {
		return netId;
	}

	public String getCourseId() {
		return courseId;
	}

	public Float getTeaching() {
		return teaching;
	}

	public Float getCourseWork() {
		return courseWork;
	}

	public Float getGrading() {
		return grading;
	}

	public Float getGtaSupport() {
		return gtaSupport;
	}

	public String getComment() {
		return comment;
	}
	
}
