'use strict';

require('./global.service');
require('../general-layout/_index');
require('../public-app/_index');

var app = require('./_index'),
    http;

angular.element(document).ready(onDocumentReady);

function onDocumentReady() {
    var injector = angular.injector(['app']);
    http = injector.get('$http');

    createApp();
}

function createApp() {
    var constants = require('./constants');
    var APP_SETTINGS = constants();

    angular.module('app').constant('APP_SETTINGS', APP_SETTINGS);
    angular.module('app').config(require('./on_config'));
    angular.module('app').run(require('./on_run'));
    angular.bootstrap(document, ['app']);
}