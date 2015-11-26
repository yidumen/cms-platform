app.controller('wechat-replyRule', ['$scope', '$http', function ($scope, $http) {
    $http.get('/api/wechat/message/rules').then(function (response) {
        $scope.rules = response.data;
    })
}]);