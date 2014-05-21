package org.mulesoft.apimanager.client.results;

/**
 * APIManagerResource
 * 
 * Provides wrapper for the results of accessing a protected resource.
 * 
 * @see http://tools.ietf.org/html/draft-ietf-oauth-v2-31 for details on OAUTH V2 Dance.
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public class APIManagerResourceResult {

	private String resource;

	private int statusCode;

	private String data;

	/**
	 * Default Constructor
	 */
	public APIManagerResourceResult() {
	}

	/**
	 * Constructor with all required fields.
	 * 
	 * @param resource
	 * @param statusCode
	 */
	public APIManagerResourceResult(String resource, int statusCode) {
		this.resource = resource;
		this.statusCode = statusCode;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
