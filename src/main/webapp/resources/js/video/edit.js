var video;
$.ajax({
    cache: false,
    url: "/video/ajax/" + $.getPathVariable(3),
    dataType: "json",
    type: 'GET',
    success: function (data, textStatus, jqXHR) {
        video = data;
        $('#title').val(data.title);
        $("#itemId").html(data.id);
        $("#file").html(data.file);
        $("#dur").html(data.duration);
        $("#sort").val(data.sort);
        $("#grade").val(data.grade);
        $("#recommend").val(data.recommend);
        var shoot = $("#shoot");
        shoot.val(data.shootTime);
        shoot.parent().datepicker({
            date: data.shootTime, // set init date
            format: "yyyy-mm-dd", // set output format
            effect: "none", // none, slide, fade
            position: "bottom", // top or bottom,
            locale: 'zhCN' // 'ru' or 'en', default is $.Metro.currentLocale
        });
        $("#status").html(data.status);
        $("#pub").html(data.pubDate);
        var tags = new Array(), cols = new Array();
        for (var i = 0, max = data.tags.length; i < max; i++) {
            if (data.tags[i].type === "COLUMN") {
                cols.push(data.tags[i]);
            } else if (data.tags[i].type === "CONTENT") {
                tags.push(data.tags[i]);
            }
        }
        $("#col").before(function () {
            var content = new String();
            for (var i = 0, max = cols.length; i < max; i++) {
                content = content.concat('<span class="bg-darkOrange fg-white on-left-more padding5">', cols[i].tagname, '<span class="on-right" data-id="' + cols[i].id + '">×</span>', '</span>');
            }
            return content;
        });
        $("#tag").before(function () {
            var content = new String();
            for (var i = 0, max = tags.length; i < max; i++) {
                content = content.concat('<span class="bg-cyan fg-white on-left-more padding5">', tags[i].tagname, '<span class="on-right" data-id="' + tags[i].id + '">×</span>', '</span>');
            }
            return content;
        });
        $("#desc").html(function () {
            if (data.descrpition && data.descrpition.length > 0) {
                return data.descrpition;
            } else {
                return "无描述信息";
            }
        });
        $("#note").html(function () {
            if (data.note && data.note.length > 0) {
                return data.note;
            } else {
                return "无备注信息";
            }
        });
        $("#ext").html(function () {
            var content = new String();
            for (var i = 0, max = data.extInfo.length; i < max; i++) {
                content = content.concat("<tr><td>", data.extInfo[i].resolution, "</td>",
                        "<td>", data.extInfo[i].fileSize, "</td></tr>");
            }
            return content;
        });
    }
});