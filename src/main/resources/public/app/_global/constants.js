'use strict';

var AppSettings = function(config) {
    var mainAppBaseUrl = 'http://' + config.mainAppHost + ":" + config.mainAppPort;
    var apiGatewayBaseUrl = 'http://' + config.gatewayHost + ":" + config.gatewayPort;
    // var mainAppBaseUrl = 'http://localhost:63769';
    // var apiGatewayBaseUrl = 'http://localhost:2000';

    return {
        appTitle: 'application',
        apiUrl: {
            defaultRedirectAfterLoginUrl: mainAppBaseUrl + "/mainUi",
            loginUrl: apiGatewayBaseUrl + '/gateway/login',
            createUserUrl: apiGatewayBaseUrl + '/gateway/gateway/createUser',
            activateUser: apiGatewayBaseUrl + '/gateway/gateway/activateUser',
            initiateResetPassword: apiGatewayBaseUrl + '/gateway/gateway/initiateResetPassword',
            resetPassword: apiGatewayBaseUrl + '/gateway/gateway/resetPassword?resetPasswordToken='
        },
        errorMessages: {
            authenticationFailed: 'Invalid username and/or password.',
            emptyUsername: 'Username is mandatory',
            emptyPassword: 'Password is mandatory'
        },
    };
};

module.exports = AppSettings;