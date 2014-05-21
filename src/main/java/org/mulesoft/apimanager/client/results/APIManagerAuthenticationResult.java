package org.mulesoft.apimanager.client.results;

import java.util.HashMap;
import java.util.Map;

import org.mulesoft.apimanager.client.APIManagerClientConstants;

/**
 * APIManagerAuthenticationResult
 * 
 * Provides a Result Wrapper for the API Manager Authentication Response.
 * 
 * @see http://tools.ietf.org/html/draft-ietf-oauth-v2-31
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public class APIManagerAuthenticationResult implements
		APIManagerClientConstants {
	// Line Breaks used in toString Method.
	static final String LINE_BREAK = System.getProperty("line.separator");

	/**
	 * scope
	 * 
	 * <pre>
	 * OPTIONAL, if identical to the scope requested by the client,
	 *          otherwise REQUIRED.  The scope of the access token as described
	 *          by Section 3.3 of OAUTH 2.0 draft-ietf-oauth-v2-31.
	 * </pre>
	 */
	public static final String SCOPE = "scope";

	/**
	 * token_type
	 * 
	 * <pre>
	 * REQUIRED.  The type of the token issued as described in
	 *          Section 7.1 of OAUTH 2.0 draft-ietf-oauth-v2-31. Value is case insensitive.
	 * </pre>
	 */
	public static final String TOKEN_TYPE = "token_type";

	/**
	 * access_token REQUIRED. The access token issued by the authorization
	 * server.
	 */
	public static final String ACCESS_TOKEN = "access_token";

	/**
	 * expires_in
	 * 
	 * <pre>
	 * RECOMMENDED.  The lifetime in seconds of the access token.  For
	 *          example, the value "3600" denotes that the access token will
	 *          expire in one hour from the time the response was generated.
	 *          If omitted, the authorization server SHOULD provide the
	 *          expiration time via other means or document the default value.
	 * </pre>
	 */
	public static final String EXPIRES_IN = "expires_in";

	/**
	 * error
	 * 
	 * <pre>
	 * REQUIRED.  A single ASCII [USASCII] error code from the
	 *          following:
	 *          invalid_request
	 *                The request is missing a required parameter, includes an
	 *                invalid parameter value, includes a parameter more than
	 *                once, or is otherwise malformed.
	 *          unauthorized_client
	 *                The client is not authorized to request an access token
	 *                using this method.
	 *          access_denied
	 *                The resource owner or authorization server denied the
	 *                request.
	 *          unsupported_response_type
	 *                The authorization server does not support obtaining an
	 *                access token using this method.
	 *          invalid_scope
	 *                The requested scope is invalid, unknown, or malformed.
	 *          server_error
	 *                The authorization server encountered an unexpected
	 *                condition that prevented it from fulfilling the request.
	 *                (This error code is needed because a 500 Internal Server
	 *                Error HTTP status code cannot be returned to the client
	 *                via a HTTP redirect.)
	 *          temporarily_unavailable
	 *                The authorization server is currently unable to handle
	 *                the request due to a temporary overloading or maintenance
	 *                of the server.  (This error code is needed because a 503
	 *                Service Unavailable HTTP status code cannot be returned
	 *                to the client via a HTTP redirect.)
	 *          Values for the "error" parameter MUST NOT include characters
	 *          outside the set %x20-21 / %x23-5B / %x5D-7E.
	 * </pre>
	 */
	public static final String ERROR = "error";

	/**
	 * error_description
	 * 
	 * <pre>
	 * OPTIONAL.  A human-readable ASCII [USASCII] text providing
	 *          additional information, used to assist the client developer in
	 *          understanding the error that occurred.
	 *          Values for the "error_description" parameter MUST NOT include
	 *          characters outside the set %x20-21 / %x23-5B / %x5D-7E.
	 * </pre>
	 */
	public static final String ERROR_DESCRIPTION = "error_description";

	/**
	 * error_uri
	 * 
	 * <pre>
	 * OPTIONAL.  A URI identifying a human-readable web page with
	 *          information about the error, used to provide the client
	 *          developer with additional information about the error.
	 *          Values for the "error_uri" parameter MUST conform to the URI-
	 *          Reference syntax, and thus MUST NOT include characters outside
	 *          the set %x21 / %x23-5B / %x5D-7E.
	 * </pre>
	 */
	public static final String ERROR_URI = "error_uri";

	/**
	 * Authentication Result Map Containing Parsed Results of the Location
	 * Header Response.
	 */
	private Map<String, String> authenticationResultMap = new HashMap<String, String>();

	/**
	 * Default Constructor
	 */
	public APIManagerAuthenticationResult() {
	}

	/**
	 * Constructor with a String to be Parsed into Name Value Pairs
	 */
	public APIManagerAuthenticationResult(final String locationHeader) {
		// Validate the Location Header contains our Access Token.
		if ((locationHeader != null)
				&& (locationHeader.contains(CONSOLE_TAG_RESPONSE))) {
			String[] stringArray = locationHeader.substring(
					locationHeader.indexOf(CONSOLE_TAG_RESPONSE)
							+ CONSOLE_TAG_RESPONSE.length()).split("&");
			for (int i = 0; i < stringArray.length; i++) {
				String[] nameValuePair = stringArray[i].split("=");
				if (nameValuePair.length == 2) {
					this.authenticationResultMap.put(nameValuePair[0],
							nameValuePair[1]);
				}
			}
		}
	}

	/**
	 * Access AuthenticationResultMap
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getAuthenticationResultMap() {
		return authenticationResultMap;
	}

	/**
	 * Helper Method to Directly obtain Access Token.
	 * 
	 * @return
	 */
	public final String getAccessToken() {
		return this.authenticationResultMap.get(ACCESS_TOKEN);
	}

	/**
	 * Helper Method to Directly obtain Expires In Value.
	 * 
	 * @return
	 */
	public final String getExpiresIn() {
		return this.authenticationResultMap.get(EXPIRES_IN);
	}

	/**
	 * Helper Method to Directly obtain Token Type Value.
	 * 
	 * @return
	 */
	public final String getTokenType() {
		return this.authenticationResultMap.get(TOKEN_TYPE);
	}

	/**
	 * Helper Method to Directly obtain Error Indicator.
	 * 
	 * @return
	 */
	public final String getError() {
		return this.authenticationResultMap.get(ERROR);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(APIManagerAuthenticationResult.class.getName() + ":{"
				+ LINE_BREAK);
		for (String key : this.authenticationResultMap.keySet()) {
			sb.append("  " + key + ":[" + this.authenticationResultMap.get(key)
					+ "]" + LINE_BREAK);
		}
		sb.append("}" + LINE_BREAK);
		return sb.toString();
	}

}
