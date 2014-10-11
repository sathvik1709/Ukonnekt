package com.seteam3.parameters;

public class FeedbackResultParameters {

	private int errorId;
	private Float teaching;
	private Float courseWork;
	private Float grading;
	private Float gtaSupport;

	public FeedbackResultParameters(float teaching, float courseWork, float grading, float gtaSupport) {
		
		this.teaching = teaching;
		this.courseWork = courseWork;
		this.grading = grading;
		this.gtaSupport = gtaSupport;
	}

	public int getErrorId() {
		return errorId;
	}

	public Float getTeaching() {
		return teaching;
	}

	public void setTeaching(Float teaching) {
		this.teaching = teaching;
	}

	public Float getCourseWork() {
		return courseWork;
	}

	public void setCourseWork(Float courseWork) {
		this.courseWork = courseWork;
	}

	public Float getGrading() {
		return grading;
	}

	public void setGrading(Float grading) {
		this.grading = grading;
	}

	public Float getGtaSupport() {
		return gtaSupport;
	}

	public void setGtaSupport(Float gtaSupport) {
		this.gtaSupport = gtaSupport;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

}
