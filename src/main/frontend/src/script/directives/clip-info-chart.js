/**
 * @author 蔡迪旻
 * 2015年12月09日
 */
angular.module('app')
    .directive('clipInfoChart', function (pathFilter, frameFilter) {
        'use strict';
        return {
            scope: {
                data: '=clipInfoChart'
            },
            link: function ($scope, $element) {
                $element.height($element.parents('#container').height() - 1 - 19.2 * 3 - 1 - 25 - 28.8);
                $element.width($element.parents('#container').width());
                var chart = echarts.init($element.get());
                //chart.setTheme('infographic');
                chart.showLoading();
                $scope.data.promise.then(function (data) {
                    var clips = data.sort(function (value1, value2) {
                        if (value1.start < value2.start) {
                            return -1;
                        } else if (value1.start > value2.start) {
                            return 1;
                        } else {
                            return 0;
                        }
                    });
                    var recording = clips.map(function (item) {
                        return item.recording.file;
                    });
                    /*categories存储图表纵轴的类目，所以它需要按素材名称进行分组。
                     分组的算法是：recording数组是所有剪辑所用的素材名称，只取每种素材名称的最后一个放进categories数组*/
                    var categories = [];
                    recording.forEach(function (item, index, array) {
                        if (array.lastIndexOf(item) === index) {
                            categories.push(item);
                        }
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
                            data: categories.map(function (item, index) {
                                if (item === clip.recording.file) {
                                    console.log(item, clip.recording.file)
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
                            data: categories.map(function (item, index) {
                                if (item === clip.recording.file) {
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
    });
