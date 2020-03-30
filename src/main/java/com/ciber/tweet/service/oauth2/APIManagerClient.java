package com.ciber.tweet.service.oauth2;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

public abstract class APIManagerClient<I,O> {
	
	private final String TOKEN_URL = "https://api.twitter.com/oauth2/token";
	private final String CLIENT_ID = "rpAkfncPngSVqC4XntxUUsJvJ";
	private final String CLIENT_SECRET = "U6S3BHcLNMMYv22Vg3AmsUKu15n756ugYpgKpqXmcHv3Gj86K4";
	private final String GRAND_TYPE = "client_credentials";

	private OAuth2RestTemplate oAuth2RestTemplate;
	
	public O execute(final I input) throws KeyManagementException, NoSuchAlgorithmException {
		final ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(TOKEN_URL);
		resource.setClientId(CLIENT_ID);
		resource.setClientSecret(CLIENT_SECRET);
		resource.setGrantType(GRAND_TYPE);
		
		this.oAuth2RestTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext());
		this.oAuth2RestTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) this.oAuth2RestTemplate.getAccessToken();
		token.setTokenType("Bearer");

		this.setNoSSL();
		this.setInterceptor();
		
		return this.performCall(this.oAuth2RestTemplate, input);
		
	}

	protected abstract O performCall(OAuth2RestTemplate oAuth2RestTemplate, I input);

	private void setInterceptor() {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new OAuth2RequestInterceptor());
		interceptors.add(new RequestResponseLoggingInterceptor());
		this.oAuth2RestTemplate.setInterceptors(interceptors);
	}

	private void setNoSSL() throws KeyManagementException, NoSuchAlgorithmException {
		final ClientHttpRequestFactory requestFactory = new SSLContextRequestFactory();
		this.oAuth2RestTemplate.setRequestFactory(requestFactory);
		
		final ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		provider.setRequestFactory(requestFactory);
		this.oAuth2RestTemplate.setAccessTokenProvider(provider);
	}
}

