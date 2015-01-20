$(".form-signin").submit(function (event) {
    var passDom = $(this).children("#password");
    var password = hex_md5(passDom.val());
    passDom.val(password);
    if ($(this).children("div.checkbox").children("label").children("#autologin").prop("checked")) {
        setCookie("username", encodeURIComponent($(this).children("#username").val()));
        setCookie("password", password);
    }
});

