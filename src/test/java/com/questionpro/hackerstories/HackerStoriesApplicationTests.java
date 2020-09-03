package com.questionpro.hackerstories;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.questionpro.hackerstories.constants.Constants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class HackerStoriesApplicationTests {

  @Autowired
  private WireMockServer wireMockServer;
  
  @Autowired
  private WebTestClient webTestClient;
  
  @AfterEach
  public void afterEach() {
    this.wireMockServer.resetAll();
  }
  
  @Test
  public void testBestStories() throws IOException {
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.BEST_STORIES_URL)
          .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody("[1]"))
      );
    
    String item = "{\n" + 
        "    \"by\": \"danfritz\",\n" + 
        "    \"descendants\": 62,\n" + 
        "    \"id\": 24328640,\n" + 
        "    \"kids\": [24331909],\n" + 
        "    \"score\": 269,\n" + 
        "    \"time\": 1598850068,\n" + 
        "    \"title\": \"Analysis of Today's CenturyLink/Level(3) Outage\",\n" + 
        "    \"type\": \"story\",\n" + 
        "    \"url\": \"https://blog.cloudflare.com/analysis-of-todays-centurylink-level-3-outage/\"\n" + 
        "}";
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.ITEM_URL + "1.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(item)));
    webTestClient.get().uri(Constants.BEST_HYPHEN_STORIES).accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isOk();
  }
  
  @Test
  void testPastStories() {
    webTestClient.get().uri(Constants.PAST_HYPHEN_STORIES).accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isOk();
  }
  
  @Test
  void testGetTopCommentsOfGivenStory() {
    String item = "{\n" + 
        "    \"by\": \"danfritz\",\n" + 
        "    \"descendants\": 62,\n" + 
        "    \"id\": 24328640,\n" + 
        "    \"kids\": [24331909],\n" + 
        "    \"score\": 269,\n" + 
        "    \"time\": 1598850068,\n" + 
        "    \"title\": \"Analysis of Today's CenturyLink/Level(3) Outage\",\n" + 
        "    \"type\": \"story\",\n" + 
        "    \"url\": \"https://blog.cloudflare.com/analysis-of-todays-centurylink-level-3-outage/\"\n" + 
        "}";
    
    String comment = "{\n" + 
        "    \"by\": \"achiang\",\n" + 
        "    \"id\": 24331909,\n" + 
        "    \"kids\": [],\n" + 
        "    \"parent\": 24328640,\n" + 
        "    \"text\": \"There is probably more than what is being said here otherwise clients for reddit or 4chan would have been removed a long time ago.\",\n" + 
        "    \"time\": 1598884877,\n" + 
        "    \"type\": \"comment\"\n" + 
        "}";
    
    String user = "{\n" + 
        "    \"about\": \"https:&#x2F;&#x2F;twitter.com&#x2F;chizangiam\",\n" + 
        "    \"created\": 1319951931,\n" + 
        "    \"id\": \"achiang\",\n" + 
        "    \"karma\": 291,\n" + 
        "    \"submitted\": [24334898]\n" + 
        "}";
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.ITEM_URL + "1.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(item)));
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.ITEM_URL + "24331909.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(comment)));
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.USER_URL + "achiang.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(user)));
    
    webTestClient.get().uri(Constants.COMMENTS + "?story=1").accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isOk();
  }
  
  @Test
  void testGetTopCommentsOfGivenStoryWithNoUser() {
    String item = "{\n" + 
        "    \"by\": \"danfritz\",\n" + 
        "    \"descendants\": 62,\n" + 
        "    \"id\": 24328640,\n" + 
        "    \"kids\": [24331909],\n" + 
        "    \"score\": 269,\n" + 
        "    \"time\": 1598850068,\n" + 
        "    \"title\": \"Analysis of Today's CenturyLink/Level(3) Outage\",\n" + 
        "    \"type\": \"story\",\n" + 
        "    \"url\": \"https://blog.cloudflare.com/analysis-of-todays-centurylink-level-3-outage/\"\n" + 
        "}";
    
    String comment = "{\n"+ 
        "    \"id\": 24331909,\n" + 
        "    \"kids\": [],\n" + 
        "    \"parent\": 24328640,\n" + 
        "    \"text\": \"There is probably more than what is being said here otherwise clients for reddit or 4chan would have been removed a long time ago.\",\n" + 
        "    \"time\": 1598884877,\n" + 
        "    \"type\": \"comment\"\n" + 
        "}";
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.ITEM_URL + "1.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(item)));
    
    this.wireMockServer.stubFor(
        WireMock.get(Constants.SLASH + Constants.ITEM_URL + "24331909.json")
        .willReturn(ResponseDefinitionBuilder.responseDefinition()
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(comment)));
    
    webTestClient.get().uri(Constants.COMMENTS + "?story=1").accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isOk();
  }
  
  @Test
  void testGetTopCommentsOfGivenStoryWithoutStoryId() {
    webTestClient.get().uri(Constants.COMMENTS).accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isBadRequest();
  }
  
  @Test
  void testGetTopCommentsOfGivenStoryWrongUrl() {
    webTestClient.get().uri(Constants.COMMENTS + "?story=").accept(MediaType.APPLICATION_JSON)
    .exchange()
    .expectStatus().isBadRequest();
  }

}
