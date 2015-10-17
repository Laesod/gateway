'use strict';

var istanbul = require('browserify-istanbul');

module.exports = function(config) {

    config.set({
        basePath: '../',
        frameworks: ['jasmine', 'browserify'],
        preprocessors: {
            'src/main/resources/public/app/_global/main.js': ['browserify']
        },
        plugins: ['karma-jasmine', 'karma-mocha-reporter', 'karma-phantomjs-launcher', 'karma-chrome-launcher', 'karma-browserify', 'karma-coverage'],
        browsers: ['PhantomJS'], // browsers: ['Chrome'], //for debugging
        reporters: ['mocha', 'coverage'],
        // singleRun: false, //for debugging
        autoWatch: true,

        browserify: {
            debug: true,
            transform: [
                'brfs',
                'bulkify',
                istanbul({
                    ignore: ['**/node_modules/**', '**/lib/**', '**/js/**/*']
                })
            ],
            bundleDelay: 300
        },

        // coverageReporter: {
        //     type: 'html',
        //     dir: './coverage/'
        // },

        coverageReporter: {
            dir: './test/coverage/',
            reporters: [{
                    type: 'text-summary'
                }, {
                    type: 'html'
                },
                // { type: 'json-summary', subdir: '.', file: 'summary.json' },
                // { type: 'lcov', subdir: 'lcov' },
                // { type: 'lcovonly', subdir: '.', file: 'lcov.info' },
                // { type: 'cobertura', subdir: '.', file: 'cobertura.xml' },
                // { type: 'teamcity', subdir: '.', file: 'teamcity.txt' },
                // { type: 'text', subdir: '.', file: 'text.txt' },
                // { type: 'json', subdir: '.', file: 'coverage.json' },
                // { type: 'clover', subdir: '.', file: 'clover.xml' }
            ]
        },

        proxies: {
            '/': 'http://localhost:9876/'
        },

        urlRoot: '/__karma__/',

        files: [
            'src/main/resources/public/build/js/lib.js',
            'src/main/resources/public/lib/angular-mocks/angular-mocks.js',
            'src/main/resources/public/app/_global/main.js',
            'src/main/resources/public/build/js/templates.js',
            'src/main/resources/public/app/**/*.spec.js'
        ]

    });

};