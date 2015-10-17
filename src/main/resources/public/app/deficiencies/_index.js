'use strict';

var bulk = require('bulk-require');

module.exports = angular.module('deficiencies', []);

angular.module('deficiencies').config(function($stateProvider) {
    $stateProvider
        .state('app.deficiencies', {
            parent: 'app',
            url: '/deficiencies-list',
            templateUrl: 'views/deficiencies-list.html',
            controller: 'DeficienciesListCtrl',
            title: 'Deficiencies List'
        });
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);