var formTemplate;
$(function () {
    formTemplate = $(".employeeForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

function get_employee_list() {
    var designationSeq = $("#employeeDesignationSeq").val();
    loadObjectData('fleet/employeeSalary/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeq', employeeList, 'employeeSeq', 'employeeName');
    });
}

function get_employee_list_search() {
    var designationSeq = $("#employeeDesignationSeqSearch").val();
    loadObjectData('fleet/employeeSalary/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeqSearch', employeeList, 'employeeSeq', 'employeeName');
        add_empty_option_to_dropdown('employeeSeqSearch', 'ALL');
    });
}

function create_salary() {
    if (form_validate("create")) {
        saveFormData('fleet/employeeSalary/save', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                $("#salaryDetails").html('');
                $("#salaryDetails").hide();
                $("#report_salarySeq").val(responseObject.object.salarySeq);
                load_report();
            }
        });
    }
}

function search_salary_history() {
    searchFormData('fleet/employeeSalary/searchSalaryData', 'searchForm').done(function (responseObject) {
        $("#salaryData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function search_salary() {
    if (form_validate("searchSalaryForm")) {
        searchFormData('fleet/employeeSalary/searchSalary', 'searchSalaryForm').done(function (responseObject) {
            $("#salaryDetails").html(responseObject);
            $("#salaryDetails").show();
        });
    }
}

function load_salary_data_to_modal(salarySeq) {
    loadObjectData('fleet/employeeSalary/findBySalarySeq/', salarySeq, 'GET').done(function (salary) {
        customJsonBinder({salary: salary}, 'modal');
        $('#myModal').modal("show");
        initializeConfirmBox("modal");
        $("#report_salarySeq").val(salary.salarySeq);
    });
}

function delete_salary() {
    var salarySeq = $('#salarySeq').val();
    var data = {'salarySeq': salarySeq};
    loadDataPost('fleet/employeeSalary/delete', data).done(function (responseObject) {
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
    searchFormData('fleet/employeeSalary/calculate', 'create').done(function (salary) {
        $("#basicSalary").text(convertToCurrency(salary.basicSalary));
        $("#netPay").text(convertToCurrency(salary.netPay));

        $("#allowance").text(convertToCurrency(salary.totalAllowance));
        $("#commission").text(convertToCurrency(salary.totalCommission));

        $("#deduction").text(convertToCurrency(salary.totalDeduction));
        $("#companyContribution").text(convertToCurrency(salary.totalCompanyContribution));

        $("#salaryAdvance").text(convertToCurrency(salary.totalSalaryAdvance));
    });
}

function load_report() {
    $("#reportOptionModel").modal("show");
}

function view_salary_report() {
    var salarySeq = $('#report_salarySeq').val();
    var reportSeq = $('select#reportModel_reportSeq').val();
    var pdf = $('#reportModel_pdf:checked').val();
    var rtf = $('#reportModel_rtf:checked').val();
    var xls = $('#reportModel_xls:checked').val();
    var data = {
        'enableCancel': 1,
        'salarySeq': salarySeq,
        'reportSeq': reportSeq,
        'pdf': pdf,
        'rtf': rtf,
        'xls': xls};
    loadDataGet('fleet/employeeSalary/generateSalaryReport', data).done(function (responseObject) {
        window.open('fleet/employeeSalary/getFile/' + responseObject, '_blank');
    });
}