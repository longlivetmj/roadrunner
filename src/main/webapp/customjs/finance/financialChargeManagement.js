var formTemplate;
$(function () {
    formTemplate = $(".financeChargeForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    var financialChargeSeq = $('#create_financialChargeSeq').val();
    if (financialChargeSeq === '' || financialChargeSeq === null) {
        showCreateOptions("create");
    } else {
        showUpdateOptions("create");
    }
});

function create_financial_charge() {
    if (form_validate("create")) {
        saveFormData('finance/financialChargeManagement/createFinancialCharge', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                getFinancialChargeByFinancialChargeSeq(responseObject.object.financialChargeSeq).done(function (financialCharge) {
                    transform_form(formTemplate, 'create', 'create', {financialCharge: financialCharge});
                });
            }
        });
    }
}

function update_finance_charge() {
    if (form_validate("create")) {
        saveFormData('finance/financialChargeManagement/updateFinancialCharge', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                getFinancialChargeByFinancialChargeSeq(responseObject.object.financialChargeSeq).done(function (financialCharge) {
                    transform_form(formTemplate, 'create', 'create', {financialCharge: financialCharge});
                });
            }
        });
    }
}

function delete_all_finance_charge() {
    if (confirm("Are you sure you want to Delete all the rows ?")) {
        saveFormData('finance/financialChargeManagement/deleteAllFinancialCharge', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                location.reload();
            }
        });
    }
}

function calculate_charge_amount(fieldName, obj) {
    var attrId = $(obj).attr("id");
    var chargeValue, qty, amount;
    var param = attrId.match(/\d+/);
    chargeValue = $('input[name="' + fieldName + '[' + param + '].chargeValue"]').val();
    qty = $('input[name="' + fieldName + '[' + param + '].quantity"]').val();
    amount = (chargeValue * qty );
    $('input[name="' + fieldName + '[' + param + '].amount"]').val(amount.toFixed(2));
}

function create_new_local_invoice() {
    var financialChargeSeq = $('#create_financialChargeSeq').val();
    if (financialChargeSeq !== '') {
        window.open('#/finance/localInvoice?financialChargeSeq=' + financialChargeSeq, '_blank');
    }
}

function create_new_expense_voucher() {
    var financialChargeSeq = $('#create_financialChargeSeq').val();
    if (financialChargeSeq !== '') {
        window.open('#/finance/expenseVoucher?financialChargeSeq=' + financialChargeSeq, '_blank');
    }
}

function getFinancialChargeByFinancialChargeSeq(financialChargeSeq) {
    return loadObjectData('finance/financialChargeManagement/findByFinancialChargeSeq/', financialChargeSeq, 'GET');
}
