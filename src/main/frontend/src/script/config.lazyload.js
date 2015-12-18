// lazyload config

angular.module('app')
    /**
     * jQuery plugin config use ui-jq directive , config the js and css files that required
     * key: function name of the jQuery plugin
     * value: array of the css js file located
     */
    .constant('JQ_CONFIG', {})
    // oclazyload config
    .config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
        // We configure ocLazyLoad to use the lib script.js as the async loader
        'use strict';
        $ocLazyLoadProvider.config({
            debug: true,
            events: true,
            modules: [
                {
                    name:'echarts',
                    files:[
                        'http://apps.bdimg.com/libs/echarts/2.1.9/source/echarts-all.js',
                    ]
                },
                {
                    name: 'angular-dataTables',
                    files: [
                        //'/library/jquery.dataTables.min.js',
                        //'/library/angular-datatables.js',
                        '/library/datatables.bootstrap.css',
                        //'/library/angular-datatables.bootstrap.js'
                    ]
                },
                {
                    name: 'ngSanitize',
                    files: [
                        '/library/angular-sanitize.min.js'
                    ]
                },
                {
                    name: 'ngFlow',
                    files: [
                        'http://cdn.bootcss.com/ng-flow/2.6.1/ng-flow-standalone.min.js'
                    ]
                },
                {
                    name: 'flowjs',
                    files: [
                        'http://cdn.bootcss.com/flow.js/2.9.0/flow.min.js'
                    ]
                },
                {
                    name: 'cropper',
                    files: [
                        'http://cdn.bootcss.com/cropper/1.0.0/cropper.min.css',
                        'http://cdn.bootcss.com/cropper/1.0.0/cropper.min.js'
                    ]
                },
                {
                    name: 'angularMasonry',
                    files: [
                        'http://cdn.bootcss.com/jquery.imagesloaded/3.2.0/imagesloaded.pkgd.min.js',
                        'http://cdn.bootcss.com/masonry/3.3.2/masonry.pkgd.min.js',
                        '/library/angular-masonry.js'
                    ]
                },
                {
                    name: 'angularFileUpload',
                    files: [
                        'http://cdn.bootcss.com/angular-file-upload/1.1.6/angular-file-upload.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular',
                    files: [
                        'http://cdn.bootcss.com/videogular/1.3.2/videogular.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular.plugins.controls',
                    files: [
                        '/library/vg-controls.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular.plugins.buffering',
                    files: [
                        '/library/vg-buffering.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular.plugins.overlayplay',
                    files: [
                        '/library/vg-overlay-play.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular.plugins.poster',
                    files: [
                        '/library/bg-poster.min.js'
                    ]
                },
                {
                    name: 'com.2fdevs.videogular.plugins.imaads',
                    files: [
                        '/library/vg-ima-ads.min.js'
                    ]
                },
                {
                    name: 'xeditable',
                    files: [
                        'http://cdn.bootcss.com/angular-xeditable/0.1.9/js/xeditable.min.js',
                        'http://cdn.bootcss.com/angular-xeditable/0.1.9/css/xeditable.css'
                    ]
                }
            ]
        });
    }])
;
