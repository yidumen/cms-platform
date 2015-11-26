$('#container').css('min-height', $(window).height() - 50 - 1 - 19.2 * 3 - 1 - 25 - 28.8);

angular.module('platform', ['video', 'goods'])
  .config(function ($routeProvider) {
    $routeProvider.otherwise({
      templateUrl: '/home'
    });
  })
  .controller('navigateCtrl', function ($scope) {
    $scope.$on('$routeChangeStart', function () {
      $('.am-dropdown').dropdown('close');
    });
  });

angular.module('component', ['datatables'])
  .service("dtOptions", function (DTOptionsBuilder, DTDefaultOptions) {
    DTDefaultOptions.setLanguageSource('/resources/script/Chinese.json');
    return DTOptionsBuilder.newOptions()
      .withOption('autoWidth', false)
      .withOption('lengthMenu', [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50])
      .withOption('deferRender', true)
      .withOption('pagingType', 'full_numbers')
      .withOption('order', []);
  })
  .filter('path', function ($location) {
    return function () {
      var result = $location.path().split('/');
      return result[result.length - 1];
    };
  })
  .directive('datepicker', function () {
    return {
      require: '?ngModel',
      link: function (scope, element, attrs, ngModel) {
        if (!ngModel) {
          return;
        }

        var updateModel = function (dateTxt) {
          scope.$apply(function () {
            // Call the internal AngularJS helper to
            // update the two way binding
            ngModel.$setViewValue(dateTxt);
          });
        };

        ngModel.$render = function () {
          // Use the AngularJS internal 'binding-specific' variable
          element.datepicker('setValue', ngModel.$viewValue || '');
          ngModel.$setViewValue(element.val());
        };
        element.datepicker().on('changeDate.datepicker.amui', function (event) {
          updateModel($(event.target).val());
        });
      }
    };
  })
  .directive('amAlterBox', function ($location) {
    return {
      scope: {},
      restrict: 'AE',
      replace: true,
      template: '<div class="am-container"></div>',
      link: function ($scope, element) {
        $scope.stateClass = ['am-alert-success ', 'am-alert-warning', 'am-alert-danger'];
        $scope.iconClass = ['am-icon-info', 'am-icon-exclamation', 'am-icon-times-circle'];
        $scope.$on('serverResponsed', function (event, response) {
          var iconClass, stateClass;
          switch (response.statusCode) {
            case '200':
              iconClass = 'am-icon-info';
              stateClass = 'am-alert-success';
              break;
            case '301':
              iconClass = 'am-icon-exclamation';
              stateClass = 'am-alert-warning';
              break;
            case '300':
              iconClass = 'am-icon-times-circle';
              stateClass = 'am-alert-danger';
              break;
          }
          var box = $('<div class="am-alert am-center" data-am-alert><button type="button" class="am-close">&times;</button></div>').addClass(stateClass)
            .append('<p><span class="am-margin-right-xs ' + iconClass + '"></span> ' + response.message + '</p>').css('max-width', '800px').hide().appendTo(element).slideDown();
          if (response.statusCode == 200) {
            setTimeout(function () {
              box.alert('close');
            }, 5000);
          }
          if (response.forwardUrl) {
            $location.url(response.forwardUrl).replace();
          }
        });
      }
    };
  })
  .directive('upload', function ($http, $rootScope) {
    return {
      link: function (scope, element, attrs) {
        element.change(function () {
          showBusy();
          var formData = new FormData();
          formData.append('file', element[0].files[0]);
          $http({
            method: 'POST',
            url: attrs.upload,
            data: formData,
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).success(function (data) {
            $rootScope.$broadcast('serverResponsed', data);
            hideBusy();
          });
        })
      }
    }
  });

function showBusy() {
  $('#modal-loading').modal({
    closeViaDimmer: false,
    width: 200
  })
}

function hideBusy() {
  $('#modal-loading').modal('close');
}

function setCookie(name, value) {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + 10 * 365);
  document.cookie = name + '=' + value + ';expires=' + exdate.toGMTString();
}
