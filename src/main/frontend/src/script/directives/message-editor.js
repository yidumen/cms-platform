/**
 * @author 蔡迪旻
 * 2015年11月01日
 */
angular.module('app')
    .directive('textEditor', function () {
        return {
            restrict: 'A',
            templateUrl: '/wechat/block/text-editor.html',
            link: function (scope, element, attrs) {

            }
        };
    })
    .directive('pictureEditor', function () {
        return {
            restrict: 'AE',
            templateUrl: '/material/block/picture-editor.html',
            link: function (scope) {
                scope.view = 0;
                scope.toView = function (view) {
                    scope.view = view;
                }
            }
        };
    })
    .directive('pictureView', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: '/material/block/picture-view.html',
            controller: ['$scope', '$http', 'ipCookie', function ($scope, $http, ipCookie) {
                $scope.selected = -1;

                var pictures;
                $scope.currentPage = 1;
                $scope.pageSize = ipCookie('material.picture.edit-size');
                if (!$scope.pageSize) {
                    $scope.pageSize = 12;
                }
                $scope.selectImage = function (index) {
                    $scope.selected = index;
                };
                $http.get('/api/material/images').then(function (response) {
                    pictures = $scope.pictures = response.data;
                    $scope.currentGroup = pictures;
                    $scope.groups = [];
                    var group;
                    pictures.forEach(function (picture) {
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
                    ipCookie('material.picture.edit-size', newValue, {expires: 9999})
                });
                $scope.submitSelected = function () {
                    //todo:图片消息后台及提交
                };
            }]
        };
    })
    .directive('imageUploader', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: '/material/block/image-uploader.html'
        }
    })
    .directive('audioEditor', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: '/material/block/audio-editor.html',
            link: function (scope) {
                scope.view = 0;
                scope.toView = function (view) {
                    scope.view = view;
                }
            }
        }
    })
    .directive('audioView', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: '/material/block/audio-view.html',
            controller: ['$scope', '$http', 'ipCookie', function ($scope, $http, ipCookie) {
                $scope.selected = -1;

                var audios;
                $scope.currentPage = 1;
                $scope.pageSize = ipCookie('material.audio.edit-size');
                if (!$scope.pageSize) {
                    $scope.pageSize = 10;
                }
                $scope.selectAudio = function (index) {
                    $scope.selected = index;
                };
                $http.get('/api/material/audios').then(function (response) {
                    audios = $scope.audios = response.data;
                    $scope.currentGroup = audios;
                    $scope.groups = [];
                    var group;
                    audios.forEach(function (audio) {
                        if (!audio.group || !audio.group.name) {
                            return;
                        }
                        for (var i = 0; i < $scope.groups.length; i++) {
                            group = $scope.groups[i];
                            if (audio.group.id === group.id) {
                                group.audios.push(audio);
                                return;
                            }
                        }
                        group = audio.group;
                        group.audios = [];
                        group.audios.push(audio);
                        $scope.groups.push(group);
                    });
                });
                $scope.currentGroupIndex = -1;
                $scope.displayGroup = function (index) {
                    $scope.currentGroupIndex = index;
                    $scope.currentPage = 1;
                    if (index < 0) {
                        $scope.currentGroup = audios;
                    } else {
                        $scope.currentGroup = $scope.groups[index].audios;
                    }
                };
                $scope.$watch('pageSize', function (newValue) {
                    ipCookie('material.audio.edit-size', newValue, {expires: 9999})
                });
                $scope.submitSelected = function () {
                    //todo:图片消息后台及提交
                };
            }]
        };
    })
    .directive('newsEditor', function () {
        return {
            restrict:'A',
            replace: true,
            templateUrl:'/material/block/news-editor.html'
        }
    })
    .directive('messageEditor', function () {
        return {
            restrict: 'AE',
            replace: true,
            templateUrl: '/wechat/block/message-editor.html',
            controller: ['$scope', function ($scope) {
                $scope.type = 0;
                $scope.switchType = function (type) {
                    $scope.type = type;
                };
            }],
            link: function (scope, element, attrs) {

            }
        }
    });