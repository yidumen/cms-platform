/**
 *
 * Created by cdm on 15/12/4.
 */
angular.module('app')
    .controller('managerController',
        function ($scope, $compile, $templateCache, $http,
                  videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        'use strict';
        $scope.dtInstance = {};
        $scope.dtOptions = dtOptions.withSource('/api/video/manager')
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
            DTColumnBuilder.newColumn('sort', '序号').withOption('width', 64),
            DTColumnBuilder.newColumn('duration', '时长').withOption('width', 100).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn('tags', '栏目').withOption('width', 90).renderWith(function (data) {
                var result = String();
                if (data.length > 0) {
                    for (var i = 0, max = data.length; i < max; i++) {
                        if (data[i].type === 2) {
                            if (data[i].tagname !== '') {
                                result = result.concat(data[i].tagname);
                                result = result.concat('<br>');
                            }
                        }
                    }
                    return result.trim();
                } else {
                    return '无';
                }
            }),
            DTColumnBuilder.newColumn('shootTime', '拍摄时间').withOption('width', 120),
            DTColumnBuilder.newColumn('pubDate', '发布/更新 时间').withOption('width', 180),
            DTColumnBuilder.newColumn('recommend', '推荐度').withOption('width', 80),
            DTColumnBuilder.newColumn('status', '状态').withOption('width', 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle('操作').withOption('width', 100).renderWith(function (data) {
                var el = '<div class="operation"><a class="fa fa-edit margin-right-xs" ui-sref="site.video-edit({id:' + data.id + '})"></a>';
                if (data.status !== 2) {
                    el = el.concat('<a class="fa fa-archive margin-right-xs" href="javascript:;" ng-click="archive(' + data.id + ')"></a>');
                }
                el = el.concat('</div>');
                return el;
            }).notSortable()
        ];
        $scope.archive = function (id) {
            $http.get('/api/video/archive/' + id).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
                $scope.dtInstance.reloadData();
            });
        };
    });