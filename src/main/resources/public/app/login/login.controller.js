'use strict';

module = require('./_index');

function LoginCtrl($scope, $state, $http, $cookies, APP_SETTINGS, $window, $stateParams) {
    removeErrors();

    $scope.languages = APP_SETTINGS.supportedLanguages;
    $scope.language = $scope.languages[0];

    function onLoginSuccess(response) {
        if($stateParams.location){
            $window.location = $stateParams.location;
        }else{
            $window.location = APP_SETTINGS.apiUrl.defaultRedirectUrl;
        }
    }

    function onLoginFailure(response) {
        $scope.authenticationError = APP_SETTINGS.errorMessages.authenticationFailed;

        $scope.username = '';
        $scope.password = '';
    }

    $scope.login = function() {
        removeErrors();

        if (!$scope.username || !$scope.password) {
            $scope.formValidation.username = $scope.username ? null : APP_SETTINGS.errorMessages.emptyUsername;
            $scope.formValidation.password = $scope.password ? null : APP_SETTINGS.errorMessages.emptyPassword;

            return;
        }

        $http.post('login', "username=" + $scope.username + "&password=" + $scope.password + "&remember-me=" + $scope.remember_me, {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).success(function(data) {
            if (data.substring(0, 6) === "<html>") {
                onLoginFailure();
            } else {
                onLoginSuccess();
            }
        }).error(function() {
            onLoginFailure();
        });
    };

    function removeErrors() {
        $scope.formValidation = {
            username: null,
            password: null
        };

        $scope.authenticationError = null;
    }

    $scope.onLanguageChange = function() {
        $cookies.remove("appLanguage");
        $cookies.put("appLanguage", $scope.language.code);
    };

}

module.controller('LoginCtrl', LoginCtrl);