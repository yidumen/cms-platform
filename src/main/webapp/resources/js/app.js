angular.module("metroui", ['datatables', 'ngResource'])
        .run(function (DTDefaultOptions) {
            DTDefaultOptions.setLanguageSource('/resources/js/jquery/Chinese.json');
        })
        .service("dtOptions", function (DTOptionsBuilder) {
            return DTOptionsBuilder.newOptions()
                    .withOption("autoWidth", false)
                    .withOption("lengthMenu", [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50])
                    .withOption("deferRender", true)
                    .withOption("pagingType", "full_numbers")
                    .withOption("order", []);
        });
function getPathVariable() {
    var variable = window.location.pathname.split("/");
    return variable[variable.length - 1];
}
