# spring-boot-for-gemfire-geode-samples
 1/11/2019 PCC Credentials Example Notes - Google Docs
https://docs.google.com/document/d/1FdWwnrgfnoB3BJBQI1kBGJWLMZ-M3Q0_0oMNzpmiay8/edit 1/1
PCC Credentials Example Notes
The error we are seeing when we run the app:
Caused by: org.apache.geode.security.AuthenticationRequiredException: No security
credentials are provided
To throw this error all you need to do is comment out the two lines in the gfsecurity.properties

security-client-auth-init: io.pivotal.support.config.CredAuthInit
security-manager = io.pivotal.support.config.DSGSecurityManager

The code we added in addition to the properties file can be found in the config package:

config
 + CredAuthInit
 + DSGSecurityManager
 + GemfireCredentials
 
 Initial TroubleShooting:
 
 Removed:
 - // @ClientCacheApplication(locators =
   //		{@ClientCacheApplication.Locator(host = "10.232.232.123", port = 55221)})
 - io.pivotal.support.config

Added manifest.yml with:

applications:
- name: spring-tips-pcc
  buildpack: java_buildpack_offline
  path: target/spring-boot-for-gemfire-geode-samples-1.0-SNAPSHOT.jar
  health-check-type: process
  services:
  - spring-tips-pcc

cf push 

output: 

2019-01-14T16:27:45.830-08:00 [APP/PROC/WEB/0] [OUT] 2019-01-15 00:27:45.830 INFO 21 --- [ main] io.pivotal.support.ClientApp : Started ClientApp in 5.843 seconds (JVM running for 6.865)
2019-01-14T16:27:45.885-08:00 [APP/PROC/WEB/0] [OUT] name:String:1:1:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=1]]
2019-01-14T16:27:45.885-08:00 [APP/PROC/WEB/0] [OUT] id:Object:identity:0:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=-1
2019-01-14T16:27:45.885-08:00 [APP/PROC/WEB/0] [OUT] fields=[
2019-01-14T16:27:45.885-08:00 [APP/PROC/WEB/0] [OUT] name=io.pivotal.support.Customer
2019-01-14T16:27:45.885-08:00 [APP/PROC/WEB/0] [OUT] [info 2019/01/15 00:27:45.884 UTC <main> tid=0x1] Caching PdxType[dsid=0, typenum=10817017
2019-01-14T16:28:15.919-08:00 [APP/PROC/WEB/0] [OUT] $$$ firstAttemptCustomer found: Optional[Customer(id=5, name=Name5)]
2019-01-14T16:28:15.919-08:00 [APP/PROC/WEB/0] [OUT] $$$ FirstAttempt Optional[Customer(id=5, name=Name5)]
2019-01-14T16:29:05.921-08:00 [APP/PROC/WEB/0] [OUT] $$$ secondAttemptCustomer found: Optional[Customer(id=5, name=Name5)]
2019-01-14T16:29:45.923-08:00 [APP/PROC/WEB/0] [ERR] $$$ ERROR thirdAttemptCustomer FOUND:Optional[Customer(id=5, name=Name5)]
2019-01-14T16:29:45.923-08:00 [APP/PROC/WEB/0] [OUT] $$$ ThirdAttempt Optional[Customer(id=5, name=Name5)]
2019-01-14T16:29:45.924-08:00 [APP/PROC/WEB/0] [ERR] $$$ ERROR FOUND EXPIRED ENTRY Optional[Customer(id=8, name=Name8)]
 
 To resolve:
 
 Was the ttl fixed with another configuration setting? 
 
