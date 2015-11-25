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

module = require('../_index');

function LoginCtrl($scope, $state, $http, $cookies, APP_SETTINGS, $window, $stateParams, globalService, $translate) {
    function onLoginSuccess(response) {
        if ($stateParams.location) {
            $window.location = $stateParams.location;
        } else {
            $window.location = APP_SETTINGS.apiUrl.defaultRedirectAfterLoginUrl;
        }
    }

    function onLoginFailure(response) {
        $translate("wrongCredentials").then(function(value) {
            globalService.displayToast({
                messageText: value,
                messageType: "error"
            });
        });
    }

    $scope.onLogin = function() {
        globalService.login({
            username: $scope.username,
            password: $scope.password,
            remember_me: $scope.remember_me
        }).then(function(data) {
            if (data.substring(0, 6) === "<html>") {
                onLoginFailure();
            } else {
                onLoginSuccess();
            }
        }, function() {
            onLoginFailure();
        });
    };

    $scope.onSignUp = function() {
        $state.go("app.signUp");
    };

    $scope.onForgotPassword = function() {
        $state.go("app.forgotPassword");
    };

    $scope.onKeyPress = function(event) {
        if (event.keyCode === 13) {
            $scope.onLogin();
        }
    };
}

module.controller('LoginCtrl', LoginCtrl);