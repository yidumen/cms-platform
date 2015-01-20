$("#pub-shoot").datetimepicker({
    language: "zh-CN",
    format: "yyyy-mm-dd",
    weekStart: 1,
    autoclose: true,
    startView: 2,
    minView: 2,
    todayBtn: "linked",
    todayHighlight: true,
    forceParse: false
});
$("form").submit(function () {
    $.ajax({
        data: $(this).serialize(),
        type: "post",
        url: $(this).attr('action'),
        success: function () {
            $("#container").load("/video/publish");
        }
    });
    return false;
});