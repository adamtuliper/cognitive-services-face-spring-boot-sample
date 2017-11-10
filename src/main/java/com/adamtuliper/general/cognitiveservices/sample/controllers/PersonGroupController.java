package com.adamtuliper.general.cognitiveservices.sample.controllers;

import java.util.List;

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

    //The key for your Microsoft Cognitive Services Face api.
    @Value("${microsoft.cognitiveservices.face.ocp-apim-subscription-key}")
    private String subscriptionKey;

    //See application.yaml for settings
    @Value("${microsoft.cognitiveservices.face.uriPersonGroups}")
    private String uri;


    @RequestMapping("/personGroups")
    public List<PersonGroup> getPersonGroups(@RequestParam(value="name", defaultValue="World") String name) {


        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Ocp-Apim-Subscription-Key", subscriptionKey);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<PersonGroup>> response = restTemplate.exchange(uri,
                        HttpMethod.GET, new HttpEntity<Object>(headers), new ParameterizedTypeReference<List<PersonGroup>>() {
                        });

        List<PersonGroup> personGroups = response.getBody();

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        return personGroups;

    }
}


