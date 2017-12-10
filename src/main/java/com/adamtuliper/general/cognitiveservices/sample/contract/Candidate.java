package com.adamtuliper.general.cognitiveservices.sample.contract;

/**
 * 
 * @author csmith
 *
 */
public class Candidate {
	Person person;
	Float confidenceLevel;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Float getConfidenceLevel() {
		return confidenceLevel;
	}
	public void setConfidenceLevel(Float confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
}
