$(".dropdown-menu li a").click(function (event) {
    var dom = jQuery(event.currentTarget);
    jQuery(".dropdown-menu").css("display", "none");
    dom.parents(".dropdown").addClass("active");
});

