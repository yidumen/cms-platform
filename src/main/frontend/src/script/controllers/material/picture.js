app.controller('material-picture', ['$scope', '$http', 'ipCookie', function ($scope, $http, ipCookie) {
    var pictures;
    $scope.currentPage = 1;
    $scope.pageSize = ipCookie('material.picture.view-size');
    if (!$scope.pageSize) {
        $scope.pageSize = 10;
    }
    $http.get('/api/material/images').then(function (response) {
        pictures = $scope.pictures = response.data;
        $scope.currentGroup = pictures;
        $scope.groups = [];
        pictures.forEach(function (picture) {
            var group;
            if (!picture.group || !picture.group.name) {
                return;
            }
            for (var i = 0; i < $scope.groups.length; i++) {
                group = $scope.groups[i];
                if (picture.group.id === group.id) {
                    group.pictures.push(picture);
                    return;
                }
            }
            group = picture.group;
            group.pictures = [];
            group.pictures.push(picture);
            $scope.groups.push(group);
        });
    });
    $scope.currentGroupIndex = -1;
    $scope.displayGroup = function (index) {
        $scope.currentGroupIndex = index;
        $scope.currentPage = 1;
        if (index < 0) {
            $scope.currentGroup = pictures;
        } else {
            $scope.currentGroup = $scope.groups[index].pictures;
        }
    };
    $scope.$watch('pageSize', function (newValue) {
        ipCookie('material.picture.view-size', newValue, {expires: 9999})
    });
}]);