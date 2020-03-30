package com.ciber.tweet.service.oauth2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2RequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, 
			ClientHttpRequestExecution execution)
			throws IOException {
		log(request);
		Optional<Map.Entry<String, List<String>>> optional = request.getHeaders().entrySet().stream()
				.filter(entry -> "Authorization".equals(entry.getKey())).findFirst();
		if (optional.isPresent()) {
			String auth = optional.get().getValue().get(0);
			request.getHeaders().set("Authorization", auth.replace("bearer", "Bearer"));
			// request.getHeaders().set("Authorization", auth);
		}
		return execution.execute(request, body);
	}
	private void log(final HttpRequest request) {
		request.getHeaders().entrySet()
		.forEach(header -> log.debug(header.getKey()+":"+header.getValue()));
	}
}
