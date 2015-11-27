# gateway

[![Build Status](https://travis-ci.org/Laesod/gateway.svg?branch=master)](https://travis-ci.org/Laesod/gateway)
[![License](http://img.shields.io/:license-apache 2.0-blue.svg)](https://github.com/Laesod/gateway/blob/master/LICENSE)

Docs: http://laesod.github.io/gateway/

The goal of this project is to have a "common" seed for spring based application that enables
authentication flows (sign up, login, reset password, change password etc), security (role based, project based, user group based), common features (like localization, entity version tracking etc). It contains angular 1.x UI portion that is using angular material library (https://material.angularjs.org/latest/) and contains typical public views like sign up, login, reset password etc. UI application is based on gulp-browserify boilerplate for angularjs (read here for more details: https://github.com/jakemmarsh/angularjs-gulp-browserify-boilerplate).

Project is initially configured to be used along with mainUi-seed application (initial private portion of the "common" web application).

Prerequisites:

	- You have mainUi-seed application up and running (after login you should be redirected to this project)

In order to start the project please follow the steps:

	- Make sure that you have MySQL db running locally on default port (user: root, password: 'gateway');
	- Create db with name gateway. This database will be initialized by the application on the run time. 
	Two initial users will be added:'admin@gmail.com' and 'user@gmail.com' both with password 'admin'. admin@gmail.com will be assigned to roles ROLE_ADMIN and ROLE_USER, user@gmail.com will be assigned to the role ROLE_USER (check db_initializer.sql in resources for more details). Passwords will be stored in DB in encoded mode.

In command line navigate to gateway folder and execute:

	- npm i; (will install npm packages dependencies)
	- bower i; (will install js dependencies)
	- gulp prod; (will produce build folder with final app that should be ready for use)

Open another command line, navigate to gateway folder and execute (previous terminal should be open): 

	- mvn clean install
	- mvn spring-boot:run

To generate site reports run ( result will be available in target/site/index.html ):

    - mvn clean install site

Unit tests and integration tests will be executed. Embedded mail server will be started during integration test and stopped after tests finished. During development optionally mail server can be run manually. It can be an embedded mvn mailserver plugin (that can be run by mvn emailserver:run in separate console) or a separate application (for example Papercut at https://github.com/jaben/papercut).
In order to skip the tests during compilation use mvn clean install -DskipTests. 
Now you can try to access localhost:2000/gateway. Note that for spring application URL context path /gateway is specified (needed for deployment on production environment and being served by proxy based on url paths)

For testing purposes use user/password created by db_initializer.sql script.

To speed up UI development next approach is supported:

After starting spring application in command line navigate to gateway folder and execute "gulp dev" (will prepare UI that is ready to be served by web server without URL context path). In another terminal navigate to gateway/src/main/public and 
start npm live-server on port 1000 by executing "live-server --port=1000" (can be any other web server but used port should be 1000 or 1001 though). UI application 
should be available now at localhost:1000 and can be updated in "live" mode. Served this way UI application should be able to access services from spring application (except login service).


Features that can be found in the project:

	- Spring security used based on cookies

	- Remember me feature (no login needed for two months);

	- Request input data validation (validation result will be returned with details per attribute - can be used to display field validation in UI for each item of the form)

	- UI form items validation based on BE request input data validation

	- Backend/UI Localization enabled (based on cookie appLanguage - cookie will stay valid for one month). I.e. request input validation results will be returned with respect of the current language.
	 
	- Rest end point unit test examples 

	- Profiling example (different db source settings based on application.properties profile value (development/prod/test));

	- Spring based audit mechanism (automatic population of the fields CreatedBy, ModifiedBy, CreatedAt, LastModifiedAt);

	- Transactional way of execution of the operations relevant for db modifications;

	- Concurrent entity modification locking (prevent data from been overwritten if changed between read/write operation);

	- Hibernate envers enabled (read more here: http://docs.jboss.org/envers/docs/). That gives possibility to track entity modifications at individual field level (for test purposes activated for userEntity);

	- Multilanguage support(as many languages as needed) for db fields (as example check description field for ProjectEntity);
	
General notes:

	- for email account that will be used as a sender turn on action should be done here (in case of gmail account): https://www.google.com/settings/security/lesssecureapps
	- example of shell script for jenkins to start the app on production server:
	
sudo bower install --allow-root 
sudo gulp prod
sudo mvn clean install -DskipTests 
echo "sudo mvn spring-boot:run -Dspring.profiles.active=prod" | at now + 1 minutes	
	
## License

Licensed under the [Apache 2.0 license](https://github.com/Laesod/gateway/blob/master/LICENSE).
