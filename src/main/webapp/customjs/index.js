/**
 * Created by Thilanga-Ehl on 8/31/2016.
 */

function resizeDiv() {
    var window_height = $("#pageContent").height(),
        content_height = window_height;
    if ($(window.top).height() > window_height) {
        if ($(window.top).height() > 600) {
            content_height = 600;
        } else {
            content_height = $(window.top).height();
        }
    }
    $('.page-content').height(content_height);
}

$(function () {
    // Listen on hash change:
    window.addEventListener('hashchange', router);
    // Listen on page load:
    window.addEventListener('load', routerPageLoad);
    //loadPageContent('config/freightLineManagement', 'Freight Line');

    /*   window.setInterval(function () {
           renew_csrf();
       }, 6000);*/

});

function router() {
    var url = location.hash.slice(1) || '/';
    if (url.length > 2) {
        url = url.substring(1);
        loadPageContent(url);
    }
}

function routerPageLoad() {
    var url = location.hash.slice(1) || '/';
    if (url.length > 2) {
        url = url.substring(1);
        loadLeftNaviByURL(url);
        loadPageContent(url);
    }
}

function loadPageContent(pageRefInput) {
    $.ajax({
        url: pageRefInput,
        type: "GET",
        dataType: 'text',
        success: function (response) {
            try {
                $('#pageContent').html(response);
                var selectedListId = pageRefInput.replace("/", "-");
                $('#' + selectedListId).toggleClass("active");
                $('#' + selectedListId).siblings().removeClass("active");
                if (!$('#' + selectedListId).parent().parent().hasClass("active")) {
                    $('#' + selectedListId).parent().parent().toggleClass("active");
                }
                $('#' + selectedListId).parent().parent().siblings().removeClass("active");
                var pageName = $("#" + pageRefInput.split("/")[1]).val();
                if(pageName !== undefined){
                    $('#breadcrumbDocumentLink').html(pageName);
                    document.title = pageName;
                }else{
                    document.title = 'Road Runner';
                }
            } catch (e) {
            }
            resizeDiv();
        },
        error: function (error) {
            console.log('the page was NOT loaded', error);
        }
    });
}

function loadLeftNavi(pageRefInput) {

    if (window[pageRefInput] != null) {
        var cacheData = window[pageRefInput];
        $("#sideBarData").html(cacheData);
        $("#breadcrumbSubModule").html(pageRefInput.replace("/", " > "));
        $("#x-navigation").mCustomScrollbar("update");
        initializeSideBar();
        resizeDiv();

    } else {
        $.ajax({
            url: '/loadLeftNavi/' + pageRefInput,
            type: 'GET',
            dataType: 'text',
            success: function (response) {
                window[pageRefInput] = response;
                $("#sideBarData").html(response);
                $("#breadcrumbSubModule").html(pageRefInput.replace("/", " > "));
                $("#x-navigation").mCustomScrollbar("update");
                initializeSideBar();
                resizeDiv();
            },
            error: function (error) {
                console.log('the page was NOT loaded', error);
            }
        });
    }
}

function loadLeftNaviByURL(URL) {
    $.ajax({
        url: '/loadLeftNaviByURL/' + URL,
        type: 'GET',
        dataType: 'text',
        success: function (response) {
            $("#sideBarData").html(response);
            $("#breadcrumbSubModule").html($('#subModuleDefinition').html());
            $("#x-navigation").mCustomScrollbar("update");
            initializeSideBar();
            resizeDiv();
        },
        error: function (error) {
            console.log('the page was NOT loaded', error);
        }
    });
}

function force_logout() {
    //window.location.href= "/logout";
}

function load_user_profile() {
    $("#myModal").modal("show");
}

function change_user_credentials() {
    if (form_validate("userProfile")) {
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        if (newPassword == confirmPassword) {
            var responseObject = saveFormData('/changePassword', 'userProfile');
            if (responseObject.status == true) {
                new_notification("Password successfully Changed", "success");
            }
        } else {
            new_notification("New Password did not match confirmed password", "error");
        }

    }
}

function renew_csrf() {
    $.ajax({
        url: "/renewCSRF",
        type: "POST",
        async: true,
        global: false,
        done: function (data) {
            $("meta[name='_csrf']").attr('content', data.token);
            $("meta[name='_csrf_header']").attr('content', data.headerName);
        },
        fail: function () {
            console.log('the page was NOT loaded', error);
        }
    });
}