package com.adamtuliper.general.cognitiveservices.sample.contract;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A group of people on the MCS Face service
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonGroup implements MCSFaceObject {
	public String personGroupId;
	public String name;
	public String userData;
	public TrainingStatus trainingStatus;
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

	public TrainingStatus getTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingStatus(TrainingStatus status) {
		this.trainingStatus = status;
	}

}
