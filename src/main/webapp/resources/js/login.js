$("form").submit(function () {
    var passDom = $("#password");
    var password = hex_md5(passDom.val());
    passDom.val(password);
    if ($("#autologin").prop("checked")) {
        setCookie("username", encodeURIComponent($(this).children("#username").val()));
        setCookie("password", password);
    }
});
function setCookie(name, value) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + 10 * 365);
    document.cookie = name + "=" + value + ";expires=" + exdate.toGMTString();
}

