package org.mulesoft.apimanager.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mulesoft.apimanager.client.results.APIManagerAuthenticationResult;
import org.mulesoft.apimanager.client.results.APIManagerResourceResult;

/**
 * APIManagerClient Provides simple demonstration of Authenticating and
 * Accessing a API Manager Resource using Java and Apache HttpClient constructs.
 * 
 * @see http://tools.ietf.org/html/draft-ietf-oauth-v2-31 for details on OAUTH
 *      V2 Dance.
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public class APIManagerClient implements APIManagerClientConstants {

	// Logger
	private static final Log log = LogFactory.getLog(APIManagerClient.class);
	
	/**
	 * main Drives End to End demonstration of accessing API Manager using a API
	 * Manager's Principal and Credentials.
	 * 
	 * @param args
	 *            - Argument[0] - Will assume to be User Principal 
	 *            - Argument[1] - Will assume to be User Credentials
	 * 
	 */
	public static void main(String[] args) {
		// Validate arguments
		if ((args == null) || (args.length != 2)) {
			log.warn("Invalid Number of Arguments Specified.");
			log.info("Usage:");
			log.info(APIManagerClient.class.getName()
							+ " <API Manager User Principal> <API Manager User Credentials>");
			System.exit(1);
		}

		// Simple Notification Banner.
		log.info("Access MuleSoft Anypoint API Manager via Account Principal: "+ args[0]);
		// Create HTTP Client Construct
		HttpClient httpClient = HttpClientBuilder.create().build();

		/**
		 * Authenticate and Obtain an Access Token for Subsequent GET Resource
		 * Requests.
		 */
		try {
			APIManagerAuthenticationResult authResult = APIManagerClientTools
					.authenticate(httpClient, args[0], args[1]);
			if ((authResult == null)
					|| (!authResult.getAuthenticationResultMap().containsKey(
							ACCESS_TOKEN))) {
				if (authResult.getAuthenticationResultMap().containsKey(ERROR)) {
					log.error("Error: "
									+ authResult.getError()
									+ " "
									+ (authResult.getAuthenticationResultMap()
											.containsKey(ERROR_DESCRIPTION) ? authResult
											.getAuthenticationResultMap().get(
													ERROR_DESCRIPTION) : ""));
				} else {
					log.error("No Access Token Found!");
				}
				// End Main.
				System.exit(2);
			}

			// Access protected Resources....

			// Optional parameters: offset=0&limit=10&order=desc

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), ORGANIZATIONS_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), SERVICES_RESOURCE, "", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), POLICES_RESOURCE, "", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), ANNOUNCEMENTS_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), POLICY_TEMPLATES_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools
					.getResource(httpClient, authResult.getAccessToken(),
							CONSUMERS_RESOURCE, "", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), CONTRACTS_RESOURCE,
					"orderBy=lastUpdated&order=desc", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), TAXONOMIES_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), TAGS_RESOURCE, "q=_", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), AGENT_TOKENS_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), ENVIRONMENTS_RESOURCE, "",
					"JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), USERS_RESOURCE, "", "JSON"));

			reportResult(APIManagerClientTools.getResource(httpClient,
					authResult.getAccessToken(), USERS_RESOURCE, "", "XML"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		log.info("\nDone");
		System.exit(0);
	}

	/**
	 * Simple helper Method to report Results.
	 * 
	 * @param result
	 */
	private static void reportResult(APIManagerResourceResult result) {
		log.info("Resource Request: " + result.getResource()+" Status: " + result.getStatusCode()
				+ " Data Length: "
				+ ((result.getData() != null) ? result.getData().length() : 0)
				+ " Data:[" + result.getData() + "]");
	}

}
