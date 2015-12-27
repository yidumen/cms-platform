/**
 * 蔡迪旻
 * 2015年10月21日.
 */
app.controller('material-audio', ["$sce", '$scope', '$http', function ($sce, $scope, $http) {
        $http.get('/api/material/audios').then(function (response) {
            $scope.audios = response.data.map(function (audio) {
                audio.sources = [{
                    src: $sce.trustAsResourceUrl(audio.file),
                    type: 'audio/mpeg'
                }];
                return audio;
            });
            $scope.currentSource = $scope.audios[0].sources;
        });
        $scope.API = null;
        $scope.playingIndex = 0;


        $scope.onPlayerReady = function (API) {
            $scope.API = API;
        };

        $scope.play = function (index) {
            // get prev or next item
            index == "next" ? $scope.playingIndex++ : $scope.playingIndex--;
            if ($scope.playingIndex >= $scope.audios.length) $scope.playingIndex = 0;
            if ($scope.playingIndex == -1) $scope.playingIndex = $scope.audios.length - 1;
            // play it
            $scope.setActive($scope.playingIndex);
        };

        $scope.setActive = function (index) {
            var playing = $scope.API.currentState=='play';
            $scope.API.stop();
            $scope.currentSource = $scope.audios[index].sources;
            if (playing) {
                $scope.API.play();
            }
        };

    }]);