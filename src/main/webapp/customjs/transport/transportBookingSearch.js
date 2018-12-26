var formTemplate;

$(function () {
    formTemplate = $(".searchForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function search_booking() {
    if (form_validate("searchForm")) {
        searchFormData('transport/transportBookingSearch/findBookings', 'searchForm').done(function (responseObject) {
            $('#bookingData').html(responseObject);
            $("#bookingData .datatable").DataTable();
        });
    }
}

function load_selected_booking(bookingSeq) {
    window.open('#/transport/transportBookingManagement?bookingSeq=' + bookingSeq, '_blank');
}

function reset() {
    reset_form(formTemplate, 'searchForm');
}