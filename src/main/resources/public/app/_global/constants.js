/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
'use strict';

var contextParams = require('./context-params');

var AppSettings = function(config) {
    var mainAppBaseUrl = 'http://' + config.mainAppHost + ":" + config.mainAppPort;
    var apiGatewayBaseUrl = 'http://' + config.gatewayHost + ":" + config.gatewayPort;
    // var mainAppBaseUrl = 'http://localhost:63769';
    // var apiGatewayBaseUrl = 'http://localhost:2000';

    return {
        appTitle: 'application',
        appLanguage: 'en',
        contextPrefix: contextParams.contextPrefix(), //'/gateway',
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
