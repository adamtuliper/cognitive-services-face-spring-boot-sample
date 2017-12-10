package com.adamtuliper.general.cognitiveservices.sample.contract;

/**
 * A Person on the MCS Face Service
 * 
 * @author csmith
 *
 */
public class Person {
	String personId;
	String name;
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
