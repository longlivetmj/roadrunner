var formTemplate;

$(function () {
    formTemplate = $(".searchForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".x-navigation-minimize").trigger("click");
    load_module_departments();
});

function search_booking() {
    if (form_validate("searchForm")) {
        searchFormData('finance/financialChargeSearch/findBookings', 'searchForm').done(function (responseObject) {
            $('#bookingData').html(responseObject);
            $("#financeChargeData").DataTable({
                fixedColumns: {
                    "leftColumns": 1
                },
                scrollX: true,
                iDisplayLength: 25,
                aaSorting: [
                    [2, 'desc']
                ]
            });
        });
    }
}

function load_selected_booking(bookingSeq) {
    window.open('#/finance/financialChargeSearch?bookingSeq=' + bookingSeq, '_blank');
}

function load_module_departments() {
    var moduleSeq = $('#moduleSeq').val();
    if (moduleSeq !== -1) {
        loadObjectData('finance/financialChargeSearch/findModuleDepartments/', moduleSeq, "GET").done(function (departments) {
            populate_dropdown('departmentSeq', departments, 'departmentSeq', 'departmentName');
        });
    } else {
        $('#search_departmentSeq').selectpicker('refresh');
    }
}

function reset() {
    reset_form(formTemplate, 'searchForm');
}

function load_vehicle_list_by_transporter() {
    var transporterSeq = $("#transportCompanySeq").val();
    if (transporterSeq !== '') {
        loadObjectData('finance/financialChargeSearch/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
            populate_dropdown('vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            add_empty_option_to_dropdown('vehicleSeq', 'ALL VEHICLES');
        });
    }
}

function load_finance_charge(referenceSeq, moduleSeq, targetType, referenceType) {
    window.open('#/finance/financialChargeManagement?referenceSeq=' + referenceSeq + '&moduleSeq=' + moduleSeq + '&targetType=' + targetType + '&referenceType=' + referenceType, '_blank');
}