angular.module("video", ['ngResource', 'ngRoute', 'component'])
  .config(function ($routeProvider) {
    $routeProvider.when("/video/info", {controller: 'infoController', templateUrl: "/video/info"})
      .when("/video/create", {controller: 'createController', templateUrl: '/video/create'})
      .when('/video/publish', {controller: 'publishController', templateUrl: '/video/publish'})
      .when('/video/manager', {controller: 'managerController', templateUrl: '/video/manager'})
      //当请求“/video/edit/number”时实际上只是请求一个静态页面“/video/edit”，但这个页面加载后会读取number作为参数然后再查询数据，其它页面类推
      .when('/video/edit/:id', {controller: 'editController', templateUrl: '/video/edit'})
      .when('/video/clipInfo/:id', {controller: 'video-clip', templateUrl: '/video/clipInfo'});
  })
  .filter("duration", function () {
    return function (input) {
      var minute = Math.floor(input / (1000 * 60));
      var second = Math.floor(input / 1000) - minute * 60;
      var mesc = input - minute * 60 * 1000 - second * 1000;
      var result = String();
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
  .filter('frame', function () {
    return function (frame) {
      var second = Math.ceil(frame / 25);
      var minute = Math.ceil(second / 60);
      minute = minute.toString();
      if (minute.length < 2) {
        minute = '0' + minute;
      }
      second = second % 60;
      second = second.toString();
      if (second.length < 2) {
        second = '0' + second;
      }
      return minute + ':' + second;
    };
  })
  .directive('clipInfoChart', function (pathFilter, frameFilter) {
    return {
      scope: {
        data: '=clipInfoChart'
      },
      link: function ($scope, $element) {
        'use strict';
        $element.height($element.parents('#container').height() - 1 - 19.2 * 3 - 1 - 25 - 28.8);
        var chart = echarts.init($element.get());
        //chart.setTheme('infographic');
        chart.showLoading();
        $scope.data.promise.then(function (data) {
          var clips = data.clips.sort(function (value1, value2) {
            if (value1.start < value2.start) {
              return -1;
            } else if (value1.start > value2.start) {
              return 1;
            } else {
              return 0;
            }
          });
          var video = data.video;
          var recording = clips.map(function (item) {
            return item.file;
          });
          /*categories存储图表纵轴的类目，所以它需要按素材名称进行分组。
           分组的算法是：recording数组是所有剪辑所用的素材名称，只取每种素材名称的最后一个放进categories数组*/
          var categories = [];
          recording.forEach(function (item, index, array) {
            if (array.lastIndexOf(item) === index) categories.push(item);
          });
          /* series存储图表数据，图表采用堆叠形式。
           * 堆叠顺序为start-end-start-end，必须增量堆叠，即每条数据必须减去上次数值
           * * */
          //1.声明图表数据的数组
          var series = [];
          //2.声明一个数组，用来存储每组数据最后的坐标值，顺序与categories一一对应
          var lastValue = categories.map(function () {
            return 0;
          });
          //3.遍历clips
          clips.forEach(function (clip) {
            var sery1 = {
              name: 'start',
              type: 'bar',
              stack: 'clip',
              clickable: false,
              tooltip: {show: false},
              itemStyle: {
                normal: {
                  color: 'rgba(0,0,0,0)'
                },
                emphasis: {
                  color: 'rgba(0,0,0,0)'
                }
              },
              data: categories.map(function (item, index, array) {
                if (item === clip.file) {
                  return clip.start - lastValue[index];
                } else {
                  return '-';
                }
              })
            };
            series.push(sery1);
            var sery2 = {
              name: 'end',
              type: 'bar',
              stack: 'clip',
              data: categories.map(function (item, index, array) {
                if (item === clip.file) {
                  lastValue[index] = clip.end;
                  return clip.end - clip.start;
                } else {
                  return '-';
                }
              })
            };
            series.push(sery2);
          });
          var max = Math.max.apply({}, lastValue);
          var options = {
            xAxis: {
              name: '时间',
              type: 'value',
              splitLine: {show: false},
              max: max,
              min: 0,
              axisLabel: {
                formatter: frameFilter
              }
            },
            yAxis: {
              name: '素材',
              type: 'category',
              data: categories
            },
            series: series
          };
          chart.setOption(options);
          chart.hideLoading();
        });
      }
    };
  })
  .controller("queryController", function ($scope) {
    $scope.model = {
      file: [],
      duration: []
    };
    $scope.requestVideo = function () {
      console.log(angular.toJson($scope.model));
    };
  })
  .controller('infoController', function ($scope, $resource, $compile, videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder, DTInstances) {
    $scope.dtOptions = dtOptions.withSource("/ajax/video/info")
      .withOption("createdRow", function (row, data, dataIndex) {
      $compile(angular.element(row).contents())($scope);
    })
      .withOption('initComplete', function () {
        DTInstances.getLast().then(function (lastDTInstance) {
          var table = lastDTInstance.DataTable;
          $scope.clearSearch = function () {
            table.columns().search('').draw();
          };
          $scope.search = function (col, input, regx) {
            table.columns().search('');
            table.columns(col).search(input, regx).draw();
          };

        });
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
        return '<div class="operation"><a class="am-icon-info-circle" href="" title="完整信息" ng-click="showDetail(' + data.id + ')"></a><a class="am-icon-film am-margin-left-xs" href="#/video/clipInfo/' + data.id + '"></a><a target="_blank" href="http://yidumen.aliapp.com/video/' + row.file + '" class="am-icon-external-link am-fr"></a></div>';
      }).notSortable()
    ];
    $scope.showDetail = function (id) {
      $resource('/ajax/video/detail/:id', {id: id}).get().$promise.then(function (data) {
        $scope.video = data;
        $('#detail-dialog').modal({
          width: 560,
          height: 530
        });
      })
    };
  })
  .controller("managerController", function ($scope, $templateCache, videoStatusFilter, durationFilter, dtOptions, DTColumnBuilder, DTInstances) {
    $scope.dtOptions = dtOptions.withSource("/ajax/video/manager")
      .withOption("createdRow", function (row, data, dataIndex) {
        $compile(angular.element(row).contents())($scope);
      })
      .withOption('initComplete', function () {
        DTInstances.getLast().then(function (lastDTInstance) {
          var table = lastDTInstance.DataTable;
          $scope.clearSearch = function () {
            table.columns().search('').draw();
          };
          $scope.search = function (col, input, regx) {
            table.columns().search('');
            table.columns(col).search(input, regx).draw();
          };

        });
      });
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
        var el = $('<div class="operation"><a class="am-icon-edit am-margin-right-xs" href="#/video/edit/' + data.id + '"></a></div>');
        if (data.status !== 2) {
          el.append('<a class="am-icon-archive am-margin-right-xs" href="javascript:;" ng-click="archive(' + data.id + ')"></a>');
        }
        return el.html();
      }).notSortable()
    ];
    $scope.archive = function (id) {
      $resource('/ajax/video/archive/:id', {id: id}).get().$promise.then(function (data) {
        $scope.$parent.$broadcast('serverResponsed', data);
        $scope.dtInstance.reloadData();
      });
    };
  })
  .controller("editController", function ($scope, $resource, $location, pathFilter) {
    $resource("/ajax/video/detail/:id", {id: pathFilter}).get().$promise.then(function (data) {
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
    var maxSort, newSort, maxRecommend;
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
    });
    $resource("/ajax/video/sort").get().$promise.then(function (data) {
      newSort = data.sort;
    });
    $resource("/ajax/video/max/recommend").get().$promise.then(function (data) {
      maxRecommend = data.max;
    })
    $scope.submit = function () {
      if (!$scope.model.shootTime) {
        return;
      }
      $resource("/ajax/video/create").save($scope.model).$promise.then(function (data) {
        $scope.$parent.$broadcast('serverResponsed', data);
        if (data.code == 0) {
          $location.path("/video/publish").replace();
        }
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
  })
  .controller('video-clip', function ($scope, $q, $resource, pathFilter) {
    $scope.clipInfo = $q.defer();
    $resource('/ajax/video/clipInfo/:id', {id: pathFilter()}).get().$promise.then(function (data) {
      $scope.clipInfo.resolve(data);
      $scope.video = data.video;
      $scope.clips = data.clips;
    });
  });
