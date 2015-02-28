$('#edit-panel').hide();
$("#video-table").dataTable({
    deferRender: true,
    lengthMenu: [10, 11, 12, 13, 14, 15, 20, 25, 30, 40, 50],
    pageLength: 14,
    autoWidth: false,
    pagingType: "full_numbers",
    ajax: "/video/list",
    language: {
        url: "/resources/js/jquery/Chinese.json"
    },
    columns: [
        {data: "file"},
        {data: "title"},
        {data: "tags", render: function (data) {
                var result = new String();
                if (data.length > 0) {
                    for (var i = 0, max = data.length; i < max; i++) {
                        if (data[i].type === "COLUMN") {
                            if (data[i].tagname !== "") {
                                result = result.concat(" ");
                                result = result.concat(data[i].tagname);
                            }
                        }
                    }
                    return result.trim();
                } else {
                    return '无';
                }
            }
        },
        {data: "sort"},
        {data: "duration"},
        {data: "shootTime"},
        {data: "pubDate", render: function (data) {
                if (data === null) {
                    return "从未发布";
                } else {
                    return data;
                }
            }
        },
        {data: "recommend"},
        {data: "status"}
    ]
});
$('#video-table').DataTable().on("page", function () {
    $('#edit-panel').hide();
});
$('#search-toolbar>button').click(function () {
    var search = $(this).data("search");
    if (search) {
        $("#video-table").DataTable().columns().search("");
        $("#video-table").DataTable().column(search.column)
                .search(search.input, search.regex, !search.regex)
                .draw();
    }
});
$('#full-edit').click(function () {
   var link = $(this).attr("href");
   $(this).attr('href', link + $('#video-table').DataTable().row('.info').data().id);
});
$('#video-table tbody').on('click', 'tr', function () {
    if ($(this).hasClass('info')) {
        $(this).removeClass('info');
        $('#edit-panel').fadeOut();
    }
    else {
        var table = $("#video-table").DataTable();
        var row = table.$('tr.info');
        row.removeClass('info');
        $(this).addClass('info');
        var node = $(this).position();
        $('#edit-panel').animate({top: (node.top - 81) + "px"}).fadeIn();
    }
});