/**
 * @author 蔡迪旻
 * 2015年12月19日
 */
angular.module('app')
    .directive('uiContainer', ['$window', function ($window) {
        'use strict';
        return {
            link: function (scope, element) {
                element.css('min-height', $window.height - 46 - 33);
                $window.addEventListener('resize', function () {
                    element.css('min-height', $window.height - 46 - 33);
                });
            }
        };
    }]);