var formTemplate;

$(function () {
    formTemplate = $(".searchForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".datetimepicker").datepicker({dateFormat: 'yy-mm-dd', timeFormat: 'hh:mm TT'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    view_hide('createBookingPanel');
    view_hide('createViaLocationPanel');
    editableInit();
});

function save_tracking_data() {
    if (form_validate("trackingDataForm")) {
        saveFormData('transport/transportBookingSummary/saveFeedback', 'trackingDataForm').done(function (responseObject) {
            if (responseObject.status === true) {

            }
        });
    }

}

function load_finance_charge(referenceSeq, moduleSeq, targetType, referenceType) {
    window.open('#/finance/financialChargeManagement?referenceSeq=' + referenceSeq + '&moduleSeq=' + moduleSeq + '&targetType=' + targetType + '&referenceType=' + referenceType, '_blank');
}

function editableInit() {
    $('.AAP').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeAAP', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $("#transportBookingSeq").val()},
            intercept: function (jsondata) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    return (responseData.message);
                } else {
                    return '';
                }
            },
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.DFP').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeDFP', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $("#transportBookingSeq").val()},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.AAD').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeAAD', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $("#transportBookingSeq").val()},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.DFD').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeDFD', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $("#transportBookingSeq").val()},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.arrivalConfirmation').each(function () {
        var $this = $(this);
        var data = $("#arrivalConfirmationData").val();
        var declineRemarks = '';
        $this.editable('transport/transportBookingStatus/changeArrivalConfirmation', {
            type: 'select',
            data: data,
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            submit: 'OK',
            cancel: 'Cancel',
            width: '50px',
            height: '14px',
            callback: function (data, settings) {
                var json = $.parseJSON(data);
                $(this).text(json.arrivalConfirmation);
            }
        });
    });

    $('.supplierNegotiations').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeSupplierNegotiations', {
            type: 'supplierNegotiation',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            submit: 'OK',
            cancel: 'Cancel',
            width: '200px',
            height: '50px',
            submitdata: {
                id: $("#transportBookingSeq").val()
            }
        });
    });
}
