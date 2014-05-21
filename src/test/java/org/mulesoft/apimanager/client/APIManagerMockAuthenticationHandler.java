package org.mulesoft.apimanager.client;

import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.mulesoft.apimanager.client.results.APIManagerAuthenticationResult;

/**
 * APIManagerMockAuthenticationHandler
 * 
 * Mock API Manager Authentication Handler
 * 
 * @author jeff.schenk@mulesoft.com
 *
 */
public class APIManagerMockAuthenticationHandler implements org.apache.http.protocol.HttpRequestHandler, APIManagerClientConstants {

	
	/**
	 * Handle Mocking the Response to incoming authentication Request.
	 */
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext)
			throws HttpException, IOException {
		
		UUID id = UUID.fromString(APIManagerClientTest.TEST_TOKEN.toString());
		String locationResponseHeader = CONSOLE_TAG_RESPONSE + ACCESS_TOKEN + "=" + id + "&"
		+ APIManagerAuthenticationResult.EXPIRES_IN + "="
		+ "3600" + "&"
		+ APIManagerAuthenticationResult.TOKEN_TYPE + "="
		+ "bearer";
		
		httpResponse.addHeader("Location",locationResponseHeader);
		httpResponse.setStatusCode(302);
	}


}
