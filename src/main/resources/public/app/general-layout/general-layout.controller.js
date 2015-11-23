'use strict';

var generalLayoutModule = require('./_index');

function GeneralLayoutCtrl($scope, $window, APP_SETTINGS, globalService, $cookies) {
    $scope.currentLanguageCode = APP_SETTINGS.appLanguage.toUpperCase();

    $scope.changeLanguage = function() {
        if ($scope.currentLanguageCode == "EN") {
            $cookies.put("appLanguage", "fr");
        } else {
            $cookies.put("appLanguage", "en");
        }

        $window.location.reload();
    };
}

generalLayoutModule.controller('GeneralLayoutCtrl', GeneralLayoutCtrl);