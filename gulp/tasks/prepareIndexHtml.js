'use strict';

var config = require('../config'),
    gulp = require('gulp'),
    g = require('gulp-load-plugins')(),
    version = new Date().getTime();

gulp.task('prepareIndexHtml', function() {
    gulp.src(config.views.index)
        .pipe(g.htmlReplace({
            css: {
                tpl: '<link type="text/css" rel="stylesheet" href="%s">',
                src: 'gateway/build/css/app.css?' + version
            },

            js: {
                tpl: '<script src="%s"></script>',
                src: 'gateway/build/js/app.js?' + version
            },

            jsLib: {
                tpl: '<script src="%s"></script>',
                src: 'gateway/build/js/lib.js?' + version
            },

            templates: {
                tpl: '<script src="%s"></script>',
                src: 'gateway/build/js/templates.js?' + version
            }
        }))
        .pipe(gulp.dest(config.root));
});