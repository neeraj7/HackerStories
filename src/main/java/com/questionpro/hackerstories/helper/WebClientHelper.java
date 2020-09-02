package com.questionpro.hackerstories.helper;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import com.questionpro.hackerstories.constants.Constants;
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
   * WebClient instance.
   */
  private WebClient client = WebClient.create(Constants.BASE_URL);

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
