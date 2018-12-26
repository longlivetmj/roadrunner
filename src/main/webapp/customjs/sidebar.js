/**
 * Created by Thilangaj on 9/19/2016.
 */
function initializeSideBar() {
    if($(".page-sidebar").hasClass("scroll")){
        if($("body").hasClass("page-container-boxed")){
            var doc_height = $(document).height() - 40;
        }else{
            var doc_height = $(document).height() - 40;
        }
        $(".page-sidebar").height(doc_height);
    }

    $(".x-navigation li, .x-navigation-minimized > li").click(function (event) {
        event.stopPropagation();
        var li = $(this);
        if (li.children("ul").length > 0 || li.children(".panel").length > 0 || $(this).hasClass("xn-profile") > 0) {
            if (li.hasClass("active")) {
                li.removeClass("active");
                li.find("li.active").removeClass("active");
            } else {
                li.addClass("active");
                $('.x-navigation-horizontal li ul li').hide();
            }

            if(li.hasClass("active")){

            }
        }
    });

    $(".x-navigation-horizontal li").click(function (event) {
        event.stopPropagation();
        var li = $(this);
        $('.x-navigation-horizontal li ul li').show();
        if (li.children("ul").length > 0 || li.children(".panel").length > 0 || $(this).hasClass("xn-profile") > 0) {
            if (li.hasClass("active")) {
                li.removeClass("active");
                li.find("li.active").removeClass("active");
            } else {
                li.addClass("active");
            }
        }
    });
}

