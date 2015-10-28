# gateway

The goal of this project is to have a "common" seed for spring based application that enables
authentication flows (sign up, login, reset password, change password etc), security (role based, project based, user group based), commont features (like localization, entity version tracking etc). It contains angular 1.x UI portion that is css framework agnostic (no css frameworked is used and any can be applied) and contains typical public views like sign up, login, reset password etc. UI application is based on gulp-browserify boilerplate for angularjs (read here for more details: https://github.com/jakemmarsh/angularjs-gulp-browserify-boilerplate).

Project is initially configured to be used along with mainUi-seed application (initial private portion of the "common" web application).

Prerequisites:

	- You have mainUi-seed application up and running on port 63769

In order to start the project please follow the steps:

	- Make sure that you have MySQL db running locally on default port (user: root, password: '');
	- Create db with name gateway. This database will be initialized by the application on the run time. Two initial users will be added:'admin@gmail.com' and 'user@gmail.com' both with password 'admin'. admin@gmail.com will be assigned to roles ROLE_ADMIN and ROLE_USER, user@gmail.com will be assigned to the role ROLE_USER (check db_initializer.sql in resources for more details). Passwords will be stored in DB in encoded mode.

In command line navigate to gateway folder and execute:

	- npm i; (will install npm packages dependencies)
	- bower i; (will install js dependencies)
	- gulp dev; (will produce build folder with final app that should be ready for production use)

Open another command line, navigate to gateway folder and execute (previous terminal should be open). Unit tests will be run. Start DB, local mail server will be started during integration test and stoped after tests finished. During development optionally mail server can be run manually  (for example https://github.com/jaben/papercut Papercut or mvn emailserver:run in separate console).

	- mvn clean install
	- mvn spring-boot:run

Now you can try to access localhost:2000

For testing purposes use user/password created by db_initializer.sql script.

Features:

	- Spring security used based on cookies

	- Logged in user can change his password with PUT request http://localhost:2000/gateway/changePassword that accept two attributes in the payload: currentPassword and newPassword.

	- Possibility to remember password (will be kept for two months);

	- Request input data validation (validation result will be returned with details per attribute - can be used to display field validation in UI for each view of the form)

	- Backend Localization enabled (based on cookie gatewayLanguage - cookie will stay valid for one month). I.e. request input validation results will be returned with respect of the current language. As example - there is validation for proper email format and password length for create user end point.

	- Rest end point unit test examples 

	- Profiling example (different db source settings based on application.properties profile value (development/production/test));

	- Spring based audit mechanism (automatic population of the fields CreatedBy, ModifiedBy, CreatedAt, LastModifiedAt);

	- Transactional way of execution of the operations relevant for db modifications;

	- Concurrent entity modification locking (prevent data from been overwritten if changed between read/write operation);

	- Hibernate envers enabled (read more here: http://docs.jboss.org/envers/docs/). That gives possibility to track entity modifications at individual field level (for test purposes activated for userEntity);

	- Multilanguage support(as many languages as needed) for db fields (as example check description field for ProjectEntity);
	
General notes:

	- for email account that will be used as a sender turn on action should be done here (in case of gmail account): https://www.google.com/settings/security/lesssecureapps
