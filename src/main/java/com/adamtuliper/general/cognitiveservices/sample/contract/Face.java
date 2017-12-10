package com.adamtuliper.general.cognitiveservices.sample.contract;

/**
 * A Face on the MCS Face Service
 * @author csmith
 *
 */
public class Face {
	String faceId;
	Person person;
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
