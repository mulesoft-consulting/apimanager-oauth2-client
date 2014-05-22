apimanager-oauth2-client
========================

JAVA Client Example of Accessing APIManager via simple HTTP Client

A simple APIManagerClient provides simple demonstration of Authenticating and
Accessing protected API Manager Resources using Java and Apache HttpClient constructs.
 

Please see [http://tools.ietf.org/html/draft-ietf-oauth-v2-31] for details on OAUTH V2.


Requirements
------------
The only requirement is to have JAVA SDK and Maven installed and available.  
This project can easily be imported into Anypoint Studio or Native Eclipse. 

Building
--------
To build the simple project and run the Test Suite, perform the following command:

mvn clean install


Running
-------
To run from command line using Maven perform the following command substituting your Credentials:

mvn exec:java -Dexec.mainClass="org.mulesoft.apimanager.client.APIManagerClient" \
 -Dexec.classpathScope=runtime \
 -Dexec.args="arg0 arg1"

Where: 
* arg0 is your API Manager Account Principal
* arg1 is your API Manager Account Credentials


