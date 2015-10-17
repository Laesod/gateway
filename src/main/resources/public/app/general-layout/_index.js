'use strict';

var bulk = require('bulk-require');

module.exports = angular.module('generalLayout', []);

angular.module("generalLayout").config(function($stateProvider) {
    $stateProvider
        .state('app', {
            url: '',
            abstract: true,
            templateUrl: "views/general-layout.html",
            title: ''
        });
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);