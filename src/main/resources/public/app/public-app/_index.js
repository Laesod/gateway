'use strict';

var bulk = require('bulk-require');

module.exports = angular.module('publicApp', []);

angular.module('publicApp').config(function($stateProvider) {
    $stateProvider
        .state('app.login', {
            parent: 'app',
            url: '/login?location',
            templateUrl: 'views/login.html',
            controller: 'LoginCtrl',
            title: 'Login'
        })
        .state('app.forgotPassword', {
            parent: 'app',
            url: '/forgot-password',
            templateUrl: 'views/forgot-password.html',
            controller: 'ForgotPasswordCtrl',
            title: 'Forgot Password'
        })
        .state('app.signUp', {
            parent: 'app',
            url: '/sign-up',
            templateUrl: 'views/sign-up.html',
            controller: 'SignUpCtrl',
            title: 'Sign Up'
        })
        .state('app.activateAccount', {
            parent: 'app',
            url: '/activate-account/:emailVerificationToken',
            templateUrl: 'views/activate-account.html',
            controller: 'ActivateAccountCtrl',
            title: 'Activate Account'
        })
        .state('app.resetPassword', {
            parent: 'app',
            url: '/reset-password/:resetPasswordToken',
            templateUrl: 'views/reset-password.html',
            controller: 'ResetPasswordCtrl',
            title: 'Reset Password'
        });
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);