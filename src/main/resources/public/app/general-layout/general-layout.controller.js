'use strict';

var generalLayoutModule = require('./_index');

function GeneralLayoutCtrl($scope, $rootScope, $window, APP_SETTINGS, globalService, $cookies, $state) {
    $scope.currentLanguageCode = APP_SETTINGS.appLanguage.toUpperCase();
	$rootScope.displayBackNavigation = false;

    $scope.changeLanguage = function() {
        if ($scope.currentLanguageCode == "EN") {
            $cookies.put("appLanguage", "fr");
        } else {
            $cookies.put("appLanguage", "en");
        }

        $window.location.reload();
    };

    $scope.backToLogin = function(){
    	$state.go("app.login");
    };
}

generalLayoutModule.controller('GeneralLayoutCtrl', GeneralLayoutCtrl);