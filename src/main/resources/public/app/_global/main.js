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

    require('./global.service');
    require('../general-layout/_index');
    require('../public-app/_index');

    var app = require('./_index'),
        http, APP_SETTINGS;

    var constants = require('./constants');
    var contextParams = require('./context-params');

    angular.element(document).ready(onDocumentReady);

    function onDocumentReady() {
        var injector = angular.injector(['app']);
        http = injector.get('$http');

    http.get(contextParams.configServicePrefix() + 'gateway/gateway/config?' + new Date().getTime()).
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