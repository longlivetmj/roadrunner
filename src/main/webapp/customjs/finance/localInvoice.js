var formTemplate;
$(function () {
    formTemplate = $(".localInvoiceForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    initializeCustomAjaxSelectWithTwoParam('transportBookingSeq', '/finance/localInvoice/findTransportBooking', 'moduleSeq', 'targetType');
    initializeCustomSelect('search_transportBookingSeq', '/finance/localInvoice/findTransportBooking', 'search_moduleSeq');
    $('#transportBookingSeq').on('changed.bs.select', function (e) {
        search_financial_charge_transport_booking();
        panel_toggle('bookingInformation');
    });
    var transportBookingSeq = $("#transportBookingSeq").val();
    if (transportBookingSeq !== null && transportBookingSeq !== '') {
        search_exchange_rate_bank();
        search_financial_charge_transport_booking();
    }
});

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
        loadPageData('finance/localInvoice/searchExchangeRateBankData', data, "POST").done(function (exchangeRate) {
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
    loadPageData('finance/localInvoice/searchExchangeRate', data, "POST").done(function (exchangeRate) {
        $("#loadCommonExchangeRateData").html(exchangeRate);
        get_currency(exchangeRateSeq);
    });
}

function load_module_departments() {
    var moduleSeq = $('#search_moduleSeq').val();
    if (moduleSeq !== -1) {
        loadObjectData('finance/localInvoice/findModuleDepartments/', moduleSeq, "GET").done(function (departments) {
            populate_dropdown('search_departmentSeq', departments, 'departmentSeq', 'departmentName');
        });
    } else {
        $('#search_departmentSeq').selectpicker('refresh');
    }
}

function get_currency(exchangeRateSeq) {
    if (exchangeRateSeq !== null) {
        loadObjectData('finance/localInvoice/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
            populate_dropdown('baseCurrencySeq', responseObject, 'currencySeq', 'currencyCode');
            $('select#baseCurrencySeq').change();
        });
        loadObjectData('finance/localInvoice/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
            $('select#baseCurrencySeq').val(exchangeRate.baseCurrencySeq);
            set_stakeholder_Data();
        });
    }
}

function search_financial_charge_transport_booking() {
    var moduleSeq = $('select#moduleSeq').val();
    var targetType = $('select#targetType').val();
    var transportBookingSeq = $('select#transportBookingSeq').val();
    var data = {
        'moduleSeq': moduleSeq,
        'targetType': targetType,
        'transportBookingSeq': transportBookingSeq
    };
    loadPageData('finance/localInvoice/searchLocalInvoiceTransportBooking', data, "POST").done(function (financialCharge) {
        $("#loadFinancialChargeTransportBooking").html(financialCharge);
        load_transport_booking_charge();
        panel_toggle('bookingInformation');
    });
}

function search_local_invoice() {
    searchFormData('finance/localInvoice/searchLocalInvoice', "searchForm").done(function (financialCharge) {
        $("#loadFinancialChargeData").html(financialCharge);
        $('.datatable').DataTable();
    });
}

function load_transport_booking_charge() {
    var moduleSeq = $('select#moduleSeq').val();
    var exchangeRateSeq = $('input[name=exchangeRateSeq]:checked').val();
    var financialChargeSeq = $("#financialChargeSeq").val();
    var baseCurrencySeq = $('select#currencySeq').val();
    if (financialChargeSeq !== undefined && financialChargeSeq !== null && exchangeRateSeq !== null && baseCurrencySeq !== null && moduleSeq !== null &&
        financialChargeSeq !== '' && exchangeRateSeq !== '' && baseCurrencySeq !== '' && moduleSeq !== '') {
        var data = {
            'moduleSeq': moduleSeq,
            'financialChargeSeq': financialChargeSeq,
            'exchangeRateSeq': exchangeRateSeq,
            'baseCurrencySeq': baseCurrencySeq
        };
        loadPageData('finance/localInvoice/searchLocalInvoiceFinancialChargeData', data, "POST").done(function (financialChargeDetail) {
            $("#loadFinancialCharge").html(financialChargeDetail);
            panel_toggle('chargeInfo');
        });
    }
}

