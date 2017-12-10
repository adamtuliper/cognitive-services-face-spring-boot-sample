# cognitive-services-face-spring-boot-sample
Java Spring Boot example for calling Cognitive Services Face Api

# Motivation
This service provides a Spring Boot and Swagger2 wrapper for the MCS Face API. This allows it to reside within the Spring eco-system, leveraging  Spring security and Swagger UI documentation. In a high security environment this is preferable to direct API calls from the browser and avoids CORS issues.

# Installation
## Prerequisites
- Java 1.8 JDK
- Maven 2

## Helpful
- Spring Tools Suite (STS) or Eclipse with the plugins

## Steps
- Clone this repository
- build/test

cd cognitive-services-face-spring-boot-sample

mvn clean install -Dmicrosoft.cognitiveservices.face.ocp-apim-subscription-key=[your access key (not subscription key)] 
 -Dmicrosoft.cognitiveservices.face.uri.base=https://westus.api.cognitive.microsoft.com/face/v1.0
 
