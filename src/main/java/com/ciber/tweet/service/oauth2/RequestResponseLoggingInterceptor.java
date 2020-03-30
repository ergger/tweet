package com.ciber.tweet.service.oauth2;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.nimbusds.jose.util.StandardCharset;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution)
			throws IOException {
		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	private void logRequest(final HttpRequest request, final byte[] body) {
		log.info("=====================================");
		log.info("URI     : {}", request.getURI());
		log.info("Method  : {}", request.getMethod());
		log.info("Headers : {}", request.getHeaders());
		log.info("Body    : {}", new String(body, StandardCharset.UTF_8));
		log.info("=====================================");
	}

	private void logResponse(final ClientHttpResponse response) throws IOException {
		log.info("=====================================");
		log.info("Status Code: {}", response.getStatusCode());
		log.info("Status text: {}", response.getStatusText());
		log.info("Header     : {}", response.getHeaders());
		log.info("Body       : {}", response.getBody());
		log.info("=====================================");
	}
}
