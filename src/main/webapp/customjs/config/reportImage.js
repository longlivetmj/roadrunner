$(function () {
    $("#create_reportImage").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });

    $("#update_reportImage").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });

    $(".select").selectpicker();
});

function create_report_image() {
    if (form_validate("create")) {
        saveFormDataWithAttachments('config/reportImage/saveReportImage', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('update_reportImageSeq', responseObject.object, 'reportImageSeq', 'fileName');
            }
        });
    }
}

function update_report_image() {
    if (form_validate("update")) {
        saveFormDataWithAttachments('config/reportImage/updateReportImage', 'update').done(function () {
            
        });
    }
}

function load_report_image() {
    var reportImageSeq = $("select#update_reportImageSeq").val();
    if (reportImageSeq !== '-1') {
        loadObjectData('config/reportImage/findByReportImageSeq/', reportImageSeq, 'GET').done(function (reportImage) {
            $('form#update').BindJson(reportImage, 'update');
        });
    } else {
        $('form#update').trigger("reset");
    }
}
