package com.adamtuliper.general.cognitiveservices.sample.contract;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A Person on the MCS Face Service
 * 
 * @author csmith
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements MCSFaceObject{
	String personId;
	String personGroupId;
	String name;
	String userData; 
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
	public String getPersonGroupId() {
		return personGroupId;
	}
	public void setPersonGroupId(String personGroupId) {
		this.personGroupId = personGroupId;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
}
