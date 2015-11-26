(function ($) {
  'use strict';
  var setCookie = function (name, value) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + 10 * 365);
    document.cookie = name + '=' + value + ';expires=' + exdate.toGMTString();
  }
  $('form').submit(function () {
    var passDom = $('#password');
    var password = md5(passDom.val());
    passDom.val(password);
    if ($('#autologin').prop('checked')) {
      setCookie('username', encodeURIComponent($(this).children('#username').val()));
      setCookie('password', password);
    }
  });
})(jQuery);

