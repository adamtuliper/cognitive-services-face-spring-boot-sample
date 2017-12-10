package com.adamtuliper.general.cognitiveservices.sample.contract;

import java.io.Serializable;
/**
 * A group of people on the MCS Face service
 *
 */
public class PersonGroup implements Serializable{
	public String personGroupId;
    public String name;
    public String userData;
        public String getPersonGroupId() {
		return personGroupId;
	}
	public void setPersonGroupId(String personGroupId) {
		this.personGroupId = personGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}


}
