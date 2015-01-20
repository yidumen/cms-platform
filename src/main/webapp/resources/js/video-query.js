$(".date-shoot").datetimepicker({
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
$(".date-pub").datetimepicker({
    language: "zh-CN",
    format: "yyyy-mm-dd hh:ii:ss",
    weekStart: 1,
    autoclose: true,
    startView: 2,
    minView: 0,
    todayHighlight: true,
    forceParse: false
});
$("button[type='submit']").click(function () {
    var model = new Object();
    var pattern = /([A-Z]*)(\d)/;
    model.extInfo = new Array();
    model.extInfo2 = new Array();
    jQuery("input").each(function () {
        var property = $(this).attr("name");
        var value = $(this).val();
        if (value !== "") {
            var matches = property.match(pattern);
            if ((matches instanceof Object) && matches.index === 0) {
                var info = new Object();
                info.resolution = matches[1];
                info.fileSize = $(this).val();
                property = "extInfo" + (matches[2] === "2" ? "2" : "");
                console.log(property);
                model[property].push(info);
            } else {
                model[property] = $(this).val();
            }
        }
    });
    jQuery.ajax({
        url: "/video/query/process",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(model),
        type: "POST",
        dataType: 'html',
        beforeSend: function (xhr) {
            startBlock();
        },
        success: function (data, textStatus, jqXHR) {
            jQuery("#container").html(data);
        },
        complete: function (jqXHR, textStatus) {
            endBlock();
        }
    });
});
