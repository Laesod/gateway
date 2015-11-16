'use strict';

var gulp = require('gulp');
var replace = require('gulp-batch-replace');

gulp.task('prepareContextPrefix', function() {
    var replacementMap = [];

    replacementMap.push(["valueToBeReplaced", '/gateway']);

    gulp.src(['src/main/resources/public/templates/context-prefix.js'])
        .pipe(replace(replacementMap))
        .pipe(gulp.dest('src/main/resources/public/app/_global'));
});