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

module.exports = angular.module('generalLayout', []);

angular.module("generalLayout").config(function($stateProvider) {
    $stateProvider
        .state('app', {
            url: '',
            abstract: true,
            controller: 'GeneralLayoutCtrl',
            templateUrl: "views/general-layout.html",
            title: ''
        });
});

angular.module("generalLayout").controller("ToastCtrl", function($scope, $rootScope, $mdToast) {
    $scope.messageText = $rootScope.toastMessageText;

    $scope.messageType = $rootScope.toastMessageType;
});

bulk(__dirname, ['./**/!(*_index|*.spec).js']);
