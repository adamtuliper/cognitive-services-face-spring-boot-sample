package com.adamtuliper.general.cognitiveservices.sample.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adamtuliper.general.cognitiveservices.sample.ControllerSupport;
import com.adamtuliper.general.cognitiveservices.sample.contract.Person;
import com.adamtuliper.general.cognitiveservices.sample.contract.PersonGroup;
import com.adamtuliper.general.cognitiveservices.sample.contract.TrainingStatus;

@RestController()
public class PersonGroupController {

	private static final Logger LOGGER=LoggerFactory.getLogger(PersonGroupController.class);
	@Autowired
	private ControllerSupport support; 

	/**
	 * List all personGroups 
	 * @return
	 */
	@RequestMapping(path = "/personGroups", method = RequestMethod.GET)
	public List<PersonGroup> getPersonGroups() {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<PersonGroup>> response = restTemplate.exchange(support.personGroupsUri, HttpMethod.GET,
				support.augmentHeaders(), new ParameterizedTypeReference<List<PersonGroup>>() {
				});

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		List<PersonGroup> personGroups = response.getBody();
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

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Person>> response = restTemplate.exchange(support.personsUri, HttpMethod.GET,
				support.augmentHeaders(), new ParameterizedTypeReference<List<Person>>() {
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

	@RequestMapping(path = "/personGroups", method = RequestMethod.POST )
	@ResponseBody
	public PersonGroup createPersonGroup(@RequestBody PersonGroup personGroup) {

		String personGroupId = UUID.randomUUID().toString();
		personGroup.setPersonGroupId(personGroupId);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<PersonGroup> response = 
			restTemplate.exchange(
				support.personGroupUri, 
				HttpMethod.PUT,
				support.augmentHeaders(personGroup), 
				PersonGroup.class, 
				personGroupId);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		// Put does not return a new object
		return personGroup;

	}
	
	/**
	 * Update a new person group
	 * @param personGroup
	 * @return the updated person group
	 */

	@RequestMapping(path = "/personGroups/{personGroupId}", method = RequestMethod.PUT )
	@ResponseBody
	public PersonGroup trainPersonGroup(@PathVariable String personGroupId, @RequestBody PersonGroup personGroup) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		if (personGroup.getTrainingStatus().equals(TrainingStatus.needsTraining)) {
			// This is a training request
			response = 
				restTemplate.exchange(
					support.personGroupTrainingUri, 
					HttpMethod.POST,
					support.augmentHeaders(), 
					String.class, 
					personGroup.getPersonGroupId());
				personGroup.setTrainingStatus(TrainingStatus.ongoing);
		} else {
			// This is an update request
			response = 
				restTemplate.exchange(
					support.personGroupTrainingUri, 
					HttpMethod.PATCH,
					support.augmentHeaders(personGroup), 
					String.class, 
					personGroup.getPersonGroupId());
		}
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		// Train does not return a new object
		return personGroup;

	}

	/**
	 * Delete a person group
	 * @param personGroupId
	 */
	@RequestMapping(path = "/personGroups/{personGroupId}", method = RequestMethod.DELETE )
	@ResponseBody
	public void deletePersonGroup(@PathVariable String personGroupId) {

		RestTemplate restTemplate = new RestTemplate();
		try {
		restTemplate.exchange(
				support.personGroupUri, 
				HttpMethod.DELETE,
				support.augmentHeaders(),
				String.class, 
				personGroupId);
		} catch (Exception e) {
			LOGGER.error("Failed to delete PersonGroup {}", personGroupId, e);
		}
		
	}

}
