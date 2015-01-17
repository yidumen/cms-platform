$(".dropdown-menu li a").click(function (event) {
    var dom = jQuery(event.currentTarget);
    var link = dom.attr("href");
    $("#container").load(link);
    jQuery(".dropdown-menu").css("display", "none");
    dom.parents(".dropdown").addClass("active");
    return false;
});
//$(".dropdown-toggle").click(function (event) {
//    jQuery(event.currentTarget).dropdown();
//});
function setCookie(name, value) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + 10 * 365);
    document.cookie = name + "=" + value + ";expires=" + exdate.toGMTString();
}
function getCookie(c_name) {
    if (document.cookie.length > 0)
    {
        var c_start = document.cookie.indexOf(c_name + "=");
        if (c_start !== -1)
        {
            c_start = c_start + c_name.length + 1;
            var c_end = document.cookie.indexOf(";", c_start);
            if (c_end === -1)
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}
function showMessage(message, detail) {
    jQuery(".alert").alert('close');
    jQuery("body").append("<div class='alert alert-success alert-dismissible center-block'><button type=’button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span></button><strong>" + message + "</strong>" + detail + "</div>")
            .slideDown();
}
function showError(detail) {
    jQuery(".alert").alert('close');
    jQuery("body").append("<div class='alert alert-danger alert-dismissible center-block'><button type=’button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span></button>" + detail + "</div>")
            .slideDown();
}

