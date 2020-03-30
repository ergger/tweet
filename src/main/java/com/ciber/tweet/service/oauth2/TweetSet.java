package com.ciber.tweet.service.oauth2;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TweetSet extends APIManagerClient<String, String>{

	// private String request = "https://api.twitter.com/1.1/search/tweets.json?q=(from%3Asergio_fajardo)";
	private String request = "https://api.twitter.com/1.1/search/tweets.json?q=(from:{value})";

	@Override
	protected String performCall(OAuth2RestTemplate oAuth2RestTemplate, String input) {
		ObjectMapper mapper = null;
		
		try {
			// HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), multipartHeader());
			String url = buildURL(input).toUriString();
			log.info(url);
			HttpEntity<?> entity = new HttpEntity<>(multipartHeader());
			// ResponseEntity<TweetResponse> response = oAuth2RestTemplate.exchange(url, HttpMethod.POST, requestEntity, TweetResponse.class);
			ResponseEntity<String> response = oAuth2RestTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			return response.getBody();
		}catch (HttpClientErrorException e) {
			log.error("http client {}",e.getCause());
			try {
				if (mapper != null) {
					return mapper.readValue(e.getResponseBodyAsString(), String.class);
				}
			} catch (IOException ex) {
				log.error("in out {}", ex.getCause());
			}
		// }catch(JsonProcessingException e) {
		//	log.error("json processing {}", e.getCause());
		}
		return null;
	}

	private HttpHeaders multipartHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}
	
	private UriComponents buildURL(String input) {
		log.info(this.request);
		return UriComponentsBuilder.fromHttpUrl(this.request).buildAndExpand(input);
	}
}
