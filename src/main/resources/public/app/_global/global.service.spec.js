'use strict';

describe('Unit: globalService', function() {
    var globalService, httpBackend;
    beforeEach(module('app'));

    beforeEach(inject(function(_globalService_) {
        globalService = _globalService_;
    }));

    it('should work', function() {
        expect(true).toBeTruthy();
    });
});