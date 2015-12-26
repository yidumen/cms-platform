/**
 * @author 蔡迪旻
 * 2015年12月13日
 */
angular.module('app')
    .controller('infoController', function ($scope, goodsStatusFilter, dtOptions, DTColumnBuilder) {
        'use strict';
        $scope.dtOptions = dtOptions
            .withSource('/api/goods/info')
            .withOption('pageLength', 12);
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('name', '姓名').withOption('width', 120),
            DTColumnBuilder.newColumn('phone', '电话').withOption('width', 140),
            DTColumnBuilder.newColumn('address', '地址'),
            DTColumnBuilder.newColumn('createdate', '提交时间').withOption('width', 180),
            DTColumnBuilder.newColumn('status', '状态').withOption('width', 70).renderWith(function (data) {
                return goodsStatusFilter(data);
            })
            //DTColumnBuilder.newColumn("postNumber", "快递单号").withOption("width", 160)
        ];
    });