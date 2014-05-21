package org.mulesoft.apimanager.client;

/**
 * APIManager Client Constants
 * 
 * @see http://tools.ietf.org/html/draft-ietf-oauth-v2-31 for details on OAUTH
 *      V2 Dance.
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public interface APIManagerClientConstants {

	static final String API_ENDPOINT = "https://anypoint.mulesoft.com/api-platform/api";

	static final String REDIRECT_URI = "https%3A//anypoint.mulesoft.com/api-platform/api/console/";

	static final String ACCESS_TOKEN = "access_token";

	static final String CONSOLE_TAG_RESPONSE = "/console/#";

	static final String API_MANAGER_SCOPES = "ADMIN_ORGANIZATIONS%20READ_SERVICES%20WRITE_SERVICES%20CONSUME_SERVICES%20APPLY_POLICIES%20READ_CONSUMERS%20WRITE_CONSUMERS%20CONTRACT_MGMT%20CONSUME_POLICIES";

	static final String API_MANAGER_CONTENT_JSON = "application/vnd.mulesoft.habitat+json";

	static final String API_MANAGER_CONTENT_XML = "application/vnd.mulesoft.habitat+xml";

	static final String ERROR = "error";

	static final String ERROR_DESCRIPTION = "error_description";

	// Resources

	static final String AUTHORIZE_RESOURCE = "/authorize";

	static final String ORGANIZATIONS_RESOURCE = "/organizations/current";

	static final String SERVICES_RESOURCE = "/services";

	static final String POLICES_RESOURCE = "/policies";

	static final String ANNOUNCEMENTS_RESOURCE = "/announcements";

	static final String POLICY_TEMPLATES_RESOURCE = "/policy-templates";

	static final String CONSUMERS_RESOURCE = "/consumers";

	static final String CONTRACTS_RESOURCE = "/contracts";

	static final String TAXONOMIES_RESOURCE = "/taxonomies";

	static final String TAGS_RESOURCE = "/tags";

	static final String AGENT_TOKENS_RESOURCE = "/agent-tokens";

	static final String ENVIRONMENTS_RESOURCE = "/environments";

	static final String USERS_RESOURCE = "/users";

}
