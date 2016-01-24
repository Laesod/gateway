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

module.exports = angular.module('globals', []);

function globalService($http, $q, $cookies, $window, APP_SETTINGS, $mdToast, $rootScope) {
    var service = {};

    service.request = function (params) {
        var that = this;
        var deferred = $q.defer();
        params.withCredentials = true;

        $http(params).
            success(function (data) {
                deferred.resolve(data);
            }).
            error(function (err, status) {
                if(err && err.status){
                    switch (err.status) {
                        case 500:
                            that.displayToast({
                                messageText: err.message,
                                messageType: "error"
                            });
                            break;
                        case 400:
                            that.parseSpringValidationError(err);
                            break;
                    }                    
                }
                deferred.reject(err);
            });

        return deferred.promise;
    };

    service.login = function (parameters) {
        var url = APP_SETTINGS.apiUrl.loginUrl;

        return this.request({
            method: 'POST',
            url: url,
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            },
            data: "username=" + parameters.username + "&password=" + parameters.password + "&remember-me=" + parameters.remember_me
        });
    };

    service.signUp = function (parameters) {
        var url = APP_SETTINGS.apiUrl.createUserUrl;

        return this.request({
            method: 'POST',
            url: url,
            data: parameters.payload
        });
    };

    service.activateUser = function (parameters) {
        var url = APP_SETTINGS.apiUrl.activateUser + '?emailVerificationToken=' + parameters.emailVerificationToken;

        return this.request({
            method: 'PUT',
            url: url
        });
    };

    service.initiateResetPassword = function (parameters) {
        var url = APP_SETTINGS.apiUrl.initiateResetPassword;

        return this.request({
            method: 'PUT',
            url: url,
            data: parameters.payload
        });
    };

    service.resetPassword = function (parameters) {
        var url = APP_SETTINGS.apiUrl.resetPassword + parameters.resetPasswordToken;

        return this.request({
            method: 'PUT',
            url: url,
            data: parameters.payload
        });
    };

    service.displayToast = function (parameters) {
        var templateUrl = APP_SETTINGS.contextPrefix + "/templates/toast-template.html";

        $rootScope.toastMessageText = [parameters.messageText];
        $rootScope.toastMessageType = parameters.messageType;

        var oToast = {
            controller: "ToastCtrl",
            templateUrl: templateUrl, //"templates/toast-template.html",
            hideDelay: 3000,
            position: "top right"
        };

        $mdToast.show(oToast);
    };

    service.parseSpringValidationError = function (parameters) {
        var errorMessages = [];
        var errorMessage = "";
        var fieldName = "";
        var objectName = "";

        if (parameters.message) {
            while (parameters.message.indexOf("default message") >= 0) {

                parameters.message = parameters.message.substring(parameters.message.indexOf("'") + 1);
                objectName = parameters.message.substring(0, parameters.message.indexOf("'"));
                parameters.message = parameters.message.substring(parameters.message.indexOf("default message") + 17);

                fieldName = parameters.message.substring(0, parameters.message.indexOf("]"));
                parameters.message = parameters.message.substring(parameters.message.indexOf("default message") + 17);
                errorMessage = parameters.message.substring(0, parameters.message.indexOf("]"));
                errorMessages.push({
                    object: objectName,
                    field: fieldName,
                    message: errorMessage
                });

                // if ($rootScope.formElementsErrors[objectName + '_' + fieldName]) {
                // debugger;
                $rootScope.formElementsErrors[objectName + '_' + fieldName] = errorMessage;
                // }
            }
        }

        return errorMessages;
    };

    return service;
}

module.exports.service('globalService', globalService);