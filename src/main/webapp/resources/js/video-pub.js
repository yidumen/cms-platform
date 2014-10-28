$("#pub-shoot").datetimepicker({
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
$("form").submit(function () {
    $.ajax({
        data: $(this).serialize(),
        type: "post",
        url: $(this).attr('action'),
        success: function (data) {
            if (data === "0") {
                var pattern = /([0-9]*)-([0-9]*)-([0-9]*)/;
                var date = $("#pub-shoot").val();
                var matches = date.match(pattern);
                var content = "@echo off\r\nset video_id=" + $("#file").val() + "\r\nset title=" + $("#title").val() + "\r\nset video_year=" + matches[1] + "\r\nset video_month=" + matches[2] + "\r\nset video_day=" + matches[3] + "\r\ncall X:\\ChatRoom\\Tools\\video_opening_title_generator.bat";
                downloadFile("cms.bat", content);
            } else {
                showError(data);
            }
        }
    });
    return false;
});
function downloadFile(fileName, content) {
    var aLink = document.createElement('a');
    var blob = new Blob([content]);
    var evt = document.createEvent("HTMLEvents");
    evt.initEvent("click", false, false);//initEvent 不加后两个参数在FF下会报错, 感谢 Barret Lee 的反馈
    aLink.download = fileName;
    aLink.href = URL.createObjectURL(blob);
    aLink.dispatchEvent(evt);
}
