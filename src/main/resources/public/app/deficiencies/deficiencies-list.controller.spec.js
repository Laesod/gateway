'use strict';

describe('DeficienciesCtrl', function() {
    var ctrl, scope, httpBackend, cookies, windowMock;

    beforeEach(module('app'));

    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        windowMock = {
            location: {
                href: ""
            }
        };

        ctrl = $controller('DeficienciesCtrl', {
            $scope: scope,
        });
    }));
});