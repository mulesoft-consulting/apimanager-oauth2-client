package org.mulesoft.apimanager.client;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.localserver.LocalTestServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mulesoft.apimanager.client.results.APIManagerAuthenticationResult;

/**
 * APIManagerClientTest
 * 
 * Simple rudimentary Tests
 * 
 * @author jeff.schenk@mulesoft.com
 * 
 */
public class APIManagerClientTest implements APIManagerClientConstants {
	// Logger
	private static final Log log = LogFactory
			.getLog(APIManagerClientTest.class);

	// Mocked Test Server to represent API Manager
	private static LocalTestServer apimanager_server = null;
	private static String serverUrl = null;

	// Test Token used throughout
	static final UUID TEST_TOKEN = UUID.randomUUID();

	@Mock
	static APIManagerMockAuthenticationHandler authenticationHandler = new APIManagerMockAuthenticationHandler();
	static APIManagerMockErrorAuthenticationHandler authenticationErrorHandler = new APIManagerMockErrorAuthenticationHandler();

	/**
	 * Setup Mocked APIManager
	 */
	@Before
	public void setUp() throws Exception {
		apimanager_server = new LocalTestServer(null, null);
	}

	/**
	 * Tear Down Mocked APIManager
	 */
	@After
	public void tearDown() {
		try {
			apimanager_server.stop();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	public void testSimpleAuthenticationResultNoData() {

		APIManagerAuthenticationResult result = new APIManagerAuthenticationResult(
				"");
		assertNotNull(result);
		assertTrue(result.getAuthenticationResultMap().size() == 0);

	}

	@Test
	public void testAuthenticationResultAccessDenined() throws Exception {
		// Setup Server with correct Handler.
		apimanager_server.register(AUTHORIZE_RESOURCE, authenticationErrorHandler);
		apimanager_server.start();
		serverUrl = "http://"
				+ apimanager_server.getServiceAddress().getHostName() + ":"
				+ apimanager_server.getServiceAddress().getPort();

		// Create HTTP Client Construct
		HttpClient httpClient = HttpClientBuilder.create().build();

		// Authenticate
		APIManagerAuthenticationResult result = APIManagerClientTools
				.authenticate(httpClient, serverUrl + AUTHORIZE_RESOURCE, "testuser",
						"password");

		assertNotNull(result);
		assertTrue(result.getAuthenticationResultMap().size() == 1);
		assertFalse(result.getAuthenticationResultMap()
				.containsKey(ACCESS_TOKEN));
		assertTrue(result.getAuthenticationResultMap()
				.containsKey(ERROR));
		assertEquals("access_denied", result.getError());

	}
	
	@Test
	public void testAuthenticationResultNoResource() throws Exception {
		// Setup Server with correct Handler.
		apimanager_server.register(AUTHORIZE_RESOURCE, authenticationErrorHandler);
		apimanager_server.start();
		serverUrl = "http://"
				+ apimanager_server.getServiceAddress().getHostName() + ":"
				+ apimanager_server.getServiceAddress().getPort();

		// Create HTTP Client Construct
		HttpClient httpClient = HttpClientBuilder.create().build();

		// Authenticate
		APIManagerAuthenticationResult result = APIManagerClientTools
				.authenticate(httpClient, "", "U", "P");
		assertNull(result);

	}

	@Test
	public void testAuthenticationResultNoCredentials() throws Exception {
		// Setup Server with correct Handler.
		apimanager_server.register(AUTHORIZE_RESOURCE, authenticationErrorHandler);
		apimanager_server.start();
		serverUrl = "http://"
				+ apimanager_server.getServiceAddress().getHostName() + ":"
				+ apimanager_server.getServiceAddress().getPort();

		// Create HTTP Client Construct
		HttpClient httpClient = HttpClientBuilder.create().build();

		// Authenticate
		APIManagerAuthenticationResult result = APIManagerClientTools
				.authenticate(httpClient, serverUrl + AUTHORIZE_RESOURCE, null,
						null);

		assertNull(result);

	}

	@Test
	public void testAuthenticationResult() throws Exception {
		// Setup Server with correct Handler.
		apimanager_server.register(AUTHORIZE_RESOURCE, authenticationHandler);
		apimanager_server.start();
		serverUrl = "http://"
				+ apimanager_server.getServiceAddress().getHostName() + ":"
				+ apimanager_server.getServiceAddress().getPort();

		// Create HTTP Client Construct
		HttpClient httpClient = HttpClientBuilder.create().build();

		// Authenticate
		APIManagerAuthenticationResult result = APIManagerClientTools
				.authenticate(httpClient, serverUrl + AUTHORIZE_RESOURCE,
						"testuser", "password");

		assertNotNull(result);
		assertTrue(result.getAuthenticationResultMap().size() == 3);
		assertTrue(result.getAuthenticationResultMap()
				.containsKey(ACCESS_TOKEN));
		assertEquals(UUID
				.fromString(APIManagerClientTest.TEST_TOKEN.toString())
				.toString(), result.getAccessToken());
		assertEquals("3600", result.getExpiresIn());
		assertEquals("bearer", result.getTokenType());

	}

}
