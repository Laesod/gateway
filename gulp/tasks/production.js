'use strict';

var gulp = require('gulp');
var runSequence = require('run-sequence');

gulp.task('prod', ['clean', 'prepareContextParams', 'lib', 'styles', 'views', 'prepareIndexHtml'], function(cb) {
    cb = cb || function() {};

    global.isProd = true;

    runSequence(['browserify'], 'clean', 'gzip', cb);
});