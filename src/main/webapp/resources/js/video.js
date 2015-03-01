angular.module("video", ['ngResource', 'ngRoute', 'component'])
    .config(function ($routeProvider) {
        $routeProvider.when("/video/info", {controller: 'infoController', templateUrl: "/video/info"})
            .when("/video/create", {controller: 'createController', templateUrl: '/video/create'})
            .when('/video/publish', {controller: 'publishController', templateUrl: '/video/publish'})
            .when('/video/manager', {controller: 'managerController', templateUrl: '/video/manager'})
            //当请求“/video/edit/number”时实际上只是请求一个静态页面“/video/edit”，但这个页面加载后会读取number作为参数然后再查询数据
            .when('/video/edit/:id', {controller: 'editController', templateUrl: '/video/edit'});
    })
    .filter("duration", function () {
        return function (input) {
            var minute = Math.floor(input / (1000 * 60));
            var second = Math.floor(input / 1000) - minute * 60;
            var mesc = input - minute * 60 * 1000 - second * 1000;
            var result = new String();
            for (var i = 2 - minute.toString().length; i > 0; i--) {
                result = result.concat("0");
            }
            result = result.concat(minute, ":");
            for (var i = 2 - second.toString().length; i > 0; i--) {
                result = result.concat("0");
            }
            result = result.concat(second, ".");
            for (var i = 3 - mesc.toString().length; i > 0; i--) {
                result = result.concat("0");
            }
            result = result.concat(mesc);
            return result;
        };
    })
    .service("videoStatusEnum", function () {
        return ["已发布", "待审核", "已归档"];
    })
    .filter("videoStatus", function (videoStatusEnum) {
        return function (input) {
            return videoStatusEnum[input];
        };
    })
    .service('resolutionEnum', function () {
        return ['超清', '高清', '标清', '流畅'];
    })
    .filter('resolution', function (resolutionEnum) {
        return function (input) {
            return resolutionEnum[input];
        };
    })
    .controller("queryController", function ($scope) {
        $scope.model = {
            file: new Array(),
            duration: new Array()
        };
        $scope.requestVideo = function () {
            console.log(angular.toJson($scope.model));
        };
    })
    .controller("infoController", function ($scope, $resource, $compile, videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        $scope.dtOptions = dtOptions.withSource("/ajax/video/info").withOption("pageLength", 12).withOption("createdRow", function (row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 80),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 64),
            DTColumnBuilder.newColumn("duration", "时长").withOption("width", 100).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 120),
            DTColumnBuilder.newColumn("pubDate", "发布/更新 时间").withOption("width", 180),
            DTColumnBuilder.newColumn("recommend", "荐").withOption("width", 46).renderWith(function (data) {
                if (data > 0) {
                    return '<i class="am-icon-check-square-o"></i>';
                } else {
                    return '<i class="am-icon-square-o"></i>';
                }
            }),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="am-icon-info-circle am-margin-right" href="" title="完整信息" ng-click="showDetail(' + data.id + ')"></a><a target="_blank" href="http://yidumen.aliapp.com/video/' + row.file + '" class="am-icon-external-link am-fr"></a></div>';
            }).notSortable()
        ];
        $scope.showDetail = function (id) {
            $resource('/ajax/video/detail/:id', {id: id}).get().$promise.then(function (data) {
                $scope.video = data;
                console.log(data.exInfo);
                $('#detail-dialog').modal({
                    width: 560,
                    height: 530
                });
            })
        };
    })
    .controller("managerController", function ($scope, videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        $scope.dtOptions = dtOptions
            .withSource("/ajax/video/manager")
            .withOption("pageLength", 12);
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 80),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 64),
            DTColumnBuilder.newColumn("duration", "时长").withOption("width", 100).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn("tags", "栏目").withOption("width", 90).renderWith(function (data) {
                var result = new String();
                if (data.length > 0) {
                    for (var i = 0, max = data.length; i < max; i++) {
                        if (data[i].type === 2) {
                            if (data[i].tagname !== "") {
                                result = result.concat(data[i].tagname);
                                result = result.concat("<br>");
                            }
                        }
                    }
                    return result.trim();
                } else {
                    return '无';
                }
            }),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 120),
            DTColumnBuilder.newColumn("pubDate", "发布/更新 时间").withOption("width", 180),
            DTColumnBuilder.newColumn("recommend", "推荐度").withOption("width", 80),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="am-icon-edit am-margin-right" href="#/video/edit/' + data.id + '"></a></div>';
            }).notSortable()
        ];
    })
    .controller("editController", function ($scope, $resource, $location, pathVariable) {
        $resource("/ajax/video/detail/:id", {id: pathVariable[pathVariable.length - 1]}).get().$promise.then(function (data) {
            $scope.update = false;
            $scope.model = data;
            $scope.mTag = data.tags.map(function (item, index, array) {
                return item.id;
            })
        });
        $resource('/ajax/tag/tags').query().$promise.then(function (data) {
            $scope.cols = data.filter(function (item, index, array) {
                return item.type == 2;
            });
            $scope.tags = data.filter(function (item, index, array) {
                return item.type == 0;
            });
        });
        $scope.addTag = function (tag) {
            var index = $scope.mTag.indexOf(tag.id);
            if (index >= 0) {
                $scope.model.tags.splice(index, 1);
                $scope.mTag.splice(index, 1)
            } else {
                $scope.model.tags.push(tag);
                $scope.mTag.push(tag.id);
            }
        };
        $scope.submit = function () {
            //console.log($scope.model.sort);
            $resource("/ajax/video/update/:pubDate", {pubDate: $scope.update}).save($scope.model).$promise.then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data);
                $location.path("/video/manager").replace();
            });
        };
    })
    .controller("publishController", function ($scope, $resource, $compile, videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder, DTInstances) {
        $scope.dtOptions = dtOptions.withSource("/ajax/video/publish").withOption("pageLength", 12).withOption("createdRow", function (row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 80),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 64),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 120),
            DTColumnBuilder.newColumn("recommend", "荐").withOption("width", 46).renderWith(function (data) {
                if (data > 0) {
                    return '<i class="am-icon-check-square-o"></i>';
                } else {
                    return '<i class="am-icon-square-o"></i>';
                }
            }),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return videoStatusFilter(data);
            }),
            DTColumnBuilder.newColumn("id").withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="am-icon-globe am-margin-right-xs" title="发布" href="javascript:void(0);" ng-click="publish(' + data + ')"></a><a title="批处理" class="am-icon-file-text-o" href="/video/bat/' + data + '"></a><a href="javascript:void(0)" class="am-icon-remove am-fr" data-id="' + data + '"></a></div>';
            }).notSortable()
        ];
        DTInstances.getLast().then(function (dtInstance) {
            $scope.dtInstance = dtInstance;
        });
        $scope.publish = function (id) {
            showBusy();
            $resource("/ajax/video/pub/:id", {id: id}).get().$promise.then(function (data) {
                $scope.$parent.$broadcast('serverResponsed', data);
                $scope.dtInstance.reloadData();
                hideBusy();
            });
        };
    })
    .controller("createController", function ($scope, $resource, $location) {
        var maxSort, maxRecommend;
        $scope.model = {};
        $scope.model.tags = [];
        $resource('/ajax/tag/tags').query().$promise.then(function (data) {
            $scope.cols = data.filter(function (item, index, array) {
                var isColumn = item.type === 2;
                if (isColumn) {
                    if (item.tagname === '聊天室') {
                        $scope.model.tags.push(item);
                        $scope.mTag = [item.id];
                    }
                }
                return item.type == 2;
            });
            $scope.tags = data.filter(function (item, index, array) {
                return item.type == 0;
            });
        });
        $scope.addTag = function (tag) {
            var index = $scope.mTag.indexOf(tag.id);
            if (index >= 0) {
                $scope.model.tags.splice(index, 1);
                $scope.mTag.splice(index, 1)
            } else {
                $scope.model.tags.push(tag);
                $scope.mTag.push(tag.id);
            }
        };
        $resource("/ajax/video/max/sort").get().$promise.then(function (data) {
            maxSort = data.max;
        })
        $resource("/ajax/video/max/recommend").get().$promise.then(function (data) {
            maxRecommend = data.max;
        })
        $scope.submit = function () {
            $scope.model.shootTime = $('#shootTime').val();
            if (!$scope.model.shootTime) {
                return;
            }
            $resource("/ajax/video/create").save($scope.model).$promise.then(function () {
                $scope.$parent.$broadcast('serverResponsed', data);
                $location.path("/video/publish").replace();
            });
        }
        $scope.setSort = function () {
            $scope.model.sort = maxSort + 1;
        }
        $scope.setRecommend = function () {
            $scope.model.recommend = maxRecommend + 1;
        }
    });
