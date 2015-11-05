'use strict';

require('./global.service');
require('../general-layout/_index');
require('../public-app/_index');

var app = require('./_index'),
    http, APP_SETTINGS;

var constants = require('./constants');

angular.element(document).ready(onDocumentReady);

function onDocumentReady() {
    var injector = angular.injector(['app']);
    http = injector.get('$http');

    http.get('gateway/config?' + new Date().getTime()).
    success(function(response) {
        APP_SETTINGS = constants(response);
        createApp();
    });
}

function createApp() {
    angular.module('app').constant('APP_SETTINGS', APP_SETTINGS);
    angular.module('app').config(require('./on_config'));
    angular.module('app').run(require('./on_run'));
    angular.bootstrap(document, ['app']);
}