'use strict';

var gulp = require('gulp');
var runSequence = require('run-sequence');

gulp.task('prod', ['clean', 'lib', 'styles', 'views'], function(cb) {
    cb = cb || function() {};

    global.isProd = true;

    runSequence(['browserify'], 'clean', 'gzip', cb);
});