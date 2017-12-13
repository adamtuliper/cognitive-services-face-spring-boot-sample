
package com.adamtuliper.general.cognitiveservices.sample.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adamtuliper.general.cognitiveservices.sample.ControllerSupport;
import com.adamtuliper.general.cognitiveservices.sample.contract.Person;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The REST controller for the MCS Face API proxy
 * @author csmith
 *
 */
	
@RestController()
public class PersonController {

	@Autowired
	private ControllerSupport support; 


    /**
     * Get a person by ID
     * @param personId the id to use
     * @return a person or null
     */
    @RequestMapping("/persons")
    public Person getPerson(@RequestParam(value="personId") String personId) {

        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Person> response = restTemplate.exchange(
        	support.personUri,
       		HttpMethod.GET,
        	support.augmentHeaders(), 
        	Person.class,
       		personId);
		Person person = response.getBody();
 
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        return person;
    }
    

    /**
     * Create a person
     * @return a person
     */
    @RequestMapping(value="/persons", method=RequestMethod.POST)
    public Person createPerson(@RequestBody Person person) {

        RestTemplate restTemplate = new RestTemplate();
        
        String personGroupId = person.getPersonGroupId();
        person.setPersonGroupId(null);
		ResponseEntity<Person> response = restTemplate.exchange(
        	support.personsUri,
       		HttpMethod.POST,
        	support.augmentHeaders(person), 
        	Person.class,
        	personGroupId);
		Person newPerson = response.getBody();
		person.setPersonGroupId(personGroupId);
		person.setPersonId(newPerson.getPersonId());
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        return person;
    }
}


