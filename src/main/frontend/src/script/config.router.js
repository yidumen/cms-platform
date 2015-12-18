/**
 * Config for the router
 */
angular.module('app')
    .run(['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            'use strict';
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }])
    .config(['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG',
        function ($stateProvider, $urlRouterProvider, JQ_CONFIG) {
            'use strict';
            $urlRouterProvider.otherwise('/dashboard');
            $stateProvider
                .state('home', {
                    url: '/dashboard',
                    templateUrl: '/platform/dashborad.html'
                })
                .state('site', {
                    url: '/site',
                    templateUrl: '/platform/tpl/sidebar/site.html'
                })
                .state('site.video-list', {
                    url: '/video/list',
                    templateUrl: '/video/info.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load('angular-dataTables').then(function () {
                                return $ocLazyLoad.load('/script/controllers/video/info.js');
                            });
                        }]
                    },
                    controller: 'infoController'
                })
                .state('site.video-clip', {
                    url: '/clipInfo/:id',
                    templateUrl: '/video/clip_info.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load('echarts').then(function () {
                                return $ocLazyLoad.load('/script/directives/clip-info-chart.js').then(function () {
                                    return $ocLazyLoad.load('/script/controllers/video/clip.js');
                                });
                            });
                        }]
                    },
                    controller: 'clipController'
                })
                .state('site.video-create', {
                    url: '/video/create',
                    templateUrl: '/video/create.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                '/script/controllers/video/column.js',
                                '/script/controllers/video/create.js'
                            ]);
                        }]
                    },
                    controller: 'createController'
                })
                .state('site.video-manager', {
                    url: '/video/manager',
                    templateUrl: '/video/manager.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load('ngFlow').then(function () {
                                return $ocLazyLoad.load('/script/controllers/video/manager.js');
                            });
                        }]
                    },
                    controller: 'managerController'
                })
                .state('site.video-edit', {
                    url: '/video/edit/:id',
                    templateUrl: '/video/edit.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                '/script/controllers/video/column.js',
                                '/script/controllers/video/tag.js',
                                '/script/controllers/video/edit.js'
                            ]);
                        }]
                    },
                    controller: 'editController'
                })
                .state('site.video-publish', {
                    url: '/video/publish',
                    templateUrl: '/video/publish.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                            return $ocLazyLoad.load('/script/controllers/video/publish.js');
                        }]
                    },
                    controller: 'publishController'
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
                                );
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
                                });
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
                .state('material.aritcle', {
                    url: '/aritcle',
                    templateUrl: '/material/aritcle.html'
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
                    url: '/news',
                    templateUrl: '/wechat/news_manager.html'
                })
                .state('wechat.mass', {
                    url: '/mass',
                    templateUrl: '/wechat/mass_message.html'
                });
        }]);
