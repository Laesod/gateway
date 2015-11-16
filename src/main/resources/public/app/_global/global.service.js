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
        var templateUrl = "/gateway/templates/toast-template.html";

        $rootScope.toastMessageText = parameters.messageText;
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