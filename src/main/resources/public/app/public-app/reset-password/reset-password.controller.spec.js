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

describe('ResetPasswordCtrl', function() {
    var ctrl, scope, httpBackend, cookies, APP_SETTINGS, windowMock;

    beforeEach(module('app'));

    beforeEach(inject(function($controller, $rootScope, _APP_SETTINGS_) {
        scope = $rootScope.$new();
        APP_SETTINGS = _APP_SETTINGS_;
        windowMock = {
            location: {
                href: ""
            }
        };

        ctrl = $controller('ResetPasswordCtrl', {
            $scope: scope,
            $window: windowMock
        });
    }));
});