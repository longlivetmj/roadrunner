var formTemplate;
$(function () {
    formTemplate = $(".expenseVoucherForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    initializeCustomAjaxSelectWithTwoParam('transportBookingSeq', '/finance/expenseVoucher/findTransportBooking', 'moduleSeq', 'targetType');
    initializeCustomSelect('search_transportBookingSeq', '/finance/expenseVoucher/findTransportBooking', 'search_moduleSeq');
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
        loadPageData('finance/expenseVoucher/searchExchangeRateBankData', data, "POST").done(function (exchangeRate) {
            $("#loadExchangeRateBankData").html(exchangeRate);
            $('.exchangeRateBankExpenseVoucher').each(function () {
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
    loadPageData('finance/expenseVoucher/searchExchangeRate', data, "POST").done(function (exchangeRate) {
        $("#loadCommonExchangeRateData").html(exchangeRate);
        get_currency(exchangeRateSeq);
    });
}

function load_module_departments() {
    var moduleSeq = $('#search_moduleSeq').val();
    if (moduleSeq !== -1) {
        loadObjectData('finance/expenseVoucher/findModuleDepartments/', moduleSeq, "GET").done(function (departments) {
            populate_dropdown('search_departmentSeq', departments, 'departmentSeq', 'departmentName');
        });
    } else {
        $('#search_departmentSeq').selectpicker('refresh');
    }
}

function get_currency(exchangeRateSeq) {
    if (exchangeRateSeq !== null) {
        loadObjectData('finance/expenseVoucher/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
            populate_dropdown('baseCurrencySeq', responseObject, 'currencySeq', 'currencyCode');
            $('select#baseCurrencySeq').change();
        });
        loadObjectData('finance/expenseVoucher/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
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
    loadPageData('finance/expenseVoucher/searchExpenseVoucherTransportBooking', data, "POST").done(function (financialCharge) {
        $("#loadFinancialChargeTransportBooking").html(financialCharge);
        load_transport_booking_charge();
        panel_toggle('bookingInformation');
    });
}

function search_expense_voucher() {
    searchFormData('finance/expenseVoucher/searchExpenseVoucher', "searchForm").done(function (financialCharge) {
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
        loadPageData('finance/expenseVoucher/searchExpenseVoucherFinancialChargeData', data, "POST").done(function (financialChargeDetail) {
            $("#loadFinancialCharge").html(financialChargeDetail);
            panel_toggle('chargeInfo');
        });
    }
}

function calculate() {
    if (form_validate("create")) {
        searchFormData('finance/expenseVoucher/calculate', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                rivets.bind($('#calculations'), {expenseVoucher: responseObject.object});
            }
        });
    }
}

function create_expense_voucher() {
    if (form_validate("create")) {
        saveFormData('finance/expenseVoucher/createExpenseVoucher', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                load_expense_voucher_data_to_modal(responseObject.object.expenseVoucherSeq);
                search_financial_charge_transport_booking();
                calculate();
            }
        });
    }
}

function update_expense_voucher() {
    if (form_validate("update")) {
        var expenseVoucherSeq = $('input#expenseVoucherSeq').val();
        var responseObject = saveFormData('finance/expenseVoucher/updateExpenseVoucher', 'update');
    }
}

function load_expense_voucher_data_to_modal(expenseVoucherSeq) {
    var data = {'expenseVoucherSeq': expenseVoucherSeq};
    loadPageData('finance/expenseVoucher/finalExpenseVoucher', data, "POST").done(function (finalInvoiceData) {
        $("#expenseVoucherData").html(finalInvoiceData);
        $('#modal .updateOperation').show();
        $('#modal .operations').show();
        $('#myModal').modal('toggle');
        initializeConfirmBox("modal");
    });
}

function delete_expense_voucher() {
    var formId = $(this)[0].getAttribute('data-id');
    var expenseVoucherSeq = $('#' + formId + ' :input[name=expenseVoucherSeq]').val();
    var data = {'expenseVoucherSeq': expenseVoucherSeq};
    var responseObject = loadDataPost('finance/expenseVoucher/deleteByExpenseVoucherSeq', data);
    if (responseObject.status == true) {
        if (formId == 'modal') {
            $('#myModal').modal('toggle');
            new_notification(responseObject.message, 'success');
            $("#tr_" + expenseVoucherSeq).remove();
        } else {
            new_form(formTemplate, 'update', 'create');
            new_notification(responseObject.message, 'success');
        }
    } else {
        new_notification(responseObject.message, 'error');
    }
}

function get_initial_report(moduleSeq) {
    if (moduleSeq !== '') {
        loadObjectData('finance/expenseVoucher/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
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
    loadObjectData('finance/expenseVoucher/getCurrency/', exchangeRateSeq, 'GET').done(function (responseObject) {
        loadObjectData('finance/expenseVoucher/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
            loadObjectData('finance/expenseVoucher/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
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
    loadObjectData('finance/expenseVoucher/getAddressBookByStakeholderSeq/', stakeholderSeq, 'GET').done(function (addressBook) {
        $("#stakeholderAddress").html(cleanAddressBook(addressBook));
    });
}

function set_stakeholder_invoice_type(stakeholderSeq) {
    loadObjectData('finance/expenseVoucher/getStakeholder/', stakeholderSeq, 'GET').done(function (stakeholder) {
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
    loadObjectData('finance/expenseVoucher/getReportList/', moduleSeq, 'GET').done(function (responseObject) {
        populate_dropdown('reportModel_reportSeq', responseObject, 'reportSeq', 'displayName');
        $("#reportOptionModel").modal("show");
    });
}

function view_expense_voucher_report() {
    var expenseVoucherSeq = $('#finalInvoice_expenseVoucherSeq').val();
    var reportSeq = $('select#reportModel_reportSeq').val();
    var pdf = $('#reportModel_pdf:checked').val();
    var rtf = $('#reportModel_rtf:checked').val();
    var xls = $('#reportModel_xls:checked').val();
    var data = {
        'enableCancel': 1,
        'expenseVoucherSeq': expenseVoucherSeq,
        'reportSeq': reportSeq,
        'pdf': pdf,
        'rtf': rtf,
        'xls': xls};
    loadDataGet('finance/expenseVoucher/generateExpenseVoucherReport', data).done(function (responseObject) {
        window.open('finance/expenseVoucher/getFile/' + responseObject, '_blank');
    });
}

function panel_toggle(id) {
    $('#' + id).collapse({
        toggle: true
    });
}