$("form").submit(function () {
    $.ajax({
        data: $(this).serialize(),
        type: "post",
        url: $(this).attr('action'),
        success: function () {
            $("#container").load("/video/publish");
        }
    });
    return false;
});