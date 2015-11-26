/**
    * 蔡迪旻
    * 2015年10月21日.
    */
app.controller('material-video', ['$scope', '$http', 'FileUploader', '$state', function ($scope, $http, FileUploader, $state) {
    $http.get('/api/material/videos').then(function (response) {
        $scope.videos = response.data;
    });
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/material/upload/video',
        filters: [{
            name: 'videoFilter',
            fn: function (item) {
                var filename = item.name;
                var pattern = /^.+\.(ts|mp4|mpg|mpeg|ogg|3gp)$/i;
                var isVideo = pattern.test(filename);
                if (isVideo) {
                    return true;
                } else {
                    $scope.$emit('alterPOP', {
                        type: 3,
                        title: '只能上传视频',
                        text: '请点击按钮重新选择图片'
                    });
                    return false;
                }
            }
        }]
    });

    $scope.uploadImg = function () {
        uploader.uploadAll();
    };

    uploader.onAfterAddingFile = function (item) {
        if (uploader.queue.length > 1) {
            var file = item._file;
            uploader.clearQueue();
            uploader.addToQueue(file);
        }
    };

    uploader.onBeforeUploadItem = function (item) {
        item.formData.push({
            title: $scope.title
        })
    };

    uploader.onSuccessItem = function (item, response) {
        $scope.$emit('alterPOP', response);
        $state.reload();
    }
}]);