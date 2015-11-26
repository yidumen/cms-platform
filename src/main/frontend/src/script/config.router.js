'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }])
    .config(['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG',
        function ($stateProvider, $urlRouterProvider, JQ_CONFIG) {
            $urlRouterProvider.otherwise('/dashboard');
            $stateProvider
                .state('home', {
                    url: '/dashboard',
                    template: '<h1 class="wrapper-lg">欢迎光临</h1>'
                })
                .state('material', {
                    abstract: true,
                    url: '/material',
                    template: '<div class="fade-in-up-big" ui-view></div>'
                })
                .state('material.picture', {
                    url: '/picture',
                    templateUrl: '/material/picture.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load(['/js/controllers/material/picture.js',
                                'angularMasonry']);
                        }]
                    },
                    controller: 'material-picture'
                })
                .state('material.audio', {
                    url: '/audio',
                    templateUrl: '/material/audio.html',
                    resolve: {
                        deps: ['$ocLazyLoad',
                            function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'ngSanitize',
                                    'com.2fdevs.videogular',
                                    'com.2fdevs.videogular.plugins.controls',
                                    'com.2fdevs.videogular.plugins.buffering',
                                    'com.2fdevs.videogular.plugins.overlayplay'
                                ]).then(function () {
                                        return $ocLazyLoad.load('/js/controllers/material/audio.js');
                                    }
                                )
                            }]
                    },
                    controller: 'material-audio'
                })
                .state('material.video', {
                    url: '/video',
                    templateUrl: '/material/video.html',
                    resolve: {
                        deps: ['$ocLazyLoad',
                            function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'ngSanitize',
                                    'com.2fdevs.videogular',
                                    'com.2fdevs.videogular.plugins.controls',
                                    'com.2fdevs.videogular.plugins.buffering',
                                    'com.2fdevs.videogular.plugins.overlayplay',
                                    'com.2fdevs.videogular.plugins.imaads'
                                ]).then(function () {
                                    return $ocLazyLoad.load('/js/controllers/material/video.js');
                                })
                            }]
                    },
                    controller: 'material-video'
                })
                .state('material.page', {
                    url: '/page',
                    templateUrl: '/material/page.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([]);
                        }]
                    }
                })
                .state('material.aritcle',{
                    url:'/aritcle',
                    templateUrl:'/material/aritcle.html'
                })
                .state('wechat', {
                    url: '/wechat',
                    abstract: true,
                    template: '<div ui-view></div>'
                })
                .state('wechat.replyRule', {
                    url: '/reply-rule',
                    templateUrl: '/wechat/reply_rule.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    '/js/controllers/wechat/replyRule.js',
                                    '/js/directives/message-editor.js']);
                            }]
                    },
                    controller: 'wechat-replyRule'
                })
                .state('wechat.newsMessage', {
                    url:'/news',
                    templateUrl:'/wechat/news_manager.html'
                })
                .state('wechat.mass',{
                    url:'/mass',
                    templateUrl:'/wechat/mass_message.html'
                });
        }]);
