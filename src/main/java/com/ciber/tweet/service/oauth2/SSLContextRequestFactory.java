package com.ciber.tweet.service.oauth2;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class SSLContextRequestFactory extends SimpleClientHttpRequestFactory {
	private SSLContext sslContext;
	private static final String TLS_VERSION = "TLS";
	public SSLContextRequestFactory() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContextRF = SSLContext.getInstance(TLS_VERSION);
		sslContextRF.init(null, new TrustManager[] {
				new X509TrustManager() {
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
					
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}
					
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					}
				}
		}, null);
		this.sslContext = sslContextRF;
		HttpsURLConnection.setDefaultHostnameVerifier((hostname,  session) -> false);
	}

	protected void prepareConnection(final HttpsURLConnection connection, final String httpMethod) throws IOException {
		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection) connection).setSSLSocketFactory(this.sslContext.getSocketFactory());
		}
		super.prepareConnection(connection, httpMethod);
	}
}
