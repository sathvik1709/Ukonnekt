package com.seteam3.parameters;

public class TimeTableParameters {
	
	String todayDate = "";
	String resultsFor =  "";
	String courses = "";
	
	public TimeTableParameters(String date, String resultsFor, String courses){
		
		this.todayDate = date;
		this.resultsFor = resultsFor;
		this.courses = courses;
	}

	public String gettodayDate()
	{
		return todayDate;
	}
	
	public String getResultsFor()
	{
		return resultsFor;
	}
	
	public String getcourses()
	{
		return courses;
	}
	
}
