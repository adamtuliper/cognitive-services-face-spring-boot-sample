package com.adamtuliper.general.cognitiveservices.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.adamtuliper.general.cognitiveservices.sample.contract.Person;
import com.adamtuliper.general.cognitiveservices.sample.contract.PersonGroup;
import com.adamtuliper.general.cognitiveservices.sample.contract.TrainingStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CognitiveServicesFaceApiSpringBootSampleTests {

	private static final String TEST_PERSON_GROUP_NAME = "Test123";
	private static final String personGroupsUri = "/personGroups";
	private static final String personGroupUri = "/personGroups/{personGroupId}";
	private static final String TEST_PERSON_NAME = "Test Person";
	private static final String TEST_PERSON_USER_DATA = "Test Person User Data";
	private static final String personUri = "/persons/{personId}";
	private static final String personsUri = "/persons";

	@Autowired
	TestRestTemplate template;

	@Autowired
	TestRestTemplate deleteTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testThatPersonGroupsAreAccessable() {
		ResponseEntity<List<PersonGroup>> response = template.exchange(personGroupsUri,HttpMethod.GET,null, new ParameterizedTypeReference<List<PersonGroup>>() {
        });
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void testThatTestPersonGroupsCanBeDeleted() {
		ResponseEntity<List<PersonGroup>> response = template.exchange(personGroupsUri,HttpMethod.GET,null, new ParameterizedTypeReference<List<PersonGroup>>() {
        });
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		List<PersonGroup> personGroups = response.getBody();
		assertThat(personGroups.size()).isGreaterThanOrEqualTo(0);
		
		for (PersonGroup personGroup : personGroups) {
			if (personGroup.getName().equals(TEST_PERSON_GROUP_NAME)) {
				deleteTemplate.delete(personGroupUri, personGroup.getPersonGroupId());
			}
		}
	}
	
	@Test
	public void testThatPersonGroupsCanBeCreated() {
		PersonGroup personGroup = createPersonPersonGroup();
		PersonGroup response = template.postForObject(personGroupsUri, personGroup, PersonGroup.class);
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo(personGroup.getName());

		deleteTemplate.delete(personGroupUri, response.getPersonGroupId());
	}

	@Test
	public void testThatAPersonCanBeCreated() {
		PersonGroup personGroup = createPersonPersonGroup();
		personGroup = template.postForObject(personGroupsUri, personGroup, PersonGroup.class);

		Person person = createPerson(personGroup);
		Person response = template.postForObject(personsUri, person, Person.class);
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo(person.getName());

		deleteTemplate.delete(personUri, response.getPersonId());
	}
	private Person createPerson(PersonGroup personGroup) {
		Person person = new Person();
		person.setName(TEST_PERSON_NAME);
		person.setUserData(TEST_PERSON_USER_DATA);
		person.setPersonGroupId(personGroup.getPersonGroupId());
		return person;
	}

	@Test
	public void testThatAPersonGroupsCanBeTrained() {
		PersonGroup personGroup = createPersonPersonGroup();
		PersonGroup newPersonGroup = template.postForObject(personGroupsUri, personGroup, PersonGroup.class);
		assertThat(newPersonGroup).isNotNull();
		assertThat(newPersonGroup.getName()).isEqualTo(personGroup.getName());
		newPersonGroup.setTrainingStatus(TrainingStatus.needsTraining);
		ResponseEntity<PersonGroup> response = template.exchange(personGroupUri, HttpMethod.PUT, new HttpEntity<PersonGroup>(newPersonGroup), PersonGroup.class, newPersonGroup.getPersonGroupId());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		PersonGroup updatedGroup = response.getBody();
		assertThat(updatedGroup.getTrainingStatus()).isEqualTo(TrainingStatus.ongoing);
		deleteTemplate.delete(personGroupUri, newPersonGroup.getPersonGroupId());
	}

	private PersonGroup createPersonPersonGroup() {
		PersonGroup personGroup = new PersonGroup();
		personGroup.setName(TEST_PERSON_GROUP_NAME);
		personGroup.setUserData("User Data");
		return personGroup;
	}
}
