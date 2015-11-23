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

    service.request = function(params) {
        var deferred = $q.defer();

        $http(params).
        success(function(data) {
            deferred.resolve(data);
        }).
        error(function(err, status) {
            deferred.reject(err);
        });

        return deferred.promise;
    };

    service.login = function(parameters) {
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

    service.signUp = function(parameters) {
        var url = APP_SETTINGS.apiUrl.createUserUrl;

        return this.request({
            method: 'POST',
            url: url,
            data: parameters.payload
        });
    };

    service.activateUser = function(parameters) {
        var url = APP_SETTINGS.apiUrl.activateUser + '?emailVerificationToken=' + parameters.emailVerificationToken;

        return this.request({
            method: 'PUT',
            url: url
        });
    };

    service.initiateResetPassword = function(parameters) {
        var url = APP_SETTINGS.apiUrl.initiateResetPassword + '?email=' + parameters.email;

        return this.request({
            method: 'PUT',
            url: url
        });
    };

    service.resetPassword = function(parameters) {
        var url = APP_SETTINGS.apiUrl.resetPassword + parameters.resetPasswordToken;

        return this.request({
            method: 'PUT',
            url: url,
            data: parameters.payload
        });
    };

    service.displayToast = function(parameters) {
        var templateUrl = APP_SETTINGS.contextPrefix + "/templates/toast-template.html";

        $rootScope.toastMessageText = [parameters.messageText];
        $rootScope.toastMessageType = parameters.messageType;

        var oToast = {
            controller: "ToastCtrl",
            templateUrl: templateUrl,
            hideDelay: 3000,
            position: "top right"
        };

        $mdToast.show(oToast);
    };

    return service;
}

module.exports.service('globalService', globalService);
