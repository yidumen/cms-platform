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
  return $.merge(gulp.src('lib/amazeui/dist/css/amazeui.min.css').pipe(gulp.dest('../main/webapp/resources/css')),
    gulp.src([
      'lib/amazeui/dist/js/amazeui.min.js',
      'lib/angular/angular.min.*',
      'lib/angular-route/angular-route.min.*',
      'lib/angular-resource/angular-resource.min.*',
      'lib/angular-datatables/dist/angular-datatables.min.js',
      'lib/datatables/media/js/jquery.dataTables.min.js',
      'lib/jquery/dist/jquery.min.*',
      'lib/blueimp-md5/js/md5.min.js',
      'lib/echarts/build/dist/echarts-all.js',
      'app/script/Chinese.json'
    ]).pipe(gulp.dest(path.source + 'resources/script')),
    gulp.src('lib/amazeui/dist/fonts/*').pipe(gulp.dest(path.source + 'resources/fonts')))
    .pipe($.size({title: 'copy'}));
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
//region 部署到Tomcat容器目录
gulp.task('deploy:css', function () {
  return gulp.src(path.source + 'resources/css/**/*')
    .pipe(gulp.dest(path.target + 'resources/css'));
});
gulp.task('deploy:scripts', function () {
  return gulp.src(path.source + 'resources/script/**/*')
    .pipe(gulp.dest(path.target + 'resources/script'));
});
gulp.task('deploy', function () {
  runSequence(['deploy:css', 'deploy:scripts']);
});
//endregion
gulp.task('watch', function () {
  $.livereload.listen();
  gulp.watch('app/script/**/*.js', function (event) {
    if (event.path.slice(-8) === 'login.js') {
      return gulp.src(event.path).pipe(gulp.dest(path.source + 'resources/script'))
        .pipe(gulp.dest(path.target + 'resources/script'))
        .pipe($.size({showFiles: true}))
        .pipe($.livereload());
    } else {
      return gulp.src(['app/script/**/*.js', '!app/script/login.js']).pipe($.concat('app.js'))
        .pipe(gulp.dest(path.source + 'resources/script'))
        .pipe(gulp.dest(path.target + 'resources/script'))
        .pipe($.size({showFiles: true}))
        .pipe($.livereload());
    }
  });
  gulp.watch('app/less/**/*', function (event) {
    return gulp.src(event.path)
      .pipe($.less())
      .pipe($.autoprefixer({browsers: AUTOPREFIXER_BROWSERS}))
      .pipe($.csso())
      .pipe(gulp.dest(path.source + 'resources/css'))
      .pipe(gulp.dest(path.target + 'resources/css'))
      .pipe($.size({showFiles: true}))
      .pipe($.livereload());
  });
  gulp.watch(path.source + '**/*.html', function (event) {
    return gulp.src(event.path, {base: path.source})
      .pipe(gulp.dest(path.target))
      .pipe($.size({showFiles: true}))
      .pipe($.livereload());
  });
});
