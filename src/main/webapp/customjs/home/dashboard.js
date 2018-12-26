$(function () {
    $('#firstRow').children('div').each(function () {
        var controllerUrl = $('#controllerUrl' + this.id).val();
        var widgetName = $('#widgetName' + this.id).val();
        var widgetSeq = this.id;
        loadPageData(getRequestUrl(controllerUrl, widgetName), {'widgetSeq': widgetSeq}, 'GET').done(function (widget) {
            $("#" + widgetSeq).html(widget);
        });
    });

    $('#secondRow').children('div').each(function () {
        var controllerUrl = $('#controllerUrl' + this.id).val();
        var widgetName = $('#widgetName' + this.id).val();
        var widgetSeq = this.id;
        loadPageData(getRequestUrl(controllerUrl, widgetName), {'widgetSeq': widgetSeq}, 'GET').done(function (widget) {
            $("#" + widgetSeq).html(widget);
        });
    });

    $('#thirdRow').children('div').each(function () {
        var controllerUrl = $('#controllerUrl' + this.id).val();
        var widgetName = $('#widgetName' + this.id).val();
        var widgetSeq = this.id;
        loadPageData(getRequestUrl(controllerUrl, widgetName), {'widgetSeq': widgetSeq}, 'GET').done(function (widget) {
            $("#" + widgetSeq).html(widget);
        });
    });

});

function getRequestUrl(controllerUrl, methodUrl) {
    return 'home/' + controllerUrl + '/' + methodUrl;
}

function refresh(controllerUrl, widgetName, widgetSeq) {
    loadPageData(getRequestUrl(controllerUrl, widgetName), {'widgetSeq': widgetSeq}, 'GET').done(function (widget) {
        $("#" + widgetSeq).html(widget);
    });
}