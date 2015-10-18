'use strict';

var appSettings = {
    apiUrl: {
        defaultRedirectUrl: 'http://localhost:63769',
    },

    errorMessages: {
        authenticationFailed: 'Invalid username and/or password.',
        emptyUsername: 'Username is mandatory',
        emptyPassword: 'Password is mandatory'
    },

    supportedLanguages: [{
        description: "English",
        code: "en"
    }, {
        description: "French",
        code: "fr"
    }]
};

module.exports = appSettings;