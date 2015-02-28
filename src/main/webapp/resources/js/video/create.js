$.get("/video/max/sort", function (data) {
    $("#auto-sort").click(function () {
        $("#sort").val(Number(data) + 1);
        return false;
    });
});
$.get("/video/max/recommend", function (data) {
    $("#auto-recommend").click(function () {
        $("#recommend").val(Number(data) + 1);
        return false;
    });
});