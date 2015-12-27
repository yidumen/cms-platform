/**
 *
 * Created by cdm on 15/12/4.
 */
angular.module('app')
    .controller('sortController', function ($scope, $http, $uibModalInstance, dtInstance, sort, videoId) {
        'use strict';
        $scope.sort = sort;
        $scope.submit = function () {
            $http({
                method: 'PUT',
                url: '/api/video/pub/' + videoId,
                params: {sort: $scope.sort.current}
            }).success(function (data) {
                if ($scope.sort.current === $scope.sort.max) {
                    $scope.sort.max++;
                } else if ($scope.sort.current === $scope.sort.pub) {
                    $scope.sort.pub++;
                }
                $scope.sort.current++;
                $scope.$root.$broadcast('serverResponsed',data);
                dtInstance.reloadData();
                $uibModalInstance.dismiss('cancel');
            });
        };
        $scope.setSortMax = function () {
            $scope.sort.current = $scope.sort.max;
        };
        $scope.setSortNew = function () {
            $scope.sort.current = $scope.sort.pub;
        };
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
angular.module('app')
    .controller('removeController', function ($rootScope, $scope, $http, $uibModalInstance, dtInstance, videoId) {
        'use strict';
        $scope.submit = function () {
            $http.put('/api/video/delete/' + videoId).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
                dtInstance.reloadData();
                $uibModalInstance.dismiss('cancel');
            });
        };
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
angular.module('app')
    .controller('publishController', function ($scope, $http, $compile, $uibModal,
                                               videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        'use strict';
        $scope.dtInstance = {};
        $scope.dtOptions = dtOptions.withSource('/api/video/publish').withOption('createdRow', function (row) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('file', '编号').withOption('width', 80),
            DTColumnBuilder.newColumn('title', '视频标题'),
            DTColumnBuilder.newColumn('sort', '序号').withOption('width', 64),
            DTColumnBuilder.newColumn('shootTime', '拍摄时间').withOption('width', 120),
            DTColumnBuilder.newColumn('recommend', '荐').withOption('width', 46).renderWith(function (data) {
                if (data > 0) {
                    return '<i class="fa fa-check-square-o"></i>';
                } else {
                    return '<i class="fa fa-square-o"></i>';
                }
            }),
            DTColumnBuilder.newColumn('status', '状态').withOption('width', 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn('id').withTitle('操作').withOption('width', 100)
                .renderWith(function (data) {
                    return '<div class="operation"><a class="fa fa-globe margin-right-xs" title="发布" ng-click="publish(' + data + ')"></a><a title="批处理" class="fa fa-file-text-o" href="/api/video/bat/' + data + '"></a><a class="fa fa-remove fr" ng-click="remove(' + data + ')"></a></div>';
                }).notSortable()
        ];
        $scope.sort = {max: 0, pub: 0, current: 0};
        $http.get('/api/video/max/sort').then(function (data) {
            $scope.sort.max = data.data.max;
        });
        $http.get('/api/video/sort').then(function (data) {
            $scope.sort.pub = data.data.sort;
        });
        $scope.update = true;

        $scope.publish = function (id) {
            if ($scope.update) {
                $uibModal.open({
                    templateUrl: '/video/tpl/putSort.html',
                    size: 'sm',
                    controller: 'sortController',
                    windowClass: 'margin-top-10p',
                    resolve: {
                        videoId: function () {
                            return id;
                        },
                        sort: function () {
                            return $scope.sort;
                        },
                        dtInstance: function () {
                            return $scope.dtInstance;
                        }
                    }
                });
            } else {
                $http.get('/api/video/pub/' + id).then(function (data) {
                    $scope.$root.$broadcast('serverResponsed', data.data);
                    $scope.dtInstance.reloadData();
                });
            }
        };
        $scope.remove = function (id) {
            $uibModal.open({
                templateUrl: '/video/tpl/confirmRemove.html',
                size: 'sm',
                controller: 'removeController',
                windowClass: 'margin-top-10p',
                resolve: {
                    videoId: function () {
                        return id;
                    },
                    dtInstance: function () {
                        return $scope.dtInstance;
                    }
                }
            });
        };

    });