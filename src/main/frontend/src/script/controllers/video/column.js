/**
 * Created by cdm on 15/12/13.
 */
angular.module('app')
    .controller('columnController', function ($scope, $uibModalInstance, cols, mTag, model) {
        'use strict';
        $scope.cols = cols;
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