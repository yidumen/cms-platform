/**
 * @author 蔡迪旻
 * 2015年12月09日
 */
angular.module('app')
    .controller('clipController', function ($scope, $q, $http, pathFilter) {
        'use strict';
        $scope.clipInfos = $q.defer();
        $http.get('/api/video/clipInfo/' + pathFilter()).then(function (data) {
            $scope.clipInfos.resolve(data.data.clipInfos);
            $scope.file = data.data.file;
            $scope.clips = data.data.clipInfos;
        });
    });