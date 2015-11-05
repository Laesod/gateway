'use strict';

var AppSettings = function(config) {
    var mainAppBaseUrl = 'http://' + config.mainAppHost + ":" + config.mainAppPort;
    var apiGatewayBaseUrl = 'http://' + config.gatewayHost + ":" + config.gatewayPort;
    // var mainAppBaseUrl = 'http://localhost:63769';
    // var apiGatewayBaseUrl = 'http://localhost:2000';

    return {
        appTitle: 'application',
        apiUrl: {
            defaultRedirectAfterLoginUrl: mainAppBaseUrl,
            loginUrl: apiGatewayBaseUrl + '/login',
            createUserUrl: apiGatewayBaseUrl + '/gateway/createUser',
            activateUser: apiGatewayBaseUrl + '/gateway/activateUser',
            initiateResetPassword: apiGatewayBaseUrl + '/gateway/initiateResetPassword',
            resetPassword: apiGatewayBaseUrl + '/gateway/resetPassword?resetPasswordToken='
        },
        errorMessages: {
            authenticationFailed: 'Invalid username and/or password.',
            emptyUsername: 'Username is mandatory',
            emptyPassword: 'Password is mandatory'
        },
    };
};

module.exports = AppSettings;