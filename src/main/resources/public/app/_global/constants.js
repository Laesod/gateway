'use strict';

var appSettings = {
    apiUrl: {
        defaultRedirect: 'http://localhost:9000',
    },

    errorMessages: {
        authenticationFailed: 'Invalid username and/or password.',
        emptyUsername: 'Username is mandatory',
        emptyPassword: 'Password is mandatory'
    }
};

module.exports = appSettings;