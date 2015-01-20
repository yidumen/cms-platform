var pageSize = getCookie("page.size");
if (pageSize.length === 0) {
    pageSize = 12;
    setCookie("page.size", pageSize);
}
$("#size").val(pageSize);
var pageCount = Math.ceil(count / pageSize);
var videos;
var currentPage = 1;
refreshTable();
$("#count").html(pageCount);
$("#size").blur(function () {
    setCookie("page.size", this.value);
    resetTable();
    refreshTable();
});
$("#pagination").on("click", "li a", function (event) {
    currentPage = $(event.currentTarget).data("page");
    refreshTable();
});
$("#currentPage").blur(function () {
    currentPage = parseInt(this.value);
    refreshTable();
});
$("#table-content").on("click", ".icon-zoom-in", function (event) {
    var item = videos[$(event.currentTarget).data("index")];
    $("#amodal #title").replaceWith("<span id='title' class='modal-title h4'>" + item.title + "</span>");
    $("#amodal #itemId").html(item.id);
    $("#amodal #file").html(item.file);
    $("#amodal #dur").html(item.duration);
    $("#amodal #status").html(item.status);
    $("#amodal #sort").replaceWith("<p id='sort' class='text-muted form-control-static'>" + item.sort + "</p>");
    $("#amodal #shoot").replaceWith("<p id='shoot' class='text-muted form-control-static'>" + item.shootTime + "</p>");
    $("#amodal #recommend").replaceWith("<p id='recommend' class='text-muted form-control-static'>" + (item.recommend > 0 ? item.recommend : "不推荐") + "</p>");
    $("#amodal #pub").html(item.pubDate);
    $("#amodal #grade").replaceWith(function () {
        if ((item.grade instanceof String) && (item.grade.length > 0)) {
            return "<p id='grade' class='text-muted form-control-static'>" + item.grade + "</p>";
        } else {
            return "<p id='grade' class='text-muted form-control-static'>未分级</p>";
        }
    });
    $("#amodal #tag").html(function () {
        var tags = item.tags;
        if (tags.length === 0) {
            return "未指定标签";
        }
        var result = new String();
        for (var i = 0, max = tags.length; i < max; i++) {
            if (tags[i].type === "CONTENT") {
                result = result.concat("<span class='label label-success' style='margin-right:5px'>");
            } else {
                result = result.concat("<span class='label label-default' style='margin-right:5px'>");
            }
            result = result.concat(tags[i].tagname, "</span>");
        }
        return result;
    });
    $("#amodal #desc").replaceWith(function () {
        if ((item.descrpition instanceof String) && (item.descrpition.length > 0)) {
            return "<p id='desc' class='text-muted form-control-static'>" + item.descrpition + "</p>";
        } else {
            return "<p id='desc' class='text-muted form-control-static'>没有描述信息</p>";
        }
    });
    $("#amodal #note").replaceWith(function () {
        if ((item.note instanceof String) && (item.note.length > 0)) {
            return "<p id='note' class='text-muted form-control-static'>" + item.note + "</p>";
        } else {
            return "<p id='note' class='text-muted form-control-static'>没有备注信息</p>";
        }
    });
    $("#amodal #ext").html(function () {
        var content = new String();
        var infos = item.extInfo;
        for (var i = 0, max = infos.length; i < max; i++) {
            content = content.concat("<tr>",
                    "<td>", infos[i].resolution, "</td>",
                    "<td>", infos[i].fileSize, "</td>",
                    "</tr>");
        }
        return content;
    });
    $("#amodal #footer").html("<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>");
    $("#amodal #help").addClass("hide");
    $("#amodal").modal({
        backdrop: "static"

    });
});
$("#table-content").on("click", ".icon-edit", function (event) {
    var index = $(event.currentTarget).data("index");
    var item = videos[index];
    $("#amodal #title").replaceWith("<input id='title' class='form-control h4' style='width:70%' value='" + item.title + "' disabled>");
    $("#amodal #itemId").html(item.id);
    $("#amodal #file").html(item.file);
    $("#amodal #dur").html(item.duration);
    $("#amodal #status").html(item.status);
    $("#amodal #sort").replaceWith("<input id='sort' class='form-control' value='" + item.sort + "'>");
    $("#amodal #shoot").replaceWith("<input id='shoot' class='form-control' data-font-awesome='true' value='" + item.shootTime + "'>");
    $("#amodal #recommend").replaceWith("<input id='recommend' class='form-control' value='" + item.recommend + "'>");
    $("#amodal #pub").html(item.pubDate);
    $("#amodal #grade").replaceWith("<input id='grade' class='form-control' value='" + item.grade + "'>");
    $("#amodal #tag").html(function () {
        var tags = item.tags;
        if (tags.length === 0) {
            return "未包含标签";
        }
        var result = new String();
        for (var i = 0, max = tags.length; i < max; i++) {
            if (tags[i].type === "CONTENT") {
                result = result.concat("<span class='label label-success' style='margin-right:5px'>");
            } else {
                result = result.concat("<span class='label label-default' style='margin-right:5px'>");
            }
            result = result.concat(tags[i].tagname, "</span>");
        }
        return result;
    });
    $("#amodal #desc").replaceWith("<textarea id='desc' class='form-control' value='" + item.descrpition + "'>");
    $("#amodal #note").replaceWith("<input id='note' class='form-control' value='" + item.note + "'>");
    $("#amodal #ext").html(function () {
        var content = new String();
        var infos = item.extInfo;
        for (var i = 0, max = infos.length; i < max; i++) {
            content = content.concat("<tr>",
                    "<td>", infos[i].resolution, "</td>",
                    "<td>", infos[i].fileSize, "</td>",
                    "</tr>");
        }
        return content;
    });
    $("#amodal #footer").html("<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button><button id='submit' type='button' class='btn btn-primary' data-index='" + index + "'>提交</button>");
    $("input[id='shoot']").datetimepicker({
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
    $("#amodal #help").removeClass("hide");
    $("#amodal").modal({
        backdrop: "static"
    });
});
$("#amodal").on("click", "#submit", function (event) {
    var index = $(event.currentTarget).data("index");
    var item = videos[index];
    item.title = $("#amodal #title").val();
    item.sort = $("#amodal #sort").val();
    item.shootTime = $("#amodal #shoot").val();
    item.recommend = $("#amodal #recommend").val();
    item.grade = $("#amodal #grade").val();
    item.descrpition = $("#amodal #desc").val();
    item.note = $("#amodal #note").val();
    jQuery.ajax({
        url: "/video/submit",
        dataType: 'text',
        cache: false,
        data: JSON.stringify(item),
        type: "POST",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            jQuery("#amodal").modal("hide");
            refreshTable();
            if (data === '"0"') {
                showMessage("成功！", "视频 <strong>" + item.title + "</strong> 信息已保存。");
            } else {
                showError(data.substring(1, data.length - 1));
            }
        }
    });
});
$("#filter").click(function () {
    $("#container").load($(this).attr("href"));
    return false;
});
function resetTable() {
    pageSize = getCookie("page.size");
    currentPage = 1;
    pageCount = Math.ceil(count / pageSize);
}
function refreshTable() {
    jQuery.ajax({
        url: "/video/pagination/" + currentPage,
        dataType: 'json',
        cache: false,
        success: function (data) {
            videos = data;
            var tbody = new String();
            data.forEach(function (item, index, array) {
                tbody = tbody.concat("<tr>",
                        "<td>", item.file, "</td>",
                        "<td>", item.title, "</td>",
                        "<td>", item.sort === 0 ? "无" : item.sort, "</td>",
                        "<td>", item.duration, "</td>",
                        "<td>", item.shootTime, "</td>",
                        "<td>", item.pubDate, "</td>",
                        "<td>", item.recommend > 0 ? "<i class='icon-check'></i>" : "<i class='icon-check-empty'></i>", "</td>",
                        "<td>", item.status, "</td>",
                        "<td><span class='operation'>",
                        "<a class='icon-zoom-in pull-left' title='完整信息' href='javascript:void(0);' data-index='" + index + "'></a>",
                        "<a class='icon-edit pull-left' title='编辑' href='javascript:void(0);' data-index='" + index + "'></a>",
                        "<a target='_blank' href='http://yidumen.aliapp.com/%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%A7%86%E9%A2%91/" + item.file + "' class='icon-external-link pull-right' />",
                        "</span></td>",
                        "</tr>");
            });
            jQuery("#table-content").html(tbody);
            jQuery("#pagination").html(function () {
                var pagination = new String();
                if (currentPage > 1) {
                    pagination = pagination.concat("<li><a class='icon-double-angle-left' href='javascript:void(0);' data-page='1'></a></li>");
                }
                if (currentPage - 1 > 1) {
                    pagination = pagination.concat("<li><a class='icon-angle-left' href='javascript:void(0);' data-page='", currentPage - 1, "'></a></li>");
                }
                var minPage = currentPage - 4, maxPage = currentPage + 5;
                if (minPage < 1) {
                    minPage = 1;
                }
                for (var i = minPage, max = maxPage < 10 ? (pageCount < 10 ? pageCount : 10) : (maxPage >= pageCount ? pageCount : maxPage); i <= max; i++) {
                    pagination = pagination.concat("<li", i === currentPage ? " class='active'" : "", "><a href='javascript:void(0);'", " data-page='", i, "'>", i, "</a></li>");
                }
                if (currentPage + 1 < pageCount) {
                    pagination = pagination.concat("<li><a class='icon-angle-right' href='javascript:void(0);' data-page='", currentPage + 1, "'></a></li>");
                }
                if (currentPage < pageCount) {
                    pagination = pagination.concat("<li><a class='icon-double-angle-right' href='javascript:void(0);' data-page='", pageCount, "'></a></li>");
                }
                return pagination;
            });
            $("#currentPage").val(currentPage);
        }
    });
}