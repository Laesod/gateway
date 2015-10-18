'use strict';

function OnConfig($urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('login');
}

module.exports = OnConfig;