/**
 * @author 蔡迪旻
 * 2015年12月19日
 */
(function ($) {
    'use strict';
    $(document).ready(function () {
        $('#form').submit(function () {
            var passwordEl=$('#password');
            var password = passwordEl.val();
            passwordEl.val(md5(password));
        });
    });
    $.getJSON('/api/login/remember', function (data) {
        //noinspection JSUnresolvedVariable
        if (data.logined) {
            $('#form').addClass('hide');
            $('#login_user').html(data.username);
            $('#logined').removeClass('hide');
        }
    });
})(jQuery);