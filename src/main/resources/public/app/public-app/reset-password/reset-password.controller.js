'use strict';

module = require('../_index');

function ResetPasswordCtrl($scope, globalService, $stateParams, $state) {
    var resetPasswordToken = $stateParams.resetPasswordToken;

    $scope.onSubmit = function() {
        globalService.resetPassword({
            resetPasswordToken: resetPasswordToken,
            payload: {
                newPassword: $scope.newPassword
            }
        }).then(function() {
            alert("password was reset...");
            $state.go("app.login");
        });
    };
}

module.controller('ResetPasswordCtrl', ResetPasswordCtrl);