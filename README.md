# gateway

In order to start the project please follow the steps:

- Make sure that you have MySQL db running locally on default port (user: root, password: '');
- Create db with name gateway. This database will be initialized by the application on the run time. Two users will be added:
'admin@gmail.com' and 'user@gmail.com' both with password 'admin'. admin@gmail.com will be assigned to roles ROLE_ADMIN and ROLE_USER, user@gmail.com will be assigned to the role ROLE_USER (check db_initializer.sql in resources for more details). Passwords will 
be stored in DB in encoded mode.

- In command line navigate to gateway folder and execute 
	- npm install
	- bower install
	- gulp dev	

- Open another command line, navigate to gateway folder and execute (previous terminal should be open). Unit tests will be run (start local mail server for example https://github.com/jaben/papercut Papercut,  start DB)
	- mvn clean install
	- mvn spring-boot:run

Now you can try to access localhost:2000

For testing purposes use user/password created by db_initializer.sql script.

Features:

- Logged in user can change his password with PUT request http://localhost:2000/gateway/changePassword that
accept two attributes in the payload: currentPassword and newPassword.

- Possibility to remember password (will be kept for two months);

- Request input data validation (validation result will be returned with details per attribute - can be used to
display field validation in UI for each view of the form)

- Backend Localization enabled (based on cookie gatewayLanguage - cookie will stay valid for one month). I.e. request input validation results will be returned with respect of the current language. As example - there is validation for proper email format and password length for create user end point.

- Rest end point unit test examples (groovy based)

- profiling example (different db source settings based on
application.properties profile value (development/production));

- spring based audit mechanism (automatic population of the fields CreatedBy, ModifiedBy, CreatedAt, LastModifiedAt);

- transactional way of execution of the operations relevant for db modifications;

- concurrent entity modification locking (prevent data from been overwritten if changed between read/write operation);

- hibernate envers enabled (read more here: http://docs.jboss.org/envers/docs/) - gives possibility to 
track entity modifications at individual field level (for test purposes activated for userEntity);

- multilanguage support(as many languages as needed) for db fields (as example check description field for ProjectEntity);
	
General notes:
- for email account that will be used as a sender turn on action should be done here:
https://www.google.com/settings/security/lesssecureapps
