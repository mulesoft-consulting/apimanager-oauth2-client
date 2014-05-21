apimanager-oauth2-client
========================

JAVA Client Example of Accessing APIManager via simple HTTP Client

A simple APIManagerClient provides simple demonstration of Authenticating and
Accessing an Authenticated API Manager Resource using Java and Apache HttpClient constructs.
 
Please see http://tools.ietf.org/html/draft-ietf-oauth-v2-31 for details on OAUTH V2 Dance.


Building
========
mvn clean install



Running
=======

mvn exec:java -Dexec.mainClass="org.mulesoft.apimanager.client.APIManagerClient" \
              -Dexec.classpathScope=runtime \
              -Dexec.args="|APIManager User Principal| |APIManager User Credentials|"
