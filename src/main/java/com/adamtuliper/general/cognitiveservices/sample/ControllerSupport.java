package com.adamtuliper.general.cognitiveservices.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.adamtuliper.general.cognitiveservices.sample.contract.MCSFaceObject;
@EnableAutoConfiguration

public class ControllerSupport {
	
	
	// The key for your Microsoft Cognitive Services Face api.
	@Value("${microsoft.cognitiveservices.face.ocp-apim-subscription-key}")
	public String subscriptionKey;

	// See application.yaml for settings
	@Value("${microsoft.cognitiveservices.face.uri.personGroups}")
	public String personGroupsUri;

	@Value("${microsoft.cognitiveservices.face.uri.persons}")
	public String personsUri;

	@Value("${microsoft.cognitiveservices.face.uri.personGroup}")
	public String personGroupUri;
	
    //See application.yaml for settings
    @Value("${microsoft.cognitiveservices.face.uri.person}")
	public String personUri;

    @Value("${microsoft.cognitiveservices.face.uri.personGroupTraining}")
	public String personGroupTrainingUri;
	/*
	 * Package the httpEntity with headers and the object
	 * @return a packaged httpEntity
	 */
	public HttpEntity<MCSFaceObject> augmentHeaders() {
		return augmentHeaders(null);
	}
	/*
	 * Package the httpEntity with headers and the object
	 * @param faceObject - the object to use null if not needed
	 * @return a packaged httpEntity
	 */
	public HttpEntity<MCSFaceObject> augmentHeaders(MCSFaceObject faceObject) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);
		HttpEntity<MCSFaceObject> httpEntity = null;
		if (faceObject != null)  {
			httpEntity = new HttpEntity<MCSFaceObject>(faceObject,headers);
		} else {
			httpEntity = new HttpEntity<MCSFaceObject>(headers);
		}
		return httpEntity;
	}
}
