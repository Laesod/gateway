//TODO: add form validation (mandatory fields, passwords matching)
//TODO: add error message hadling (display toast)
//TODO: add success handling (display toast)

'use strict';

module = require('../_index');

function SignUpCtrl($scope, $state, $http, $cookies, APP_SETTINGS, $window, $stateParams, globalService) {
    $scope.signUpPayload = {};

    $scope.onSubmit = function() {
        globalService.signUp({
            payload: $scope.signUpPayload
        }).then(function() {
            alert("userCreated");
        }, function() {

        });
    };
}

module.controller('SignUpCtrl', SignUpCtrl);