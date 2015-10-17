'use strict';

var bulk = require('bulk-require');

module.exports = angular.module('login', []);

angular.module('login').config(function($stateProvider) {
    $stateProvider
        .state('app.login', {
            parent: 'app',
            url: '/login?location',
            templateUrl: 'views/login.html',
            controller: 'LoginCtrl',
            title: 'Login'
        });
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);