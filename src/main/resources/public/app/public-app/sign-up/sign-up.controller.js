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
//TODO: add form validation (mandatory fields, passwords matching)
//TODO: add error message hadling (display toast)
//TODO: add success handling (display toast)

'use strict';

module = require('../_index');

function SignUpCtrl($scope, $state, $http, $cookies, APP_SETTINGS, $window, $stateParams, globalService, $translate) {
    $scope.signUpPayload = {};

    var isFormDataValid = function() {
        var result = true;

        if ($scope.signUpPayload.password !== confirmPassword) {
            result = false;
        }
        return result;
    };

    $scope.onSubmit = function() {
        if (!isFormDataValid()) {
            $translate("passwordsDontMatch").then(function(value) {
                globalService.displayToast({
                    messageText: value,
                    messageType: "error"
                });
            });

            return;
        }

        globalService.signUp({
            payload: $scope.signUpPayload
        }).then(function() {
            $translate("userCreated").then(function(value) {
                globalService.displayToast({
                    messageText: value,
                    messageType: "success"
                });
            });
        }, function() {

        });
    };
}

module.controller('SignUpCtrl', SignUpCtrl);
