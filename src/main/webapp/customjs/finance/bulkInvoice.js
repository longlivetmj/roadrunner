var formTemplate;
var table;
$(function () {
    formTemplate = $(".bulkInvoiceForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    search_exchange_rate_bank();
});

function set_shipper_invoice_customer() {
    var customerSeq = $('select#customerSeq').val();
    if (customerSeq !== '') {
        loadObjectData('finance/bulkInvoice/getStakeholder/', customerSeq, 'GET').done(function (stakeholder) {
            populate_dropdown_single_element('shipperSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
            populate_dropdown_single_element('stakeholderSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
            set_stakeholder_address(customerSeq);
        });
    }
}

function search_exchange_rate_bank() {
    var moduleSeq = $('select#moduleSeq').val();
    var exchangeRateSourceType = $('select#exchangeRateSourceType').val();
    var sourceBankSeq = $('select#sourceBankSeq').val();
    var data = {
        'moduleSeq': moduleSeq,
        'exchangeRateSourceType': exchangeRateSourceType,
        'sourceBankSeq': sourceBankSeq
    };
    if (moduleSeq !== '' && exchangeRateSourceType !== '' && sourceBankSeq !== '') {
        loadPageData('finance/bulkInvoice/searchExchangeRateBankData', data, "POST").done(function (exchangeRate) {
            $("#loadExchangeRateBankData").html(exchangeRate);
            $('.exchangeRateBankLocalInvoice').each(function () {
                $(this).attr("checked", true);
                if ($(this).is(':checked')) {
                    $(this).trigger("change");
                }
                return false;
            });
        });
    }
}

function load_exchange_rate_bank(exchangeRateSeq) {
    var data = {
        'exchangeRateSeq': exchangeRateSeq
    };
    loadPageData('finance/bulkInvoice/searchExchangeRate', data, "POST").done(function (exchangeRate) {
        $("#loadCommonExchangeRateData").html(exchangeRate);
        get_currency(exchangeRateSeq);
    });
}

function load_module_departments() {
    var moduleSeq = $('#moduleSeq').val();
    if (moduleSeq !== -1) {
        loadObjectData('finance/bulkInvoice/findModuleDepartments/', moduleSeq, "GET").done(function (departments) {
            populate_dropdown('departmentSeq', departments, 'departmentSeq', 'departmentName');
        });
    }
}

function get_currency(exchangeRateSeq) {
    if (exchangeRateSeq !== null) {
        loadObjectData('finance/bulkInvoice/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
            populate_dropdown('currencySeq', responseObject, 'currencySeq', 'currencyCode');
            loadObjectData('finance/bulkInvoice/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
                $('#currencySeq').val(exchangeRate.baseCurrencySeq).selectpicker('refresh');
            });
        });
    }
}

function search_bulk_invoice() {
    searchFormData('finance/bulkInvoice/searchBulkInvoice', "searchForm").done(function (financialCharge) {
        $("#loadFinancialChargeData").html(financialCharge);
        $('.datatable').DataTable();
    });
}

function load_transport_booking_charge() {
    if (form_validate("transportBookingSearch")) {
        searchFormData('finance/bulkInvoice/searchBulkInvoiceTransportBooking', "transportBookingSearch").done(function (data) {
            $("#loadFinancialChargeTransportBooking").html(data);
            load_summed_charge();
        });
    }
}

function load_summed_charge() {
    if (form_validate("create")) {
        searchFormData('finance/bulkInvoice/loadSummedCharge', "create").done(function (data) {
            $("#loadFinancialCharge").html(data);
            calculate();
        });
    }
}

function calculate() {
    if (form_validate("create")) {
        searchFormData('finance/bulkInvoice/calculate', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                rivets.bind($('#calculations'), {bulkInvoice: responseObject.object});
            }
        });
    }
}

function create_bulk_invoice() {
    if (form_validate("create")) {
        saveFormData('finance/bulkInvoice/createBulkInvoice', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                load_bulk_invoice_data_to_modal(responseObject.object.bulkInvoiceSeq);
                load_transport_booking_charge();
            }
        });
    }
}

function update_bulk_invoice() {
    if (form_validate("update")) {
        var bulkInvoiceSeq = $('input#bulkInvoiceSeq').val();
        var responseObject = saveFormData('finance/bulkInvoice/updatebulkInvoice', 'update');
    }
}

function load_module_departments_search() {
    var moduleSeq = $('#search_moduleSeq').val();
    if (moduleSeq !== -1) {
        loadObjectData('finance/bulkInvoice/findModuleDepartments/', moduleSeq, "GET").done(function (departments) {
            populate_dropdown('search_departmentSeq', departments, 'departmentSeq', 'departmentName');
        });
    }
}

