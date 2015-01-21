var videos;
refreshTable();
$("#table-content").on("click", ".icon-globe", function () {
    var file = $(this).data("file");
    jQuery.ajax({
        type: "POST",
        data: "text",
        url: "/video/publish/" + file,
        success: function (data, textStatus, jqXHR) {
            showMessage("发布成功", "视频 " + file + " 已发布");
            refreshTable();
        }
    });
});
function refreshTable() {
    jQuery.ajax({
        url: "/video/verify",
        dataType: 'json',
        cache: false,
        success: function (data, textStatus, jqXHR) {
            videos = data;
            var tbody = new String();
            data.forEach(function (item, index, array) {
                tbody = tbody.concat("<tr>",
                        "<td>", item.file, "</td>",
                        "<td>", item.title, "</td>",
                        "<td>", item.sort === 0 ? "无" : item.sort, "</td>",
                        "<td>", item.shootTime, "</td>",
                        "<td>", item.recommend > 0 ? "<i class='icon-check'></i>" : "<i class='icon-check-empty'></i>", "</td>",
                        "<td><span class='operation'>",
                        '<a title="发布" class="icon-globe pull-left" data-file="' + item.file + '" href="javascritp:void(0)"></a>',
                        '<a title="批处理" class="icon-file pull-left" href="/video/bat/' + item.id + '"></a>',
                        '<a title="删除" class="icon-trash pull-right" data-file="' + item.file + '" href="javascritp:void(0)"></a>',
                        "</span></td>",
                        "</tr>");
            });
            jQuery("#table-content").html(tbody);
        }
    });
}
