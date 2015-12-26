/**
 * @author 蔡迪旻
 * 2015年12月13日
 */
angular.module('app')
    .controller('processController', function ($scope, $http, $compile, goodsStatusFilter, dtOptions, DTColumnBuilder) {
        'use strict';
        $scope.dtInstance = {};
        $scope.dtOptions = dtOptions.withSource('/api/goods/unprocess').withOption('pageLength', 12).withOption('createdRow', function (row) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('name', '姓名').withOption('width', 120),
            DTColumnBuilder.newColumn('phone', '电话').withOption('width', 140),
            DTColumnBuilder.newColumn('address', '地址'),
            DTColumnBuilder.newColumn('createdate', '提交时间').withOption('width', 180),
            DTColumnBuilder.newColumn(null).withTitle('操作').withOption('width', 100).renderWith(function (data) {
                return '<div class="operation"><a class="fa fa-truck margin-right" ng-click="process(' + data.id + ')"></a><a class="fa fa-trash-o fr" href="" ng-click="trash(' + data.id + ')"></a></div>';
            }).notSortable()
            //DTColumnBuilder.newColumn("postNumber", "快递单号").withOption("width", 160)
        ];
        $scope.process = function (id) {
            $http.put('/api/goods/process/' + id).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
                $scope.dtInstance.reloadData();
            });
        };
        $scope.trash = function (id) {
            $http.put('/api/goods/trash/' + id).then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data.data);
                $scope.dtInstance.reloadData();
            });
        };
    });