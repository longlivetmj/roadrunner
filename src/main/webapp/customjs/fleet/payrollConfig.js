var formTemplate;
$(function () {
    formTemplate = $(".employeeForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

function get_employee_list() {
    var designationSeq = $("#employeeDesignationSeq").val();
    loadObjectData('fleet/payrollConfig/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeq', employeeList, 'employeeSeq', 'employeeName');
    });
}

function create_payroll_config() {
    if (form_validate("create")) {
        saveFormData('fleet/payrollConfig/save', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/payrollConfig/findByPayrollSeq/', responseObject.object.payrollSeq, 'GET').done(function (payroll) {
                    transform_form(formTemplate, 'create', 'update', {payroll: payroll});
                    set_charge_type('update', payroll);
                });
            }
        });
    }
}

function update_payroll_config() {
    if (form_validate("update")) {
        saveFormData('fleet/payrollConfig/update', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/payrollConfig/findByPayrollSeq/', responseObject.object.payrollSeq, 'GET').done(function (payroll) {
                    transform_form(formTemplate, 'create', 'update', {payroll: payroll});
                    set_charge_type('update', payroll);
                });
            }
        });
    }
}

function set_charge_type(formName, payroll) {
    $("#" + formName + " .allowanceSeqList").selectpicker('destroy');
    $("#" + formName + " .allowanceSeqList option:selected").removeAttr("selected");
    $.each(payroll.payrollAllowanceList, function (i, allowance) {
        $("#" + formName + " .allowanceSeqList option[value='" + allowance.chargeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .allowanceSeqList").selectpicker('refresh');

    $("#" + formName + " .commissionSeqList").selectpicker('destroy');
    $("#" + formName + " .commissionSeqList option:selected").removeAttr("selected");
    $.each(payroll.payrollCommissionList, function (i, payrollCommission) {
        $("#" + formName + " .commissionSeqList option[value='" + payrollCommission.chargeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .commissionSeqList").selectpicker('refresh');

    $("#" + formName + " .deductionSeqList").selectpicker('destroy');
    $("#" + formName + " .deductionSeqList option:selected").removeAttr("selected");
    $.each(payroll.payrollDeductionList, function (i, deduction) {
        $("#" + formName + " .deductionSeqList option[value='" + deduction.chargeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .deductionSeqList").selectpicker('refresh');

    $("#" + formName + " .companyContributionSeqList").selectpicker('destroy');
    $("#" + formName + " .companyContributionSeqList option:selected").removeAttr("selected");
    $.each(payroll.payrollCompanyContributionList, function (i, companyContribution) {
        $("#" + formName + " .companyContributionSeqList option[value='" + companyContribution.chargeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .companyContributionSeqList").selectpicker('refresh');
}

function load_payroll_data() {
    var employeeName = $('input#search_employeeName').val();
    var status = $('select#search_status').val();
    var employeeDesignationSeq = $('select#search_employeeDesignationSeq').val();
    var data = {'employeeName': employeeName, 'status': status, 'employeeDesignationSeq': employeeDesignationSeq};
    loadPageData('fleet/payrollConfig/searchPayrollData', data, "POST").done(function (pageData) {
        $("#loadPayrollData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load_payroll_data_to_modal(payrollSeq) {
    loadObjectData('fleet/payrollConfig/findByPayrollSeq/', payrollSeq, 'GET').done(function (payroll) {
        display_update_modal(formTemplate, 'myModal', 'modal', {payroll: payroll});
        set_charge_type('modal', payroll);
    });
}

function update_payroll_config_modal() {
    if (form_validate("modal")) {
        saveFormData('fleet/payrollConfig/update', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_payroll_data_to_modal(responseObject.object.payrollSeq);
            }
        });
    }
}