'use strict';

module = require('./_index');

function DeficienciesListCtrl($scope, $timeout, $state, $stateParams, $http, $window, $cookies, APP_SETTINGS) {
    $http.get('user', {}).success(function(response) {
        $scope.authValues = response;
    });

    $scope.onLogOut = function() {
        $http.post('logout', {}).success(function() {
            $state.go("app.login");
        }).error(function(data) {});
    };
}
module.controller('DeficienciesListCtrl', DeficienciesListCtrl);