'use strict';

var requires = [
    'ui.router',
    'templates',
    'globals',
    'generalLayout',
    'publicApp',
    'ngCookies',
    'ngMaterial'
];

module.exports = angular.module('app', requires);

var bulk = require('bulk-require');
bulk(__dirname, ['./**/!(_index|main|*.spec).js']);