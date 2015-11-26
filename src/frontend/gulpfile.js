/**
 *  Amaze UI Starter Kit
 *
 *  Forked from Web Starter Kit
 *  Copyright 2014 Google Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 *
 */

'use strict';

var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
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
    source: '../main/webapp/',
    target: '../../target/cms-platform-0.1/'
};

// JavaScript 格式校验
gulp.task('jshint', function () {
    return gulp.src('app/js/**/*.js')
        .pipe($.jshint())
        .pipe($.jshint.reporter('jshint-stylish'));
});

//region 编译任务
//合并 压缩JS
gulp.task('build:scripts', function () {
    return $.merge(
        gulp.src(['app/script/**/*.js', '!app/script/login.js'])
            .pipe($.concat('app.js'))
            .pipe(gulp.dest(path.source + 'resources/script')),
        gulp.src('app/script/login.js')
            .pipe(gulp.dest(path.source + 'resources/script')));
});

// 拷贝相关资源
gulp.task('build:copy', function () {
    return gulp.src([
            'node_modules/angular-datatables/dist/angular-datatables.min.js',
            'node_modules/angular-datatables/node_modules/datatables/media/js/jquery.dataTables.min.js',
            'node_modules/blueimp-md5/js/md5.min.js',
            'app/script/Chinese.json'
        ]).pipe(gulp.dest(path.source + 'resources/script'));
});

// 编译 Less，添加浏览器前缀
gulp.task('build:styles', function () {
    return gulp.src(['app/less/*.less'])
        .pipe($.less())
        .pipe($.autoprefixer({browsers: AUTOPREFIXER_BROWSERS}))
        //.pipe(gulp.dest('../main/webapp/resources/css'))
        .pipe($.csso())
        //.pipe($.rename(
        //  {
        //    extname: '.min.css'
        //  }
        //))
        .pipe(gulp.dest(path.source + 'resources/css'))
        .pipe($.size({title: 'styles'}));
});

// 洗刷刷
gulp.task('build:clean', function (cb) {
    del(path.source + 'resources', {dot: false, force: true}, cb);
});

// 默认任务
gulp.task('default', function (cb) {
    runSequence('build:clean', ['build:styles', 'build:scripts', 'build:copy'], cb);
});
//endregion
