'use strict';

module = require('../_index');

function LoginCtrl($scope, $state, $http, $cookies, APP_SETTINGS, $window, $stateParams, globalService) {
    // $scope.languages = APP_SETTINGS.supportedLanguages;
    // $scope.language = $scope.languages[0];

    function onLoginSuccess(response) {
        if ($stateParams.location) {
            $window.location = $stateParams.location;
        } else {
            $window.location = APP_SETTINGS.apiUrl.defaultRedirectAfterLoginUrl;
        }
    }

    function onLoginFailure(response) {
        globalService.displayToast({
            messageText: "Wrong credentials",
            messageType: "error"
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

    // $scope.onLanguageChange = function() {
    //     $cookies.remove("appLanguage");
    //     $cookies.put("appLanguage", $scope.language.code);
    // };

}

module.controller('LoginCtrl', LoginCtrl);