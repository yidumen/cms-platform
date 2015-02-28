(function ($) {
    $.getPathVariable = function (index) {
        var variable = window.location.pathname.split("/");
        return variable[index];
    };
})(jQuery);

