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

function ResetPasswordCtrl($scope, $rootScope, globalService, $stateParams, $state, $translate) {
    $rootScope.displayBackNavigation = true;    
    var resetPasswordToken = $stateParams.resetPasswordToken;

    var isFormDataValid = function() {
        var result = true;

        if ($scope.newPassword !== $scope.confirmNewPassword) {
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

        globalService.resetPassword({
            resetPasswordToken: resetPasswordToken,
            payload: {
                newPassword: $scope.newPassword
            }
        }).then(function() {
            $translate("resetPasswordFinished").then(function(value) {
                globalService.displayToast({
                    messageText: value,
                    messageType: "success"
                });
            });
            $state.go("app.login");
        });
    };

    $scope.onKeyPress = function(event) {
        if (event.keyCode === 13) {
            $scope.onSubmit();
        }
    };

    $scope.onFormElementChange = function(fieldId) {
        $rootScope.formElementsErrors[fieldId] = "";
    };
}

module.controller('ResetPasswordCtrl', ResetPasswordCtrl);