var formTemplate;
$(function () {
    formTemplate = $(".employeeForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

function get_employee_list() {
    var designationSeq = $("#employeeDesignationSeq").val();
    loadObjectData('fleet/salaryAdvance/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeq', employeeList, 'employeeSeq', 'employeeName');
    });
}

function get_employee_list_search() {
    var designationSeq = $("#employeeDesignationSeq").val();
    loadObjectData('fleet/salaryAdvance/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeqSearch', employeeList, 'employeeSeq', 'employeeName');
        add_empty_option_to_dropdown('employeeSeqSearch', 'ALL');
    });
}

function load_report() {
    $("#reportOptionModel").modal("show");
}

function view_salary_advance_report() {
    var salaryAdvanceSeq = $('#report_salaryAdvanceSeq').val();
    var reportSeq = $('select#reportModel_reportSeq').val();
    var pdf = $('#reportModel_pdf:checked').val();
    var rtf = $('#reportModel_rtf:checked').val();
    var xls = $('#reportModel_xls:checked').val();
    var data = {
        'enableCancel': 1,
        'salaryAdvanceSeq': salaryAdvanceSeq,
        'reportSeq': reportSeq,
        'pdf': pdf,
        'rtf': rtf,
        'xls': xls};
    loadDataGet('fleet/salaryAdvance/generateSalaryAdvanceReport', data).done(function (responseObject) {
        window.open('fleet/salaryAdvance/getFile/' + responseObject, '_blank');
    });
}

function create_salary_advance() {
    if (form_validate("create")) {
        saveFormData('fleet/salaryAdvance/save', 'createSalaryAdvance').done(function (responseObject) {
            if (responseObject.status === true) {
                $("#salaryDetails").html('');
                $("#salaryDetails").hide();
                $("#report_salaryAdvanceSeq").val(responseObject.object.salaryAdvanceSeq);
                load_report();
            }
        });
    }
}

function search_salary() {
    if (form_validate("searchSalaryForm")) {
        searchFormData('fleet/salaryAdvance/searchSalary', 'searchSalaryForm').done(function (responseObject) {
            $("#salaryDetails").html(responseObject);
            $("#salaryDetails").show();
        });
    }
}

function search_salary_advance() {
    searchFormData('fleet/salaryAdvance/searchSalaryAdvance', 'searchForm').done(function (responseObject) {
        $("#salaryAdvanceData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function delete_salary_advance() {
    var salaryAdvanceSeq = $('#salaryAdvanceSeq').val();
    var data = {'salaryAdvanceSeq': salaryAdvanceSeq};
    loadDataPost('fleet/salaryAdvance/delete', data).done(function (responseObject) {
        if (responseObject.status === true) {
            $('#myModal').modal('toggle');
            new_notification(responseObject.message, 'success');
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load_commission_breakdown(chargeSeq) {
    var htmlData = $("#salaryCommissionBreakDown" + chargeSeq).html();
    $("#salaryCommissionModalBody").html(htmlData);
    $('#salaryCommissionModal').modal("show");
}

function calculate() {
    searchFormData('fleet/salaryAdvance/calculate', 'createSalaryForm').done(function (salary) {
        $("#basicSalary").val(salary.basicSalary);
        $("#netPay").val(salary.netPay);

        $("#allowance").val(salary.totalAllowance);
        $("#commission").val(salary.totalCommission);

        $("#deduction").val(salary.totalDeduction);
        $("#companyContribution").val(salary.totalCompanyContribution);
    });
}

function load_salary_advance(salaryAdvanceSeq) {
    loadObjectData('fleet/salaryAdvance/findBySalaryAdvanceSeq/', salaryAdvanceSeq, 'GET').done(function (salaryAdvance) {
        customJsonBinder({salaryAdvance: salaryAdvance}, 'modal');
        $('#myModal').modal("show");
        initializeConfirmBox("modal");
        $("#report_salaryAdvanceSeq").val(salaryAdvanceSeq);
    });
}