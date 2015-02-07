angular.module("video", ['metroui'])
    .directive("detailDialog", function (statusFilter, durationFilter) {
        return {
            link: function (scope, element, attrs) {
                $(attrs.dlgNode).on("click", attrs.dlgTarget, function () {
                    var id = $(this).data("id");
                    $.ajax({
                        url: "/ajax/video/detail/" + id,
                        type: 'GET',
                        dataType: 'json',
                        success: function (data) {
                            $("#itemId").html(data.id);
                            $("#file").html(data.file);
                            $("#dur").html(durationFilter(data.duration));
                            $("#sort").html(data.sort);
                            $("#grade").html(data.grade);
                            $("#recommend").html(function () {
                                return data.recommend > 0 ? data.recommend : "不推荐";
                            });
                            $("#shoot").html(data.shootTime);
                            $("#status").html(statusFilter(data.status));
                            $("#pub").html(data.pubDate);
                            var tags = new Array(), cols = new Array();
                            for (var i = 0, max = data.tags.length; i < max; i++) {
                                if (data.tags[i].type === 2) {
                                    cols.push(data.tags[i].tagname);
                                } else if (data.tags[i].type === 0) {
                                    tags.push(data.tags[i].tagname);
                                }
                            }
                            $("#col").html(function () {
                                var content = new String();
                                for (var i = 0, max = cols.length; i < max; i++) {
                                    content = content.concat('<div class="label bg-darkOrange fg-white">', cols[i], '</div>');
                                }
                                return content;
                            });
                            $("#tag").html(function () {
                                var content = new String();
                                for (var i = 0, max = tags.length; i < max; i++) {
                                    content = content.concat('<div class="label bg-cyan fg-white">', tags[i], '</div>');
                                }
                                return content;
                            });
                            $("#desc").html(function () {
                                if (data.descrpition && data.descrpition.length > 0) {
                                    return data.descrpition;
                                } else {
                                    return "无描述信息";
                                }
                            });
                            $("#note").html(function () {
                                if (data.note && data.note.length > 0) {
                                    return data.note;
                                } else {
                                    return "无备注信息";
                                }
                            });
                            $("#ext").html(function () {
                                var content = new String();
                                for (var i = 0, max = data.extInfo.length; i < max; i++) {
                                    content = content.concat("<tr><td>", data.extInfo[i].resolution, "</td>",
                                        "<td>", data.extInfo[i].fileSize, "</td></tr>");
                                }
                                return content;
                            });
                            $.Dialog({
                                icon: '<i class="icon-film"></i>',
                                title: "<strong>" + data.title + "</strong>",
                                content: element.html(),
                                flat: true,
                                shadow: true,
                                padding: 20
                            });
                        }
                    })
                });
            }
        };
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
    .filter("recommend", function () {
        return function (input) {
            if (input > 0) {
                return 'icon-checkbox';
            } else {
                return 'icon-checkbox-unchecked';
            }
        };
    })
    .service("statusEnum", function () {
        return ["已发布", "待审核", "已归档"];
    })
    .filter("status", function (statusEnum) {
        return function (input) {
            return statusEnum[input];
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
    .controller("infoController", function ($scope, statusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        $scope.dtOptions = dtOptions.withSource("/ajax/video/info").withOption("pageLength", 14);
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 70),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 70),
            DTColumnBuilder.newColumn("duration", "时长").withOption("width", 80).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 100),
            DTColumnBuilder.newColumn("pubDate", "发布/更新 时间").withOption("width", 156),
            DTColumnBuilder.newColumn("recommend", "荐").withOption("width", 56).renderWith(function (data) {
                if (data > 0) {
                    return '<i class="icon-checkbox"></i>';
                } else {
                    return '<i class="icon-checkbox-unchecked"></i>';
                }
            }),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return statusFilter(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="icon-info" title="完整信息" href="javascript:void(0);" data-id="' + data.id + '"></a>&nbsp;&nbsp;<a target="_blank" href="http://yidumen.aliapp.com/video/' + row.file + '" class="icon-new-tab-2 place-right"></a></div>';
            })
        ];
    })
    .controller("managerController", function ($scope, statusFilter, durationFilter, dtOptions, DTColumnBuilder) {
        $('#edit-panel').hide();
        $scope.dtOptions = dtOptions
            .withSource("/ajax/video/manager")
            .withOption("pageLength", 14)
            .withOption('rowCallback', function (row, data) {
                $('#edit-panel').hide();
                $('td', row).unbind('click');
                $('td', row).bind('click', function () {
                    $scope.$apply(function () {
                        if ($(row).hasClass('info')) {
                            $(row).removeClass('info');
                            $('#edit-panel').fadeOut();
                        }
                        else {
                            $('tr.info').removeClass("info");
                            $(row).addClass('info');
                            $scope.videoId = data.id;
                            var node = $(row).position();
                            $('#edit-panel').animate({top: (node.top - 81) + "px"}).show();
                        }
                    });
                });
                return row;

            });
        ;
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 70),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 70),
            DTColumnBuilder.newColumn("duration", "时长").withOption("width", 80).renderWith(function (data) {
                return durationFilter(data);
            }),
            DTColumnBuilder.newColumn("tags", "栏目").withOption("width", 80).renderWith(function (data) {
                var result = new String();
                if (data.length > 0) {
                    for (var i = 0, max = data.length; i < max; i++) {
                        if (data[i].type === 2) {
                            if (data[i].tagname !== "") {
                                result = result.concat(" ");
                                result = result.concat(data[i].tagname);
                            }
                        }
                    }
                    return result.trim();
                } else {
                    return '无';
                }
            }),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 100),
            DTColumnBuilder.newColumn("pubDate", "发布/更新 时间").withOption("width", 156),
            DTColumnBuilder.newColumn("recommend", "推荐度").withOption("width", 80),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return statusFilter(data);
            })
        ];
    })
    .controller("editController", function ($scope, $resource) {
        $resource("/ajax/video/detail/:id", {id: getPathVariable()}).get().$promise.then(function (data) {
            $scope.model = data;
            console.log($scope.model.tags)
        });
    })
    .controller("publishController", function ($scope, $resource, $compile, statusFilter, durationFilter, dtOptions, DTColumnBuilder, DTInstances) {
        $scope.dtOptions = dtOptions.withSource("/ajax/video/publish").withOption("pageLength", 13).withOption("createdRow", function (row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        });
        $scope.dtColumns = [
            DTColumnBuilder.newColumn("file", "编号").withOption("width", 70),
            DTColumnBuilder.newColumn("title", "视频标题"),
            DTColumnBuilder.newColumn("sort", "序号").withOption("width", 70),
            DTColumnBuilder.newColumn("shootTime", "拍摄时间").withOption("width", 100),
            DTColumnBuilder.newColumn("recommend", "荐").withOption("width", 56).renderWith(function (data) {
                if (data > 0) {
                    return '<i class="icon-checkbox"></i>';
                } else {
                    return '<i class="icon-checkbox-unchecked"></i>';
                }
            }),
            DTColumnBuilder.newColumn("status", "状态").withOption("width", 70).renderWith(function (data) {
                return statusFilter(data);
            }),
            DTColumnBuilder.newColumn("id").withTitle("操作").withOption("width", 100).renderWith(function (data, type, row, meta) {
                return '<div class="operation"><a class="icon-globe on-left-more" title="发布" href="javascript:void(0);" ng-click="publish(' + data + ')"></a><a title="批处理" class="icon-new" href="/video/bat/' + data + '"></a><a href="javascript:void(0)" class="icon-remove place-right" data-id="' + data + '"></a></div>';
            })
        ];
        DTInstances.getLast().then(function (dtInstance) {
            $scope.dtInstance = dtInstance;
        });
        $scope.publish = function (id) {
            $resource("/ajax/video/pub/:id", {id: id}).get().$promise.then(function (data) {
                if (data.code == 0) {
                    $.Notify({
                        caption: "成功",
                        content: "视频已成功发布",
                        style: {background: "blue", color: "white"},
                        timeout: 5000
                    });
                    $scope.dtInstance.reloadData();
                } else {
                    $.Notify({
                        caption: "失败",
                        content: data.message,
                        style: {background: "red", color: "white"},
                        timeout: 10000
                    });
                }
            });
        };
    });

