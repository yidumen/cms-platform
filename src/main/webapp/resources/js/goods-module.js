/**
 * Created by cdm on 15/2/23.
 */
angular.module("goods", ['ngResource', 'ngRoute', 'component'])
    .config(function ($routeProvider) {
        $routeProvider.when("/goods/info", {controller: "goods-info", templateUrl: "/goods/info"})
            .when("/goods/process", {controller: "processController", templateUrl: "/goods/process"})
            .when("/goods/trash", {controller: "trashController", templateUrl: "/goods/trash"});
    })
    .service("statusEnum", function () {
        return ["已处理", "待处理", "已作废"];
    })
    .filter("status", function (statusEnum) {
        return function (input) {
            return statusEnum[input];
        };
    })
    .controller("goods-info", function ($scope, $resource, statusFilter, dtOptions, DTColumnBuilder) {
        $scope.dtOptions = dtOptions
            .withSource("/ajax/goods/info")
            .withOption("pageLength", 12);
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("name", "姓名").withOption("width", 120),
            DTColumnBuilder.newColumn("phone", "电话").withOption("width", 140),
            DTColumnBuilder.newColumn("address", "地址"),
            DTColumnBuilder.newColumn("createdate", "提交时间").withOption("width", 180),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return statusFilter(data);
            })
            //DTColumnBuilder.newColumn("postNumber", "快递单号").withOption("width", 160)
        ];
    })
    .controller("processController", function ($scope, $resource, $compile, statusFilter, dtOptions, DTColumnBuilder, DTInstances) {
        $scope.dtOptions = dtOptions.withSource("/ajax/goods/unprocess").withOption("pageLength", 12).withOption("createdRow", function (row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("name", "姓名").withOption("width", 120),
            DTColumnBuilder.newColumn("phone", "电话").withOption("width", 140),
            DTColumnBuilder.newColumn("address", "地址"),
            DTColumnBuilder.newColumn("createdate", "提交时间").withOption("width", 180),
            DTColumnBuilder.newColumn(null).withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="am-icon-truck am-margin-right" href="" ng-click="process(' + data.id + ')"></a><a class="am-icon-trash-o am-fr" href="" ng-click="trash(' + data.id + ')"></a></div>';
            }).notSortable()
            //DTColumnBuilder.newColumn("postNumber", "快递单号").withOption("width", 160)
        ];
        DTInstances.getLast().then(function (dtInstance) {
            $scope.dtInstance = dtInstance;
        });
        $scope.process = function (id) {
            $resource("/ajax/goods/process/:id", {id: id}).get().$promise.then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data);
                $scope.dtInstance.reloadData();
            })
        }
        $scope.trash = function (id) {
            $resource("/ajax/goods/trash/:id", {id: id}).get().$promise.then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data);
                $scope.dtInstance.reloadData();
            })
        }
    })
    .controller("trashController", function ($scope, $resource, $compile, statusFilter, dtOptions, DTColumnBuilder, DTInstances) {
        $scope.dtOptions = dtOptions.withSource("/ajax/goods/trashed").withOption("pageLength", 12).withOption("createdRow", function (row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("name", "姓名").withOption("width", 120),
            DTColumnBuilder.newColumn("phone", "电话").withOption("width", 140),
            DTColumnBuilder.newColumn("address", "地址"),
            DTColumnBuilder.newColumn("createdate", "提交时间").withOption("width", 180),
            DTColumnBuilder.newColumn(null).withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="am-icon-recycle am-fr" href="" ng-click="recover(' + data.id + ')"></a></div>';
            }).notSortable()
            //DTColumnBuilder.newColumn("postNumber", "快递单号").withOption("width", 160)
        ];
        DTInstances.getLast().then(function (dtInstance) {
            $scope.dtInstance = dtInstance;
        });
        $scope.recover = function (id) {
            $resource("/ajax/goods/recover/:id", {id: id}).get().$promise.then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data);
                $scope.dtInstance.reloadData();
            });
        };
    })