function load_bulk_invoice_data_to_modal(bulkInvoiceSeq) {
    var data = {'bulkInvoiceSeq': bulkInvoiceSeq};
    loadPageData('finance/bulkInvoice/finalBulkInvoice', data, "POST").done(function (finalInvoiceData) {
        $("#bulkInvoiceData").html(finalInvoiceData);
        $('#modal .updateOperation').show();
        $('#modal .operations').show();
        $('#myModal').modal('toggle');
        initializeConfirmBox("modal");
    });
}

function delete_bulk_invoice() {
    var formId = $(this)[0].getAttribute('data-id');
    var bulkInvoiceSeq = $('#' + formId + ' :input[name=bulkInvoiceSeq]').val();
    var data = {'bulkInvoiceSeq': bulkInvoiceSeq};
    loadDataPost('finance/bulkInvoice/deleteByBulkInvoiceSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + bulkInvoiceSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });

}

function get_initial_report(moduleSeq) {
    if (moduleSeq !== '') {
        loadObjectData('finance/bulkInvoice/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
            populate_dropdown('reportModel_reportName', responseObject, 'reportName', 'reportName');
        });
    }
}

function set_stakeholder_Data() {
    var stakeholderSeq = $("#stakeholderSeq").val();
    if (stakeholderSeq !== null && stakeholderSeq !== undefined && stakeholderSeq !== '') {
        set_stakeholder_address(stakeholderSeq);
        set_stakeholder_invoice_type(stakeholderSeq);
        set_stakeholder_default_currency(stakeholderSeq);
    }
}

function set_stakeholder_default_currency(stakeholderSeq) {
    var exchangeRateSeq = $('input[name=exchangeRateSeq]:checked').val();
    loadObjectData('finance/bulkInvoice/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
        loadObjectData('finance/bulkInvoice/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
            loadObjectData('finance/bulkInvoice/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
                populate_dropdown('currencySeq', responseObject, 'currencySeq', 'currencyCode');
                if (stakeholder.currencySeq !== '' && stakeholder.currencySeq !== 0) {
                    $("#currencySeq").val(stakeholder.currencySeq);
                    $("#currencySeq").change();
                } else {
                    new_notification("Stakeholder Default Currency Not Found", 'error');
                    $("#currencySeq").val(exchangeRate.currencySeq);
                    $("#currencySeq").change();
                }
            });
        });
    });
}

function set_stakeholder_address(stakeholderSeq) {
    loadObjectData('finance/bulkInvoice/getAddressBookByStakeholderSeq/', stakeholderSeq, 'GET').done(function (addressBook) {
        $("#stakeholderAddress").html(cleanAddressBook(addressBook));
    });
}

function set_stakeholder_invoice_type(stakeholderSeq) {
    loadObjectData('finance/bulkInvoice/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
        if (stakeholder.suspendedTaxRegNo !== null) {
            $("#sVatCustomer").html('SVAT CUSTOMER');
        } else {
            $("#sVatCustomer").html('');
        }
    });
}

function load__report() {
    var moduleSeq = $('input#create_moduleSeq').val();
    $("#reportOptionModel").modal("show");
    get_initial_report(moduleSeq);
}

function load_model_report() {
    var moduleSeq = $("#finalInvoice_moduleSeq").val();
    loadObjectData('finance/bulkInvoice/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
        populate_dropdown('reportModel_reportSeq', responseObject, 'reportSeq', 'displayName');
        $("#reportOptionModel").modal("show");
    });
}

function view_bulk_invoice_report() {
    var bulkInvoiceSeq = $('#finalInvoice_bulkInvoiceSeq').val();
    var reportSeq = $('select#reportModel_reportSeq').val();
    var pdf = $('#reportModel_pdf:checked').val();
    var rtf = $('#reportModel_rtf:checked').val();
    var xls = $('#reportModel_xls:checked').val();
    var data = {
        'enableCancel': 1,
        'bulkInvoiceSeq': bulkInvoiceSeq,
        'reportSeq': reportSeq,
        'pdf': pdf,
        'rtf': rtf,
        'xls': xls
    };
    loadDataGet('finance/bulkInvoice/generateBulkInvoiceReport', data).done(function (responseObject) {
        window.open('finance/bulkInvoice/getFile/' + responseObject, '_blank');
    });
}

function panel_toggle(id) {
    $('#' + id).collapse({
        toggle: true
    });
}