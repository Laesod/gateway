'use strict';

var config = require('../config'),
    gulp = require('gulp');
    // g = require('gulp-load-plugins')();

gulp.task('index', function() {
    gulp.src(config.views.index)
        .pipe(gulp.dest(config.dist));
});