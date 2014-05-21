package org.mulesoft.apimanager.client;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/**
 * APIManagerMockErrorAuthenticationHandler
 * 
 * Mock API Manager Authentication Handler
 * 
 * @author jeff.schenk@mulesoft.com
 *
 */
public class APIManagerMockErrorAuthenticationHandler implements org.apache.http.protocol.HttpRequestHandler, APIManagerClientConstants {

	
	/**
	 * Handle Mocking the Response to incoming authentication Request.
	 */
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext)
			throws HttpException, IOException {
		
		String locationResponseHeader = CONSOLE_TAG_RESPONSE + ERROR + "=" + "access_denied";
		httpResponse.addHeader("Location",locationResponseHeader);
		httpResponse.setStatusCode(302);
	}


}
