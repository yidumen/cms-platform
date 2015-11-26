'use strict';

var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var minifyHtml = require('gulp-minify-html');
var minifyCss = require('gulp-minify-css');
var less = require('gulp-less');
var merge = require('merge2');
var del = require('del');
var runSequence = require('run-sequence');

var AUTOPREFIXER_BROWSERS = [
    'ie >= 10',
    'ie_mob >= 10',
    'ff >= 30',
    'chrome >= 34',
    'safari >= 7',
    'opera >= 23',
    'ios >= 7',
    'android >= 4.4',
    'bb >= 10'
];

var path = {
    web: '../webapp/',
    resource: '../webapp/resources/'
};

//region 编译任务
//合并 压缩JS
gulp.task('build:scripts', function (cb) {
    var webappJS = path.resource + 'js';
    var sourceJS = 'html/resources/js';
    merge(
        gulp.src([
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/oclazyload/dist/ocLazyLoad.min.js'
        ]),
        gulp.src([
                'js/**/*.js',
                '!js/controllers/*.js',
                '!js/app.mock.js'
            ])
            .pipe(uglify())
    )
        .pipe(concat('app.js'))
        .pipe(gulp.dest(sourceJS))
        .pipe(gulp.dest(webappJS));

    gulp.src('script/login.js').pipe(uglify())
        .pipe(concat('login.js'))
        .pipe(gulp.dest(sourceJS))
        .pipe(gulp.dest(webappJS));

    gulp.src([
            'script/Chinese.json',
            'js/**/controllers/**/*.js',

            'bower_components/angular-masonry/angular-masonry.js'
        ])
        .pipe(gulp.dest(sourceJS))
        .pipe(gulp.dest(webappJS));

    cb();
});

gulp.task('build:css', function (cb) {
    var sourceFont = 'html/resources/fonts';
    var webappFont = path.resource + 'fonts';
    var sourceCss = 'html/resources/css';
    var webappCss = path.resource + 'css';
    gulp.src('fonts/**/*')
        .pipe(gulp.dest(sourceFont))
        .pipe(gulp.dest(webappFont));

    gulp.src('css/less/app.less').pipe(less()).pipe(minifyCss())
        .pipe(concat('app.css'))
        .pipe(gulp.dest(sourceCss))
        .pipe(gulp.dest(webappCss));
    cb();
});

// 压缩 HTML
gulp.task('build:html', function () {
    return gulp.src('html/**/*.html')
        // Minify Any HTML
        .pipe(minifyHtml())
        // Output Files
        .pipe(gulp.dest(path.web));
});

// 洗刷刷
gulp.task('clean', function (cb) {
    return del([
        path.web + '/*',
        '!' + path.web + 'WEB-INF',
        'src/library',
        'src/.temp'
    ], {dot: false, force: true}, cb);
});
gulp.task('build:library', function () {
    return gulp.src([
        //angularjs
        'bower_components/angular-sanitize/angular-sanitize.min.js',
        'bower_components/angular-masonry/angular-masonry.js',
        'bower_components/angular-xeditable/dist/js/xeditable.min.js',
        'bower_components/angular-xeditable/dist/css/xeditable.css',
        'bower_components/ng-flow/dist/ng-flow-standalone.min.js',
        //videogular
        'bower_components/videogular-angulartics/vg-analytics.min.js',
        'bower_components/videogular-buffering/vg-buffering.min.js',
        'bower_components/videogular-controls/vg-controls.min.js',
        'bower_components/videogular-ima-ads/vg-ima-ads.min.js',
        'bower_components/videogular-overlay-play/vg-overlay-play.min.js',
        'bower_components/videogular-poster/vg-poster.min.js',
        'bower_components/videogular-themes-default/videogular.min.css',
        'bower_components/videogular-themes-default/videogular.css.map'
    ]).pipe(gulp.dest('src/library'));
});
// 默认任务
gulp.task('default', function (cb) {
    runSequence('build:clean', ['build:css', 'build:scripts', 'build:html'], cb);
});
//endregion
gulp.task('preprocess:mock', ['clean'], function (cb) {
    gulp.src([
            'bower_components/oclazyload/dist/ocLazyLoad.js',
            'bower_components/angular-mocks/angular-mocks.js',
            'bower_components/angular-animate/angular-animate.js',
            'node_modules/less/dist/less.js'
        ])
        .pipe(gulp.dest('src/.temp'));
    gulp.src([
        //angularjs
        'bower_components/angular-sanitize/angular-sanitize.min.js',
        'bower_components/angular-masonry/angular-masonry.js',
        'bower_components/angular-xeditable/dist/js/xeditable.min.js',
        'bower_components/angular-xeditable/dist/css/xeditable.css',
        'bower_components/ng-flow/dist/ng-flow-standalone.min.js',
        //videogular
        'bower_components/videogular-angulartics/vg-analytics.min.js',
        'bower_components/videogular-buffering/vg-buffering.min.js',
        'bower_components/videogular-controls/vg-controls.min.js',
        'bower_components/videogular-ima-ads/vg-ima-ads.min.js',
        'bower_components/videogular-overlay-play/vg-overlay-play.min.js',
        'bower_components/videogular-poster/vg-poster.min.js',
        'bower_components/videogular-themes-default/videogular.min.css',
        'bower_components/videogular-themes-default/videogular.css.map',
        'bower_components/videogular-themes-default/**/fonts/**/*'
    ]).pipe(gulp.dest('src/library'));
    cb();
});
