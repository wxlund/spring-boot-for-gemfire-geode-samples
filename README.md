# spring-boot-for-gemfire-geode-samples
 1/11/2019 PCC Credentials Example Notes - Google Docs
https://docs.google.com/document/d/1FdWwnrgfnoB3BJBQI1kBGJWLMZ-M3Q0_0oMNzpmiay8/edit 1/1
PCC Credentials Example Notes
The error we are seeing when we run the app:
Caused by: org.apache.geode.security.AuthenticationRequiredException: No security
credentials are provided
To throw this error all you need to do is comment out the two lines in the gfsecurity.properties
The code we added in addition to the properties file can be found in the config package: