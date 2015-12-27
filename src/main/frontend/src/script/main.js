/* Controllers */

angular.module('app')
    .factory('settings', ['$rootScope', function ($rootScope) {
        'use strict';
        // supported languages
        var settings = {
            layout: {
                pageSidebarClosed: false, // sidebar menu state
                pageBodySolid: false, // solid body color state
                pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
            }
        };

        $rootScope.settings = settings;

        return settings;
    }])
    .filter('path', function ($location) {
        'use strict';
        return function () {
            var result = $location.path().split('/');
            return result[result.length - 1];
        };
    })
    .service('dtOptions', function (DTOptionsBuilder, DTDefaultOptions) {
        'use strict';
        DTDefaultOptions.setLanguage({
            'sProcessing': '<i class="fa fa-circle-o-notch fa-spin"></i>处理中...',
            'sLengthMenu': '每页显示 _MENU_ 项',
            'sZeroRecords': '没有匹配结果',
            'sInfo': '显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项',
            'sInfoEmpty': '显示第 0 至 0 项结果，共 0 项',
            'sInfoFiltered': '(由 _MAX_ 项结果过滤)',
            'sInfoPostFix': '',
            'sSearch': '搜索:',
            'sEmptyTable': '表中数据为空',
            'sLoadingRecords': '<i class="fa fa-circle-o-notch fa-spin"></i>正在向服务器请求数据...',
            'sInfoThousands': ',',
            'oPaginate': {
                'sFirst': '<i class="fa fa-angle-double-left"></i>',
                'sPrevious': '<i class="fa fa-angle-left"></i>',
                'sNext': '<i class="fa fa-angle-right"></i>',
                'sLast': '<i class="fa fa-angle-double-right"></i>'
            },
            'oAria': {
                'sSortAscending': ': 以升序排列此列',
                'sSortDescending': ': 以降序排列此列'
            }
        });
        return DTOptionsBuilder.newOptions()
            .withOption('autoWidth', false)
            .withOption('lengthMenu', [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50])
            .withOption('deferRender', true)
            .withOption('pagingType', 'full_numbers')
            .withOption('order', [])
            .withBootstrap();
    })
    .filter('duration', function () {
        'use strict';
        return function (input) {
            var minute = Math.floor(input / (1000 * 60));
            var second = Math.floor(input / 1000) - minute * 60;
            var mesc = input - minute * 60 * 1000 - second * 1000;
            var result = String();
            for (var i = 2 - minute.toString().length; i > 0; i--) {
                result = result.concat('0');
            }
            result = result.concat(minute, ':');
            for (var j = 2 - second.toString().length; j > 0; j--) {
                result = result.concat('0');
            }
            result = result.concat(second, '.');
            for (var k = 3 - mesc.toString().length; k > 0; k--) {
                result = result.concat('0');
            }
            result = result.concat(mesc);
            return result;
        };
    })
    .value('videoStatusEnum', ['已发布', '待审核', '已归档', '待发布'])
    .filter('videoStatus', ['videoStatusEnum', function (videoStatusEnum) {
        'use strict';
        return function (input) {
            return videoStatusEnum[input];
        };
    }])
    .value('resolutionEnum', ['超清', '高清', '标清', '流畅'])
    .filter('resolution', ['resolutionEnum', function (resolutionEnum) {
        'use strict';
        return function (input) {
            return resolutionEnum[input];
        };
    }])
    .filter('frame', function () {
        'use strict';
        return function (frame) {
            var second = Math.ceil(frame / 25);
            var minute = Math.ceil(second / 60);
            minute = minute.toString();
            if (minute.length < 2) {
                minute = '0' + minute;
            }
            second = second % 60;
            second = second.toString();
            if (second.length < 2) {
                second = '0' + second;
            }
            return minute + ':' + second;
        };
    })
    .value('keyTypeEnum', ['包含字符', '完整匹配', '正则表达式'])
    .filter('keyType', ['keyTypeEnum', function (keyTypeEnum) {
        'use strict';
        return function (input) {
            return keyTypeEnum[input];
        };
    }])
    .value('goodsStatusEnum', ['已处理', '待处理', '已作废'])
    .filter('goodsStatus', function (goodsStatusEnum) {
        'use strict';
        return function (input) {
            return goodsStatusEnum[input];
        };
    })
    .controller('AppCtrl', ['$scope', '$window', 'toaster', '$location',
        function ($scope, $window, toaster, $location) {
            'use strict';
            var toasterType = ['success', 'info', 'wait', 'warning', 'error'];
            $scope.$on('serverResponsed', function (event, response) {
                var type, message, timeout = 5000;
                //noinspection JSUnresolvedVariable
                switch (response.statusCode) {
                    case 200:
                        type = 0;
                        message = '操作成功!';
                        break;
                    case 301:
                        type = 3;
                        message = '系统警告';
                        break;
                    case 300:
                        type = 4;
                        message = '操作失败';
                        timeout = null;
                }
                //noinspection JSUnresolvedVariable
                if (response.forwardUrl) {
                    //noinspection JSUnresolvedVariable
                    $location.url(response.forwardUrl).replace();
                }
                //noinspection JSUnresolvedVariable
                toaster.pop(toasterType[type], message, response.message, timeout);
            });
        }
    ])
    .controller('PageController', function () {
        'use strict';
        $('#container').css('min-height', $(window).height() - 46 - 33);
    })
    .controller('HeaderController', ['$scope', function ($scope) {
        'use strict';
        $scope.$on('$includeContentLoaded', function () {
            //Layout.initHeader(); // init header
        });
    }])
    .controller('SidebarController', ['$scope', function ($scope) {
        'use strict';
        $scope.$on('$includeContentLoaded', function () {
            //Layout.initSidebar(); // init sidebar
        });
    }])
    .controller('FooterController', ['$scope', function ($scope) {
        'use strict';
        $scope.$on('$includeContentLoaded', function () {
            //Layout.initFooter(); // init footer
        });
    }]);

