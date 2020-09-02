package com.questionpro.hackerstories.helper;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * A helper class to make api calls.
 * 
 * @author neeraj.kumar
 *
 */
@Component
public class WebClientHelper {
  
  /**
   * Hackernews base api url.
   */
  @Value("${config.api.base.url:}")
  private String baseUrl;

  /**
   * WebClient instance.
   */
  private WebClient client;
  
  @PostConstruct
  public void init() {
    client = WebClient.create(baseUrl);
  }

  /**
   * Make a get call to the given uri.
   * 
   * @param uri
   * @return Mono<ClientResponse>
   */
  public Mono<ClientResponse> getCall(String uri) {
    return client.get().uri(uri).accept(MediaType.APPLICATION_JSON).exchange();
  }

}
