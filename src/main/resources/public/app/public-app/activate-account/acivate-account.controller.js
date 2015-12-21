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

module = require('../_index');

function ActivateAccountCtrl($scope, $rootScope, $state, globalService, $stateParams, $translate) {
    $rootScope.displayBackNavigation = true;    
    var emailVerificationToken = $stateParams.emailVerificationToken;

    if (emailVerificationToken) {
        globalService.activateUser({
            emailVerificationToken: emailVerificationToken
        }).then(function() {
            $translate("accountActivated").then(function(value) {
                globalService.displayToast({
                    messageText: value,
                    messageType: "success"
                });
            });
        });
    }

    $scope.onContinue = function() {
        $state.go("app.login");
    };
}

module.controller('ActivateAccountCtrl', ActivateAccountCtrl);
