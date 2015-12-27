/**
 *
 * Created by cdm on 15/12/3.
 */
angular.module('app')
    .controller('detailController', function ($scope, $uibModalInstance, video) {
        'use strict';
        $scope.video = video;
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
angular.module('app')
    .controller('infoController', function ($scope, $http, $compile, $uibModal, videoStatusFilter, durationFilter, DTColumnBuilder, dtOptions) {
        'use strict';
        $scope.dtInstance = {};
        $scope.dtOptions = dtOptions.withSource('/api/video/info')
            .withOption('createdRow', function (row) {
                $compile(angular.element(row).contents())($scope);
            })
            .withOption('initComplete', function () {
                var table = $scope.dtInstance.DataTable;
                $scope.clearSearch = function () {
                    table.columns().search('').draw();
                };
                $scope.search = function (col, input, regx) {
                    table.columns().search('');
                    table.columns(col).search(input, regx).draw();
                };

            });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('file', '编号').withOption('width', 80),
            DTColumnBuilder.newColumn('title', '视频标题'),
            DTColumnBuilder.newColumn('sort', '序号').withOption('width', 68).renderWith(function (data) {
                return data > 0 ? data : '-';
            }),
            DTColumnBuilder.newColumn('duration', '时长').withOption('width', 100).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn('shootTime', '拍摄时间').withOption('width', 120),
            DTColumnBuilder.newColumn('pubDate', '发布/更新 时间').withOption('width', 180),
            DTColumnBuilder.newColumn('recommend', '荐').withOption('width', 46).renderWith(function (data) {
                if (data) {
                    return '<i class="fa fa-check-square-o"></i>';
                } else {
                    return '<i class="fa fa-square-o"></i>';
                }
            }),
            DTColumnBuilder.newColumn('status', '状态').withOption('width', 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle('操作').withOption('width', 100).renderWith(function (data, type, row) {
                return '<div class="operation"><a class="fa fa-info-circle" href="" title="完整信息" ng-click="showDetail(' + data.id + ')"></a><a class="fa fa-film margin-left-xs" ui-sref="site.video-clip({id:' + data.id + '})"></a><a target="_blank" href="http://www.yidumen.com/video/' + row.file + '" class="fa fa-external-link fr"></a></div>';
            }).notSortable()
        ];
        $scope.showDetail = function (id) {
            $http.get('/api/video/detail/' + id).then(function (data) {
                $uibModal.open({
                    templateUrl: '/video/tpl/detail.html',
                    size: 'lg',
                    controller: 'detailController',
                    windowClass: 'margin-top-10p',
                    resolve: {
                        video: function () {
                            return data.data;
                        }
                    }
                });
            });
        };
    });
