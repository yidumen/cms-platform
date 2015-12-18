
angular.module('app')
    .controller('createController', function ($scope, $http, $uibModal) {
        'use strict';
        var maxSort, newSort, maxRecommend;
        $scope.model = {};
        $scope.model.tags = [];
        $http.get('/api/tag/tags').then(function (data) {
            $scope.cols = data.data.filter(function (item) {
                var isColumn = item.type === 2;
                if (isColumn) {
                    if (item.tagname === '聊天室') {
                        $scope.model.tags.push(item);
                        $scope.mTag = [item.id];
                    }
                }
                return item.type == 2;
            });
            $scope.tags = data.data.filter(function (item) {
                return item.type === 0;
            });
        });

        $http.get('/api/video/max/sort').then(function (data) {
            maxSort = data.data.max;
        });
        $http.get('/api/video/sort').then(function (data) {
            newSort = data.data.sort;
        });
        $http.get('/api/video/max/recommend').then(function (data) {
            maxRecommend = data.data.max;
        });
        $scope.submit = function () {
            if (!$scope.model.shootTime) {
                return;
            }
            $http.post('/api/video/create', $scope.model).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
            });
        };
        $scope.setSortMax = function () {
            $scope.model.sort = maxSort + 1;
        };
        $scope.setSortNew = function () {
            $scope.model.sort = newSort + 1;
        };
        $scope.setRecommend = function () {
            $scope.model.recommend = maxRecommend + 1;
        };
        $scope.isOpen = false;
        $scope.open = function () {
            $scope.isOpen = true;
        };
        $scope.showColumns = function () {
            $uibModal.open({
                templateUrl: '/video/tpl/columnSelect.html',
                size: 'sm',
                controller: 'columnController',
                windowClass: 'margin-top-10p',
                resolve: {
                    cols: function () {
                        return $scope.cols;
                    },
                    mTag: function () {
                        return $scope.mTag;
                    },
                    model: function () {
                        return $scope.model;
                    }
                }
            });
        };
    });
