package com.adamtuliper.general.cognitiveservices.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.adamtuliper.general.cognitiveservices.sample.contract.PersonGroup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CognitiveServicesFaceApiSpringBootSampleTests {

	private static final String personGroupsUri = "/personGroups";
	private static final String personGroupUri = "/personGroups/{prersonGroupId}";
	
	
	
	@Autowired
	TestRestTemplate template;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testPersonGroupsAccessable() {
		ResponseEntity<List<PersonGroup>> response = template.exchange(personGroupsUri,HttpMethod.GET,null, new ParameterizedTypeReference<List<PersonGroup>>() {
        });
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isGreaterThanOrEqualTo(0);
	}
	
	@Test
	public void testThatPersonGroupsCanBeCreated() {
		PersonGroup personGroup = new PersonGroup();
		personGroup.setName("Test");
		personGroup.setUserData("User Data");
		personGroup.setPersonGroupId(UUID.randomUUID().toString());
		PersonGroup response = template.postForObject(personGroupUri, personGroup, PersonGroup.class, personGroup.getPersonGroupId());
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo(personGroup.getName());
	}
}
