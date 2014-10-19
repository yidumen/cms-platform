var videos = new Array();
jQuery.ajax({
    url: "/video/list",
    dataType: 'json',
    cache: false,
    success: function (data) {
        var videos = data;
        var tbody = "";
        videos.forEach(function (item) {
            var pubDate = new Date(item.pubDate);
            tbody = tbody.concat("<tr>",
                    "<td>", item.file, "</td>",
                    "<td>", item.title, "</td>",
                    "<td>", item.sort, "</td>",
                    "<td>", item.duration, "</td>",
                    "<td>", item.shootTime, "</td>",
                    "<td>", pubDate.getFullYear(), "-", pubDate.getMonth() + 1, "-", pubDate.getDate(), "</td>",
                    "<td>", item.chatroomVideo ? "聊天室" : "其它", "</td>",
                    "<td>", item.recommend > 0 ? "<i class='icon-check'></i>" : "<i class='icon-check-empty'></i>", "</td>",
                    "<td>", item.status, "</td>",
                    "</tr>");
        });
        jQuery("#table-content").html(tbody);
    },
    complete: function (jqXHR, textStatus) {

    }
});

function parseDuration(data) {

}