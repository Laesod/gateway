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