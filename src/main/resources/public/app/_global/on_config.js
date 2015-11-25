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

function OnConfig($urlRouterProvider, $httpProvider, $translateProvider, APP_SETTINGS) {
    $urlRouterProvider.otherwise('login');

    $httpProvider.interceptors.push(function($injector, $window, $cookies, $q) {
        return {
            'responseError': function(err, status) {
                return $q.reject(err);
            }
        };
    });

    //translation tables can be specified for each module
    $translateProvider.translations('en', {
        'email': 'Email',
        'password': 'Password',
        'rememberMe': 'Remember me',
        'login': 'LOGIN',
        'signUp': 'SIGN UP',
        'forgotPassword': 'FORGOT PASSWORD',
        'firstName': 'First Name',
        'lastName': 'Last Name',
        'confirmPassword': 'Confirm Password',
        'submit': 'SUBMIT',
        'wrongCredentials': 'Wrong credentials',
        'userCreated': 'Account activation email has been successfully sent. Please check your email',
        'accountActivated': 'Your account has been successfully activated',
        'resetPasswordEmailSent': 'Email with reset password instructions has been successfully sent. Please check your email',
        'resetPasswordFinished': 'Your password was successfully reset. Please try to login with your new password',
        'passwordsDontMatch': "Provided passwords don't match",
        'continue': "CONTINUE"

    });

    $translateProvider.translations('fr', {
        'email': 'Email (fr)',
        'password': 'Password (fr)',
        'rememberMe': 'Remember me (fr)',
        'login': 'LOGIN (fr)',
        'signUp': 'SIGN UP (fr)',
        'forgotPassword': 'FORGOT PASSWORD (fr)',
        'firstName': 'First Name (fr)',
        'lastName': 'Last Name (fr)',
        'confirmPassword': 'Confirm Password (fr)',
        'submit': 'SUBMIT (fr)',
        'wrongCredentials': 'Wrong credentials (fr)',
        'userCreated': 'Account activation email has been successfully sent. Please check your email (fr)',
        'accountActivated': 'Your account has been successfully activated. (fr)',
        'resetPasswordEmailSent': 'Email with reset password instructions has been successfully sent. Please check your email (fr)',
        'resetPasswordFinished': 'Your password was successfully reset. Please try to login with your new password (fr)',
        'passwordsDontMatch': "Provided passwords don't match (fr)",
        'continue': "CONTINUE (fr)"
    });

    if (APP_SETTINGS.appLanguage) {
        $translateProvider.use(APP_SETTINGS.appLanguage);
    }
}

module.exports = OnConfig;