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

angular.module("generalLayout").controller("ToastCtrl", function($scope, $rootScope, $mdToast) {
    if (angular.isArray($rootScope.toastMessageText)) {
        $scope.isArrayOfMessages = true;
    }

    $scope.messageText = $rootScope.toastMessageText;

    $scope.messageType = $rootScope.toastMessageType;
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);