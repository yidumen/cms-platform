angular.module("platform", ['video', 'goods'])
    .controller('navigateCtrl', function ($scope) {
        $scope.$on('$routeChangeStart', function () {
            $('.am-dropdown').dropdown('close');
        });
    });
angular.module('component', ['datatables'])
    .service("dtOptions", function (DTOptionsBuilder, DTDefaultOptions) {
        DTDefaultOptions.setLanguageSource('/resources/js/jquery/Chinese.json');
        return DTOptionsBuilder.newOptions()
            .withOption("autoWidth", false)
            .withOption("lengthMenu", [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50])
            .withOption("deferRender", true)
            .withOption("pagingType", "full_numbers")
            .withOption("order", []);
    })
    .service('pathVariable', function ($location) {
        var result = $location.path().split("/");
        return result;
    })
    .directive("datepicker", function () {
        return {
            require: "?ngModel",
            link: function (scope, element, attrs, ngModel) {
                if (!ngModel) return;

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
                };
                element.datepicker().on('changeDate.datepicker.amui', function (event) {
                    updateModel($(event.target).val());
                });
            }
        };
    })
    .directive('amAlterBox', function () {
        var directiveDefinitionObject = {
            scope: {},
            restrict: 'AE',
            replace: true,
            template: '<div class="am-container"></div>',
            link: function ($scope, element, attrs) {
                $scope.stateClass = ['am-alert-success ', 'am-alert-warning', 'am-alert-danger'];
                $scope.iconClass = ['am-icon-info', 'am-icon-exclamation', 'am-icon-times-circle'];
                $scope.$on('serverResponsed', function (event, response) {
                    var box = $('<div class="am-alert am-center" data-am-alert><button type="button" class="am-close">&times;</button></div>').addClass($scope.stateClass[response.code])
                        .append('<p class="' + $scope.iconClass[response.code] + '">' + response.message + '</p>').css('max-width', '500px').width(response.message.length * 24 + 34).hide().appendTo(element).slideDown();
                    if (response.code == 0) {
                        setTimeout(function () {
                            box.alert('close');
                        }, 5000);
                    }
                });
            }
        };
        return directiveDefinitionObject;
    });
function setCookie(name, value) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + 10 * 365);
    document.cookie = name + "=" + value + ";expires=" + exdate.toGMTString();
}
