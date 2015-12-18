/**
 *
 * Created by cdm on 15/12/4.
 */
angular.module('app')
    .controller('editController', function ($scope, $http, $location, $uibModal, pathFilter) {
        'use strict';
        $http.get('/api/video/detail/' + pathFilter()).then(function (data) {
            $scope.update = false;
            $scope.model = data.data;
            $scope.mTag = data.data.tags.map(function (item) {
                return item.id;
            });
        });
        $http.get('/api/tag/tags').then(function (data) {
            $scope.cols = data.data.filter(function (item) {
                return item.type === 2;
            });
            $scope.tags = data.data.filter(function (item) {
                return item.type === 0;
            });
        });
        $scope.addTag = function (tag) {
            var index = $scope.mTag.indexOf(tag.id);
            if (index >= 0) {
                $scope.model.tags.splice(index, 1);
                $scope.mTag.splice(index, 1);
            } else {
                $scope.model.tags.push(tag);
                $scope.mTag.push(tag.id);
            }
        };
        $scope.submit = function () {
            $http.put('/api/video/update/' + $scope.update, $scope.model).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
            });
        };
        $scope.submitAndVerify = function () {
            $http.put('/api/video/updateAndVerify/' + $scope.update, $scope.model).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
            });
        };
        $scope.submitAndArchive = function () {
            $http.put('/api/video/updateAndArchive/' + $scope.update, $scope.model).then(function (data) {
                $scope.$root.$broadcast('serverResponsed', data.data);
            });
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
        $scope.showTags = function () {
            $uibModal.open({
                templateUrl: '/video/tpl/tagSelect.html',
                size: 'lg',
                controller: 'tagController',
                resolve: {
                    tags: function () {
                        return $scope.tags;
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