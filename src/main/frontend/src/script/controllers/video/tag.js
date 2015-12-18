/**
 * @author 蔡迪旻
 * 2015年12月13日
 */
angular.module('app')
.controller('tagController', function ($scope, $uibModalInstance, tags, mTag, model) {
    'use strict';
    $scope.tags = tags;
    $scope.mTag = mTag;
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    $scope.addTag = function (tag) {
        var index = $scope.mTag.indexOf(tag.id);
        if (index >= 0) {
            model.tags.splice(index, 1);
            $scope.mTag.splice(index, 1);
        } else {
            model.tags.push(tag);
            $scope.mTag.push(tag.id);
        }
    };
});