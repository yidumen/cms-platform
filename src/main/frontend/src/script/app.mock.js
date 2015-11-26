/// <reference path="../bower_components/angular/angular.js"/>
'use strict';

angular.module('app', [
        'ipCookie',
        'ngMockE2E',
        'ngAnimate',
        'toaster',
        'ui.router',
        'ui.bootstrap',
        'ui.load',
        'ui.jq',
        'oc.lazyLoad'
    ])
    .value('keyTypeEnum', ['包含字符', '完整匹配', '正则表达式'])
    .filter('keyType', ['keyTypeEnum', function (keyTypeEnum) {
        return function (input) {
            return keyTypeEnum[input];
        }
    }])
    .run(['$httpBackend', function ($httpBackend) {
        var images = [];
        //拉beautylegmm.com NO1209 Sammi美女图片
        for (var i = 0; i <= 58; i++) {
            if (i === 28 || (i >= 35 && i <= 40)) {
                continue;
            }
            var index = '';
            if (i < 10) {
                index = index.concat('0', i);
            } else {
                index = index.concat(i);
            }
            images.push({
                id: i + 1,
                title: '[Beautyleg]No.1209 Sammi - ' + index,
                file: 'http://www.beautylegmm.com/photo/beautyleg/2015/1209/beautyleg-1209-00' + index + '.jpg',
                createDate: '2015-11-06',
                group: {
                    id: 1,
                    name: '[Beautyleg]NO.1209'
                }
            })
        }
        for (var i = 0; i < 63; i++) {
            if (i >= 47 && i <= 52) {
                continue;
            }
            var index = '';
            if (i < 10) {
                index = index.concat('0', i);
            } else {
                index = index.concat(i);
            }
            images.push({
                id: i + 58,
                title: '[Beautyleg]No.1208 Kaylar - ' + index,
                file: 'http://www.beautylegmm.com/photo/beautyleg/2015/1208/beautyleg-1208-00' + index + '.jpg',
                createDate: '2015-11-04',
                group: {
                    id: 2,
                    name: '[Beautyleg]NO.1208'
                }
            })
        }
        $httpBackend.whenGET('/api/material/images').respond(images);
        $httpBackend.whenGET('/api/material/videos').respond([]);
        var audios = [];
        for (var i = 0; i < 20; i++) {
            audios.push({
                id: i,
                title: '时尚工厂-01-' + (i + 1),
                file: 'http://yinpin.ndcnc.gov.cn/audio.do?fileId=' + (i + 163780),
                createDate: '2015-11-12',
                group: {
                    id: 3,
                    name: '时尚工厂系列之一'
                }
            })
        }
        $httpBackend.whenGET('/api/material/audios').respond(audios);
        $httpBackend.whenGET('/api/wechat/message/rules').respond([]);
        $httpBackend.whenGET(/^(?!\/api).*/).passThrough();
    }])
;