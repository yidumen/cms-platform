'use strict';

/* Controllers */

angular.module('app')
    .controller('AppCtrl', ['$scope', '$window', 'toaster',
        function ($scope, $window, toaster) {
            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            isIE && angular.element($window.document.body).addClass('ie');
            isSmartDevice($window) && angular.element($window.document.body).addClass('smart');

            $scope.app = {
                setting: {
                    asideFolded: false
                }
            };
            var toasterType = ['success', 'info', 'wait', 'warning', 'error'];
            $scope.$on('alterPOP', function (event, message) {
                toaster.pop(toasterType[message.event], message.title, message.text);
            });

            function isSmartDevice($window) {
                // Adapted from http://www.detectmobilebrowsers.com
                var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
            }
        }
    ])

