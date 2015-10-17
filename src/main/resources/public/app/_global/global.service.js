'use strict';

module.exports = angular.module('globals', []);

function globalService($http, $q, $cookies, $window, APP_SETTINGS) {
    var service = {};  

    // service.getXsessionToken = function() {
    //     return !!$cookies.get('X-SESSION-TOKEN');
    // };

    // service.redirectBack = function() {
    //     $window.location = APP_SETTINGS.apiUrl.doxBoxRoot;
    // };

    return service;
}

module.exports.service('globalService', globalService);