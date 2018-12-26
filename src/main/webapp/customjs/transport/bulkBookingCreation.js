var formTemplate;

$(function () {
    formTemplate = $(".bulkBookingFrom").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".fileUpload").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });
});

function upload() {
    if (form_validate("create")) {
        saveFormDataWithAttachments('transport/bulkBookingCreation/upload', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                $('#create').trigger("reset");
            }
        });
    }
}

function set_shipper_invoice_customer() {
    var customerSeq = $('select#customerSeq').val();
    if (customerSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findStakeholderSeqSeq/', customerSeq, 'GET').done(function (stakeholder) {
            populate_dropdown_single_element('shipperSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
            populate_dropdown_single_element('invoiceCustomerSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
        });
    }
}

function view_bulk_booking_data() {
    if (form_validate("searchForm")) {
        searchFormData('transport/bulkBookingCreation/findData', 'searchForm').done(function (responseObject) {
            $('#bulkUploadData').html(responseObject);
            $('.datatable').DataTable();
        });
    }
}