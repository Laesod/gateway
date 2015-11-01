'use strict';

module = require('../_index');

function ForgotPasswordCtrl($scope, globalService) {
    $scope.onSubmit = function() {
        globalService.initiateResetPassword({
            email: $scope.email
        }).then(function() {

        });
    };
}

module.controller('ForgotPasswordCtrl', ForgotPasswordCtrl);