function calculate() {
    if (form_validate("create")) {
        searchFormData('finance/localInvoice/calculate', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                rivets.bind($('#calculations'), {localInvoice: responseObject.object});
            }
        });
    }
}

function create_local_invoice() {
    if (form_validate("create")) {
        saveFormData('finance/localInvoice/createLocalInvoice', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                load_local_invoice_data_to_modal(responseObject.object.localInvoiceSeq);
                search_financial_charge_transport_booking();
                calculate();
            }
        });
    }
}

function update_local_invoice() {
    if (form_validate("update")) {
        var localInvoiceSeq = $('input#localInvoiceSeq').val();
        var responseObject = saveFormData('finance/localInvoice/updateLocalInvoice', 'update');
    }
}

function load_local_invoice_data_to_modal(localInvoiceSeq) {
    var data = {'localInvoiceSeq': localInvoiceSeq};
    loadPageData('finance/localInvoice/finalLocalInvoice', data, "POST").done(function (finalInvoiceData) {
        $("#localInvoiceData").html(finalInvoiceData);
        $('#modal .updateOperation').show();
        $('#modal .operations').show();
        $('#myModal').modal('toggle');
        initializeConfirmBox("modal");
    });
}

function delete_local_invoice() {
    var formId = $(this)[0].getAttribute('data-id');
    var localInvoiceSeq = $('#' + formId + ' :input[name=localInvoiceSeq]').val();
    var data = {'localInvoiceSeq': localInvoiceSeq};
    loadDataPost('finance/localInvoice/deleteByLocalInvoiceSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + localInvoiceSeq).remove();
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
        loadObjectData('finance/localInvoice/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
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
    loadObjectData('finance/localInvoice/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
        loadObjectData('finance/localInvoice/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
            loadObjectData('finance/localInvoice/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
                populate_dropdown('currencySeq', responseObject, 'currencySeq', 'currencyCode');
                if (stakeholder.currencySeq !== '' && stakeholder.currencySeq !== 0) {
                    $("#currencySeq").val(stakeholder.currencySeq);
                    $("#currencySeq").change();
                } else {
                    new_notification("Stakeholder Default Currency Not Found", 'error');
                    $("#currencySeq").val(exchangeRate.baseCurrencySeq);
                    $("#currencySeq").change();
                }
            });
        });
    });
}

function set_stakeholder_address(stakeholderSeq) {
    loadObjectData('finance/localInvoice/getAddressBookByStakeholderSeq/', stakeholderSeq, 'GET').done(function (addressBook) {
        $("#stakeholderAddress").html(cleanAddressBook(addressBook));
    });
}

function set_stakeholder_invoice_type(stakeholderSeq) {
    loadObjectData('finance/localInvoice/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
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
    loadObjectData('finance/localInvoice/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
        populate_dropdown('reportModel_reportSeq', responseObject, 'reportSeq', 'displayName');
        $("#reportOptionModel").modal("show");
    });
}

function view_local_invoice_report() {
    var localInvoiceSeq = $('#finalInvoice_localInvoiceSeq').val();
    var reportSeq = $('select#reportModel_reportSeq').val();
    var pdf = $('#reportModel_pdf:checked').val();
    var rtf = $('#reportModel_rtf:checked').val();
    var xls = $('#reportModel_xls:checked').val();
    var data = {
        'enableCancel': 1,
        'localInvoiceSeq': localInvoiceSeq,
        'reportSeq': reportSeq,
        'pdf': pdf,
        'rtf': rtf,
        'xls': xls};
    loadDataGet('finance/localInvoice/generateLocalInvoiceReport', data).done(function (responseObject) {
        window.open('finance/localInvoice/getFile/' + responseObject, '_blank');
    });
}

function panel_toggle(id) {
    $('#' + id).collapse({
        toggle: true
    });
}