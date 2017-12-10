package com.adamtuliper.general.cognitiveservices.sample.controllers;

import java.util.List;

import com.adamtuliper.general.cognitiveservices.sample.contract.Person;
import com.adamtuliper.general.cognitiveservices.sample.contract.PersonGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@RestController()
public class PersonGroupController {

	// The key for your Microsoft Cognitive Services Face api.
	@Value("${microsoft.cognitiveservices.face.ocp-apim-subscription-key}")
	private String subscriptionKey;

	// See application.yaml for settings
	@Value("${microsoft.cognitiveservices.face.uri.personGroups}")
	private String personGroupsUri;

	@Value("${microsoft.cognitiveservices.face.uri.people}")
	private String peopleUri;

	@Value("${microsoft.cognitiveservices.face.uri.personGroup}")
	private String personGroupUri;

	@RequestMapping(path = "/personGroups", method = RequestMethod.GET)
	public List<PersonGroup> getPersonGroups() {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<PersonGroup>> response = restTemplate.exchange(personGroupsUri, HttpMethod.GET,
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<List<PersonGroup>>() {
				});

		List<PersonGroup> personGroups = response.getBody();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		return personGroups;

	}

	/**
	 * Get a list of people in the group
	 * 
	 * @param groupId
	 *            the groupId
	 * @return the
	 */
	@RequestMapping(path = "/personGroups/{groupId}/people", method = RequestMethod.GET)
	public List<Person> getPeople(@PathVariable("groupId") String groupId,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Person>> response = restTemplate.exchange(peopleUri, HttpMethod.GET,
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<List<Person>>() {
				});

		List<Person> people = response.getBody();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		return people;
	}

	/**
	 * Create a new person group
	 * @param personGroup
	 * @return the created person group
	 */
	@RequestMapping(path = "/personGroups/{personGroupId}", method = RequestMethod.POST )
	@ResponseBody
	public PersonGroup createPersonGroup(@PathVariable String personGroupId, @RequestBody PersonGroup personGroup) {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<PersonGroup> response = restTemplate.exchange(personGroupUri, HttpMethod.PUT,
				new HttpEntity<PersonGroup>(personGroup, headers), PersonGroup.class, personGroupId);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		// Put does not return a new object
		return personGroup;

	}

}
