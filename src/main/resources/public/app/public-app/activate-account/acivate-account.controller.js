'use strict';

module = require('../_index');

function ActivateAccountCtrl($scope, $state, globalService, $stateParams) {
    var emailVerificationToken = $stateParams.emailVerificationToken;

    if (emailVerificationToken) {
        globalService.activateUser({
            emailVerificationToken: emailVerificationToken
        }).then(function() {
            alert("user was activated...");
            $scope.activationFinished = true;
        });
    }

    $scope.onContinue = function() {
        $state.go("app.login");
    };
}

module.controller('ActivateAccountCtrl', ActivateAccountCtrl);