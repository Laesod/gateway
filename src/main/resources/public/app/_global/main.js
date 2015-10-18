'use strict';

require('./global.service');
require('../general-layout/_index');
require('../login/_index');

var app = require('./_index'),
    http;

angular.element(document).ready(onDocumentReady);

function onDocumentReady() {
    var injector = angular.injector(['app']);
    http = injector.get('$http');

    createApp();
}

function createApp() {
    var APP_SETTINGS = require('./constants');

    angular.module('app').constant('APP_SETTINGS', APP_SETTINGS);
    angular.module('app').config(require('./on_config'));
    angular.module('app').run(require('./on_run'));
    angular.bootstrap(document, ['app']);
}