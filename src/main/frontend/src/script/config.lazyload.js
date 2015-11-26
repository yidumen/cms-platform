// lazyload config

angular.module('app')
    /**
     * jQuery plugin config use ui-jq directive , config the js and css files that required
     * key: function name of the jQuery plugin
     * value: array of the css js file located
     */
    .constant('JQ_CONFIG', {
    })
    // oclazyload config
    .config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
        // We configure ocLazyLoad to use the lib script.js as the async loader
        $ocLazyLoadProvider.config({
            debug: true,
            events: true,
            modules: [
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
                    name: 'ngGrid',
                    files: [
                        '../bower_components/ng-grid/build/ng-grid.min.js',
                        '../bower_components/ng-grid/ng-grid.min.css',
                        '../bower_components/ng-grid/ng-grid.bootstrap.css'
                    ]
                },
                {
                    name: 'ui.grid',
                    files: [
                        '../bower_components/angular-ui-grid/ui-grid.min.js',
                        '../bower_components/angular-ui-grid/ui-grid.min.css',
                        '../bower_components/angular-ui-grid/ui-grid.bootstrap.css'
                    ]
                },
                {
                    name: 'ui.select',
                    files: [
                        '../bower_components/angular-ui-select/dist/select.min.js',
                        '../bower_components/angular-ui-select/dist/select.min.css'
                    ]
                },
                {
                    name: 'angularFileUpload',
                    files: [
                        'http://cdn.bootcss.com/angular-file-upload/1.1.6/angular-file-upload.min.js'
                    ]
                },
                {
                    name: 'ui.calendar',
                    files: ['../bower_components/angular-ui-calendar/src/calendar.js']
                },
                {
                    name: 'angularBootstrapNavTree',
                    files: [
                        '../bower_components/angular-bootstrap-nav-tree/dist/abn_tree_directive.js',
                        '../bower_components/angular-bootstrap-nav-tree/dist/abn_tree.css'
                    ]
                },
                {
                    name: 'textAngular',
                    files: [
                        '../bower_components/textAngular/dist/textAngular-sanitize.min.js',
                        '../bower_components/textAngular/dist/textAngular.min.js'
                    ]
                },
                {
                    name: 'vr.directives.slider',
                    files: [
                        '../bower_components/venturocket-angular-slider/build/angular-slider.min.js',
                        '../bower_components/venturocket-angular-slider/build/angular-slider.css'
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
                },
                {
                    name: 'smart-table',
                    files: [
                        '../bower_components/angular-smart-table/dist/smart-table.min.js'
                    ]
                }
            ]
        });
    }])
;
