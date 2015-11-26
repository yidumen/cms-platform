/// <reference path="../bower_components/angular/angular.js"/>
'use strict';

angular.module('app', [
        'ngCookies',
        'ngAnimate',
        'toaster',
        'ui.router',
        'ui.bootstrap',
        'ui.load',
        'ui.jq',
        'oc.lazyLoad'
    ])
    .value('keyTypeEnum', ['包含字符', '完整匹配', '正则表达式'])
    .filter('keyType', ['keyTypeEnum', function (keyTypeEnum) {
        return function (input) {
            return keyTypeEnum[input];
        }
    }]);