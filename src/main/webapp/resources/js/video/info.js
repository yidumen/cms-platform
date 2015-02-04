var table = $("#video-table").dataTable({
    deferRender: true,
    lengthMenu: [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50],
    pageLength: 14,
    autoWidth: false,
    ordering: false,
    pagingType: "full_numbers",
    ajax: "/video/list",
    language: {
        url: "/resources/js/jquery/Chinese.json"
    },
    columns: [
        {data: "file"},
        {data: "title"},
        {data: "sort", render: function (data) {
                if (data > 0) {
                    return data;
                } else {
                    return '<span class="icon-blocked"></span>';
                }
            }
        },
        {data: "duration"},
        {data: "shootTime"},
        {data: "pubDate"},
        {data: "recommend", render: function (data) {
                if (data > 0) {
                    return '<i class="icon-checkbox"></i>';
                } else {
                    return '<i class="icon-checkbox-unchecked"></i>';
                }
            }
        },
        {data: "status"},
        {data: "id",
            render: function (data, type, row, meta) {
                return '<div class="operation"><a class="icon-info" title="完整信息" href="javascript:void(0);" data-index="' + meta.row + '"></a>&nbsp;&nbsp;<a target="_blank" href="http://yidumen.aliapp.com/video/' + row.file + '" class="icon-new-tab-2 place-right"></a></div>';
            }
        }
    ]
}).api();
$("#table-content").on("click", ".operation .icon-info", function () {
    var data = table.data()[$(this).data("index")];
    $("#itemId").html(data.id);
    $("#file").html(data.file);
    $("#dur").html(data.duration);
    $("#sort").html(data.sort);
    $("#grade").html(data.grade);
    $("#recommend").html(function () {
        return data.recommend > 0 ? data.recommend : "不推荐";
    });
    $("#shoot").html(data.shootTime);
    $("#status").html(data.status);
    $("#pub").html(data.pubDate);
    var tags = new Array(), cols = new Array();
    for (var i = 0, max = data.tags.length; i < max; i++) {
        if (data.tags[i].type === "COLUMN") {
            cols.push(data.tags[i].tagname);
        } else if (data.tags[i].type === "CONTENT") {
            tags.push(data.tags[i].tagname);
        }
    }
    $("#col").html(function () {
        var content = new String();
        for (var i = 0, max = cols.length; i < max; i++) {
            content = content.concat('<div class="label bg-darkOrange fg-white">', cols[i], '</div>');
        }
        return content;
    });
    $("#tag").html(function () {
        var content = new String();
        for (var i = 0, max = tags.length; i < max; i++) {
            content = content.concat('<div class="label bg-cyan fg-white">', tags[i], '</div>');
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
    $.Dialog({
        icon: '<i class="icon-film"></i>',
        title: "<strong>" + data.title + "</strong>",
        content: $("#amodal").html(),
        flat: true,
        shadow: true,
        padding: 20,
        width: 600,
        height: 560
    });
});