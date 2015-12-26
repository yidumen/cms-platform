'use strict';

var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var minifyHtml = require('gulp-minify-html');
//var minifyCss = require('gulp-minify-css');
var sass = require('gulp-sass');
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
    resource: '../webapp/resources/',
    target: '../../../target/cms-platform/'
};

//region 编译任务
//合并 压缩JS
gulp.task('build:scripts', function (cb) {
    var webappJS = path.web + 'script';
    var sourceJS = 'src/script';
    /*JS文件尽量使用公共静态库,这里只保留没有的文件*/
    //把公共库与框架页面的JS文件合并为app.js
    merge(
        gulp.src([
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/oclazyload/dist/ocLazyLoad.min.js',
            //angular-datables
            'bower_components/datatables/media/js/jquery.dataTables.min.js',
            'bower_components/angular-datatables/dist/angular-datatables.js',
            'bower_components/angular-datatables/dist/plugins/bootstrap/angular-datatables.bootstrap.js'
        ]),
        gulp.src([
                'src/script/**/*.js',
                '!src/script/controllers/*.js',
                '!src/script/layout/*.js'
            ])
            .pipe(uglify())
    )
        .pipe(concat('app.js'))
        .pipe(gulp.dest(webappJS));
    //压缩登录页面JS
    gulp.src('src/script/login.js').pipe(uglify())
        //.pipe(concat('login.js'))
        .pipe(gulp.dest(webappJS));
    //复制内部页面要按需加载的JS
    gulp.src([
            sourceJS + 'Chinese.json',
            sourceJS + '/**/controllers/**/*.js',
            sourceJS + '/**/layout/*.js',
            'bower_components/angular-masonry/angular-masonry.js'
        ])
        .pipe(gulp.dest(webappJS));

    cb();
});
gulp.task('dev:scripts', function () {
    return gulp.src([
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/oclazyload/dist/ocLazyLoad.min.js',
            'src/script/**/*.js',
            'bower_components/angular-masonry/angular-masonry.js',
            //angular-datables
            'bower_components/datatables/media/js/jquery.dataTables.min.js',
            'bower_components/angular-datatables/dist/angular-datatables.js',
            'bower_components/angular-datatables/dist/plugins/bootstrap/angular-datatables.bootstrap.js'
        ])
        .pipe(gulp.dest(path.web + 'script'));
});
gulp.task('build:css', function (cb) {
    var sourceCss = 'src/sass';
    var webappCss = path.web + 'css';

    gulp.src([
        sourceCss + '/global/components-rounded.scss',
        sourceCss + '/admin/layout/layout.scss',
        sourceCss + '/global/plugins.scss',
        sourceCss + '/admin/layout/themes/darkblue.scss',
        sourceCss + 'custom.scss'
    ]).pipe(sass({outputStyle: 'compressed'}))
        //.pipe(concat('app.css'))
        .pipe(gulp.dest(webappCss));
    cb();
});
gulp.task('dev:css', function () {
    return gulp.src([
        'src/sass/global/components-rounded.scss',
        'src/sass/admin/layout/layout.scss',
        'src/sass/global/plugins.scss',
        'src/sass/admin/layout/themes/darkblue.scss',
        'src/sass/custom.scss',
        'src/sass/admin/pages/login.scss'
    ]).pipe(sass())
        .pipe(gulp.dest('src/css'))
        .pipe(gulp.dest(path.web + 'css'));
});

// 压缩 HTML
gulp.task('dev:html', function () {
    return gulp.src('src/**/*.html')
        // Minify Any HTML
        .pipe(minifyHtml())
        // Output Files
        .pipe(gulp.dest(path.web));
});

// 洗刷刷
gulp.task('clean', function (cb) {
    return del([
        'src/css',
        path.web + '/*',
        '!' + path.web + 'WEB-INF',
        '!' + path.web + 'META-INF',
        'src/library',
        path.target + '/*',
        '!' + path.target + 'WEB-INF',
        '!' + path.target + 'META-INF'
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
            'bower_components/videogular-themes-default/videogular.css.map',
            //angular-datatables css
            'bower_components/angular-datatables/dist/plugins/bootstrap/datatables.bootstrap.css'

        ])
        //.pipe(gulp.dest('src/library'))
        .pipe(gulp.dest(path.web + 'library'));
});
// 默认任务
gulp.task('default', function (cb) {
    runSequence('clean', ['build:css', 'build:scripts', 'build:html', 'build:library'], cb);
});
gulp.task('build:dev', function (cb) {
    runSequence('clean', ['dev:css', 'dev:scripts', 'dev:html', 'build:library'], 'target:copy', cb);
});
gulp.task('target:copy', function () {
    return gulp.src(path.web + '**/*').pipe(gulp.dest(path.target));
});
//endregion
gulp.task('watch', function () {
    gulp.watch('src/**/*', function (event) {
        var filename = event.path.split('/');
        gulp.src('src/**/' + filename[filename.length - 1]).pipe(gulp.dest(path.target))
    });
});
