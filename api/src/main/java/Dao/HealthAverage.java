package Dao;

import java.io.Serializable;

public class HealthAverage implements Serializable {

	private String year;
	private String question;
	private String average;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}
}
