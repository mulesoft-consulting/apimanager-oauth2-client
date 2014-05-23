package org.mulesoft.apimanager.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.mulesoft.apimanager.client.results.APIManagerAuthenticationResult;
import org.mulesoft.apimanager.client.results.APIManagerResourceResult;

/**
 * APIManagerClientTools
 * 
 * Provide API Manager Client Tools to Authenticate and get a Resource.
 * 
 * @see http://tools.ietf.org/html/draft-ietf-oauth-v2-31 for details on OAUTH
 *      V2 Dance.
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public class APIManagerClientTools implements APIManagerClientConstants {

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(APIManagerClientTools.class);
	
	/**
	 * getResource Access a Protected API Manager Resource using an Access Token
	 * obtain in a prior authentication.
	 * 
	 * @param httpClient
	 * @param accessToken
	 * @param resource
	 * @param parameters
	 * @param responseContent
	 * @return APIManagerResourceResult
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected static APIManagerResourceResult getResource(
			final HttpClient httpClient, final String accessToken,
			final String resource, final String parameters,
			final String responseContent) throws ClientProtocolException,
			IOException {
		// Access A protected Resource....
		// Set Parameters based upon Resource Request.
		String fullyQualifiedResource = API_ENDPOINT + resource + "?"
				+ ACCESS_TOKEN + "=" + accessToken;
		if ((parameters != null) && (!parameters.isEmpty())) {
			if (!parameters.startsWith("&")) {
				fullyQualifiedResource = fullyQualifiedResource.concat("&");
			}
			fullyQualifiedResource = fullyQualifiedResource.concat(parameters);
		}
		// Instantiate the HTTP Get Request
		HttpGet getRequest = new HttpGet(fullyQualifiedResource);
		// Set our Content to Accept, either XML or the default of JSON.
		if ((responseContent != null)
				&& (responseContent.equalsIgnoreCase("xml"))) {
			getRequest.addHeader("accept", API_MANAGER_CONTENT_XML);
		} else {
			getRequest.addHeader("accept", API_MANAGER_CONTENT_JSON);
		}
		// Execute the Get Request
		HttpResponse response = httpClient.execute(getRequest);
		// Build up the Response Object.
		APIManagerResourceResult apiManagerResourceResult = new APIManagerResourceResult(
				resource, response.getStatusLine().getStatusCode());
		// Pull the Data in as Native String Data.
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		StringBuffer sb = new StringBuffer();
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		// Set the Response Data.
		apiManagerResourceResult.setData(sb.toString());
		// return the Result Object.
		return apiManagerResourceResult;
	}

	/**
	 * authenticate Authenticate the Basic API Manager User Credentials and
	 * obtain an Authentication Result Object.
	 * 
	 * @param httpClient
	 * @param username
	 * @param password
	 * @return APIManagerAuthenticationResult
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected static APIManagerAuthenticationResult authenticate(
			final HttpClient httpClient, final String username,
			final String password) throws ClientProtocolException, IOException {
		return authenticate(httpClient, API_ENDPOINT + AUTHORIZE_RESOURCE, username, password);
	}
	
	/**
	 * authenticate Authenticate the Basic API Manager User Credentials and
	 * obtain an Authentication Result Object.
	 * 
	 * @param httpClient
	 * @param resource
	 * @param username
	 * @param password
	 * @return APIManagerAuthenticationResult
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected static APIManagerAuthenticationResult authenticate(
			final HttpClient httpClient, final String resource, final String username,
			final String password) throws ClientProtocolException, IOException {
		// Validate parameters
		if ( (httpClient == null) || (resource == null) || (username == null) || (password == null) ||
		     (resource.isEmpty()) || (username.isEmpty()) || (password.isEmpty()) ) {
			return null;
		}
		// Initialize the HTTP Port End Point.
		HttpPost httpPost = new HttpPost(resource);

		// Build Name Value Pair Parameters for POST Request.
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("grant_type", "password"));
		nvps.add(new BasicNameValuePair("client_id", "CONSOLE"));
		nvps.add(new BasicNameValuePair("response_type", "token"));
		nvps.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
		
		// Set Scopes Accordingly.
		nvps.add(new BasicNameValuePair("scope", API_MANAGER_SCOPES));

		// Add the Basic Authentication for API Manager Principal and
		// Credentials.
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));

		// Associate the Parameters to the Post Request.
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		// Issue Post to Authenticate and Obtain our Access Token.
		HttpResponse response = httpClient.execute(httpPost);

		// TODO Throw a RunTime Exception
		if (response.getStatusLine().getStatusCode() != 302) {
			log.error("Error Expecting a 302 redirect, but found: "
					+ response.getStatusLine()
					+ " Content Length:"
					+ ((response.getEntity() != null) ? response.getEntity()
							.getContentLength() : "null"));
			for (Header header : response.getAllHeaders()) {
				log.info("  Header: " + header.getName() + ": "
						+ header.getValue());
			}
			return null;
		}
		// Obtain the Location Header, which has been set per our 302 response
		// Redirect.
		// We still need to verify that we have been authenticated,
		// by the presence of the access_token or not.
		Header locationHeader = response.getLastHeader("Location");
		if (locationHeader == null) {
			return null;
		}
		return new APIManagerAuthenticationResult(locationHeader.getValue());
	}

}
