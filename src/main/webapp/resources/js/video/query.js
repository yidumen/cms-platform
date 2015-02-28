$("button[type='submit']").click(function () {
    var model = new Object();
    var pattern = /([A-Z]*)(\d)/;
    model.extInfo = new Array();
    model.extInfo2 = new Array();
    jQuery("input, select").each(function () {
        var property = $(this).attr("name");
        var value = $(this).val();
        if (value !== "") {
            var matches = property.match(pattern);
            if ((matches instanceof Object) && matches.index === 0) {
                var info = new Object();
                info.resolution = matches[1];
                info.fileSize = $(this).val();
                property = "extInfo" + (matches[2] === "2" ? "2" : "");
                model[property].push(info);
            } else {
                model[property] = $(this).val();
            }
        }
    });
    $.ajax({
        url: "/video/query/process",
        contentType: "application/json",
        data: JSON.stringify(model),
        type: "POST",
        dataType: 'html',
        success: function (data) {
            $("body").html(data);
        }
    });
});
