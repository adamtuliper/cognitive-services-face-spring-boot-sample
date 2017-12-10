
package com.adamtuliper.general.cognitiveservices.sample.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adamtuliper.general.cognitiveservices.sample.contract.Person;


@RestController()
public class PersonController {

    //The key for your Microsoft Cognitive Services Face api.
    @Value("${microsoft.cognitiveservices.face.ocp-apim-subscription-key}")
    private String subscriptionKey;

    //See application.yaml for settings
    @Value("${microsoft.cognitiveservices.face.uri.person}")
    private String personUri;


    @RequestMapping("/people")
    public Person getPerson(@RequestParam(value="personId") String personId) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);

        RestTemplate restTemplate = new RestTemplate();
        
        HttpEntity<Person> httpEntity = new HttpEntity<Person>(headers);

        ResponseEntity<Person> response = restTemplate.exchange(
        		personUri,
       		 HttpMethod.GET,
        		 httpEntity, 
        		 Person.class,
       		 personId);
		Person person = response.getBody();
 
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        return person;

    }
}


