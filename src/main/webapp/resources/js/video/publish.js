var table = $("#video-table").dataTable({
    paging:false,
    autoWidth: false,
    ordering: false,
    ajax: "/video/verify",
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
        {data: "shootTime"},
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
                return '<div class="operation"><a class="icon-globe on-left" title="发布" href="javascript:void(0);" data-file="' + row.file + '"></a><a title="批处理" class="icon-new" href="/video/bat/' + data + '"></a><a href="javascript:void(0)" class="icon-remove place-right" data-id="' + data + '"></a></div>';
            }
        }
    ]
}).api();
$("#table-content").on("click", ".icon-globe", function () {
    var file = $(this).data("file");
    $.ajax({
        type: "POST",
        data: "text",
        url: "/video/publish/" + file,
        success: function (data, textStatus, jqXHR) {
            showMessage("发布成功", "视频 " + file + " 已发布");
            table.ajax.reload();
        }
    });
});