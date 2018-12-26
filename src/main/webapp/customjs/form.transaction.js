/**
 * Created by Thilanga-Ehl on 9/5/2016.
 */

var _tc = $("meta[name='_csrf']").attr("content");
var _hc = $("meta[name='_csrf_header']").attr("content");

// Header
var headersStomp = {};
headersStomp[_hc] = _tc;

$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(_hc, _tc);
});

rivets.formatters.isNotEmpty = function (arr) {
    if (Array.isArray(arr) && arr.length > 0) {
        return arr;
    } else {
        return new Array(1);
    }
};


rivets.binders.setselected = function (el, value) {
    if (value != null) {
        $(el).val(value);
    } else {
        $(el).val('');
    }
};

rivets.binders.aftersave = function (el, value) {
    if (value != null) {
        $(el).val(value);
    }
};

rivets.binders.aftertextsave = function (el, value) {
    if (value != null) {
        $(el).text(value);
    }
};


rivets.binders.setchecked = function (el, value) {
    if (value == '1') {
        $(el).attr('checked', 'checked');
    }
};

rivets.binders.setselectedattr = function (el, value) {
    if (value != null) {
        $(el).attr('selected', 'selected');
    }
};

rivets.binders.ajaxvalue = function (el, value) {
    if (typeof value !== "undefined") {
        $(el).append($("<option selected></option>")
            .attr("value", value)
            .text(""));
    }
};

rivets.binders.ajaxtext = function (el, text) {
    if (typeof text !== "undefined") {
        $(el).find(":selected").text(text);
    }
};

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location);
    if (sPageURL.indexOf("?") > 0) {
        sPageURL = decodeURIComponent(window.location).split("?")[1];
        var sURLVariables = sPageURL.split('&');
        var sParameterName;
        var i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    }
};

rivets.formatters.viewUploadedFile = function (arr) {
    return arr;
};

function saveFormDataWithAttachments(url, formId, reset) {
    var entityJson = $('#' + formId).serializeJSON(); // js library to get json from form
    var entityJsonStr = JSON.stringify(entityJson);

    var formData = new FormData();
    formData.append("data", new Blob([entityJsonStr], {
        type: "application/json"  // ** specify that this is JSON**
    }));

    // append files, if there are any
    $.each($("#" + formId).find("input[type='file']"), function (i, tag) {
        $.each($(tag)[0].files, function (i, file) {
            formData.append(tag.name, file);
        });
    });

    $("#" + formId + " .text-uppercase").each(function () {
        $(this).val($(this).val().toUpperCase());
    });

    var returnObject = $.ajax({
        url: url,
        type: 'POST',
        processData: false,
        contentType: false,
        cache: false,
        data: formData
    }).done(function (data) {
        if (data !== '') {
            if (data.status) {
                noty({text: data.message, layout: 'topCenter', type: 'success'});
                if (reset === 'reset') {
                    $('#' + formId).trigger("reset");
                }
            } else {
                noty({text: data.message, layout: 'topCenter', type: 'error'});
            }
        } else {
            noty({text: 'Critical Error', layout: 'topCenter', type: 'error'});
        }
    }).fail(function (jqXHR, textStatus) {
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function new_notification(message, type, time) {
    var thisNoty = noty({text: message, layout: 'topCenter', type: type});
    if (time == undefined) {
        setTimeout(function () {
            $.noty.close(thisNoty.options.id);
        }, 15000);
    } else {
        setTimeout(function () {
            $.noty.close(thisNoty.options.id);
        }, time);
    }
}

function searchFormData(url, formId, reset) {
    var formData = new FormData(document.getElementById(formId));
    var returnObject = $.ajax({
        url: url,
        type: 'POST',
        processData: false,
        contentType: false,
        cache: false,
        data: formData
    }).fail(function (jqXHR, textStatus) {
        var errorObject = jqXHR.responseJSON;
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function saveFormData(url, formId, reset) {
    var thisNoty;
    $("#" + formId + " .text-uppercase").each(function () {
        $(this).val($(this).val().toUpperCase());
    });

    var formData = new FormData(document.getElementById(formId));
    var returnObject = $.ajax({
        url: url,
        type: 'POST',
        processData: false,
        contentType: false,
        cache: false,
        data: formData
    }).done(function (data) {
        if (data !== '') {
            if (data.status) {
                thisNoty = noty({text: data.message, layout: 'topCenter', type: 'success'});
                setTimeout(function () {
                    $.noty.close(thisNoty.options.id);
                }, 20000);
                if (reset === 'reset') {
                    $('#' + formId).trigger("reset");
                    $('#' + formId + ' .select').selectpicker('refresh');
                }
            } else {
                thisNoty = noty({text: data.message, layout: 'topCenter', type: 'error'});
            }
        } else {
            thisNoty = noty({text: 'Critical Error', layout: 'topCenter', type: 'error'});
        }
    }).fail(function (jqXHR, textStatus) {
        var errorObject = jqXHR.responseJSON;
        thisNoty = noty({text: (errorObject.message + errorObject.errors), layout: 'topCenter', type: 'error'});
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function loadDataPost(url, data) {
    var returnObject = $.ajax({
        url: url,
        type: "POST",
        data: data
    }).fail(function (jqXHR, textStatus) {
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function loadDataGet(url, data) {
    var returnObject = $.ajax({
        url: url,
        type: "GET",
        data: data
    }).fail(function (jqXHR, textStatus) {
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function loadObjectData(url, data, type) {
    var returnObject = $.ajax({
        url: url + data,
        type: type,
        dataType: 'json'
    }).fail(function (jqXHR, textStatus) {
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}

function loadPageData(url, data, type) {
    var returnObject = $.ajax({
        url: url,
        type: type,
        data: data,
        dataType: 'text'
    }).fail(function (jqXHR, textStatus) {
        if (jqXHR.status === 401) {
            noty({text: "Your session has expired !!", layout: 'topCenter', type: 'error'});
            window.location = '/login';
        } else if (jqXHR.status === 403) {
            noty({text: "Your session has expired. Please reload !!", layout: 'topCenter', type: 'error'});
        } else {
            console.error('we have a problem...');
        }
    });
    return returnObject;
}


function form_validate(formId) {
    var result = true;
    $('#' + formId).validator('validate');
    $('#' + formId + ' .form-group').each(function () {
        if ($(this).hasClass('has-error')) {
            result = false;
            return false;
        }
    });
    return result;
}

function validate_checked_one(formId) {
    var counter = 0;
    $("#" + formId).find('input[type=checkbox]').each(function () {
        if ($(this).is(':checked') == true) {
            counter++;
        }
    });

    if (counter == 0) {
        alert("Please select at least one");
        return false;
    } else {
        return true;
    }
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

(function ($) {
    $.fn.BindJson = function (fieldData, idPrefix) {
        var selectorPrefix = " #" + idPrefix,
            key = [],
            ctl = null,
            isTextField = false,
            fill = "";
        for (key in fieldData) {
            if (fieldData.hasOwnProperty(key)) {
                ctl = $(selectorPrefix + '_' + key, this);
                fill = fieldData[key];
                if (ctl.length != 0) {
                    $(ctl).each(function () {
                        isTextField = ($(this).is("DIV") || $(this).is("SPAN") || $(this).is("LABEL"));
                        if (isTextField) {
                            $(this).text(fill);
                        } else if ($(this).prop('type') == 'checkbox') {
                            $(this).prop('checked', true);
                        } else if ($(this).is('SELECT')) {
                            $(this).val(fill);
                            $(this).selectpicker('refresh');
                        } else {
                            $(this).val(fill);
                        }
                    });
                }
            }
        }
    };
})(jQuery);

function BindJsonByForm(formTemplate, object, newFormId) {
    $('#' + newFormId).html(formTemplate);
    $('#' + newFormId).trigger('reset');
    $('#' + newFormId).validator('destroy');

    $("#" + newFormId + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
        $(this).empty();
    });
    if (object != null) {
        delete_rows_except_first_from_auto_grow_table(newFormId);
        // $('#' + newFormId + ' input[type=hidden]').val('');
        rivets.bind($('#' + newFormId), object);
        index_auto_grow_table('auto-grow-table', newFormId);
    }
    $("#" + newFormId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });
    $('#' + newFormId + ' .createOperation').hide();
    $('#' + newFormId + ' .updateOperation').show();
    $('#' + newFormId + ' .operations').show();
    initializeConfirmBox(newFormId);
}

function customJsonBinder(object, newFormId) {
    if (object != null) {
        delete_rows_except_first_from_auto_grow_table(newFormId);
        // $('#' + newFormId + ' input[type=hidden]').val('');
        rivets.bind($('#' + newFormId), object);
        index_auto_grow_table('auto-grow-table', newFormId);
    }
    $("#" + newFormId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker('refresh');
    });
}

function populate_dropdown(elementId, data, key, value) {
    $("#" + elementId).empty();
    $.each(data, function (i, datax) {
        var o = new Option("option " + datax[value], datax[key]);
        $(o).html(datax[value]);
        $("#" + elementId).append(o);
    });
    $("#" + elementId).selectpicker('refresh');
}

function populate_dropdown_by_class(elementId, data, key, value) {
    $("#" + elementId).closest('select').empty();
    $.each(data, function (i, datax) {
        var o = new Option("option " + datax[value], datax[key]);
        $(o).html(datax[value]);
        $("#" + elementId).closest('select').append(o);
    });
    $("#" + elementId).closest('select').selectpicker('refresh');
}

function populate_dropdown_with_default_select(elementId, data, key, value) {
    $("#" + elementId).empty();
    $.each(data, function (i, datax) {
        var o;
        if (i == 0) {
            o = new Option("option " + datax[value], datax[key], true);
        }
        else {
            o = new Option("option " + datax[value], datax[key]);
        }
        $(o).html(datax[value]);
        $("#" + elementId).append(o);
    });
    $("#" + elementId).selectpicker('refresh');
}

function populate_dropdown_single_element(elementId, data, key, value) {
    $("#" + elementId).empty();
    var o = new Option("option " + data[value], data[key], true);
    $(o).html(data[value]);
    $("#" + elementId).append(o);
    $("#" + elementId).selectpicker('refresh');
}

function populate_ajax_dropdown_single_element(element, data, key, value) {
    $(element).empty();
    var o = new Option("option " + data[value], data[key], true);
    $(o).html(data[value]);
    $(element).append(o);
    $(element).selectpicker('refresh');
}

function populate_dropdown_single_element_with_secondary_value(elementId, data, key, value, secondaryValue) {
    $("#" + elementId).empty();
    var secondaryData = data[value];
    var o = new Option("option " + secondaryData[secondaryValue], data[key], true);
    $(o).html(secondaryData[secondaryValue]);
    $("#" + elementId).append(o);
    $("#" + elementId).selectpicker('refresh');
}

function add_option_to_dropdown(elementId, datax, key, value) {
    var o = new Option("option " + datax[value], datax[key]);
    $(o).html(datax[value]);
    $("#" + elementId).append(o);
    $("#" + elementId).selectpicker('refresh');
}

function add_empty_option_to_dropdown(elementId, displayValue) {
    var o = new Option("option " + displayValue, '');
    $(o).html(displayValue);
    $("#" + elementId).append(o);
    $("#" + elementId).selectpicker('refresh');
}

function dataTableSubmit(formId, tableId) {
    var $form = $("#" + formId);
    var table = $('#' + tableId).DataTable();
    // Iterate over all checkboxes in the table
    table.$('input[type="checkbox"]').each(function () {
        // If checkbox doesn't exist in DOM
        if (!$.contains(document, this)) {
            // If checkbox is checked
            if (this.checked) {
                // Create a hidden element
                $form.append(
                    $('<input>')
                        .attr('type', 'hidden')
                        .attr('name', this.name)
                        .val(this.value)
                );
            }
        }
    });
}

function load_modal(modalId, formId, object) {
    $('#' + formId).validator('destroy');
    $('#' + formId).find('.removeFromModal').remove();
    $('#' + formId).trigger("reset");

    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
    });
    if (object != null) {
        delete_rows_except_first_from_auto_grow_table(formId);
        $('#' + formId + ' input[type=hidden]').val('');
        rivets.bind($('#' + formId), object);
        index_auto_grow_table('auto-grow-table', formId);
    }
    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });

    $("#" + formId + " .datepicker").each(function () {
        $(this).attr("id", formId + "_" + $(this).attr("id"));
        $(this).datepicker({dateFormat: 'yy-mm-dd'});
    });

    $("#" + formId + " .datetimepicker").each(function () {
        $(this).attr("id", formId + "_" + $(this).attr("id"));
        $(this).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT'
        });
    });

    $('#' + formId + ' .operations').hide();
    $('#' + formId + ' .createOperation').hide();
    $('#' + formId + ' .updateOperation').show();
    $('#' + modalId).modal("show");
    initializeConfirmBox(formId);
}

function display_update_modal(formContent, modalId, formId, object) {
    $('#' + modalId + ' .modal-body').html(formContent);
    $('#' + formId).validator('destroy');
    $('#' + formId).find('.removeFromModal').remove();
    $('#' + formId).trigger("reset");

    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " .datepicker").each(function () {
        $(this).datepicker('destroy');
    });
    if (object != null) {
        delete_rows_except_first_from_auto_grow_table(formId);
        $('#' + formId + ' input[type=hidden]').val('');
        rivets.bind($('#' + formId), object);
        index_auto_grow_table('auto-grow-table', formId);
    }
    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });

    $("#" + formId + " .datepicker").each(function () {
        $(this).attr("id", formId + "_" + $(this).attr("id"));
        $(this).datepicker({dateFormat: 'yy-mm-dd'});
    });

    $("#" + formId + " .datetimepicker").each(function () {
        $(this).attr("id", formId + "_" + $(this).attr("id"));
        $(this).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT'
        });
    });

    $('#' + formId + ' .operations').hide();
    $('#' + formId + ' .createOperation').hide();
    $('#' + formId + ' .updateOperation').show();
    $('#' + modalId).modal("show");
    initializeConfirmBox(formId);
}

function reset_form(formTemplate, formId) {
    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId).html('');
    $('#' + formId).html(formTemplate);
    $("#" + formId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + formId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });

    $("#" + formId + " .datepicker").each(function () {
        $(this).datepicker({dateFormat: 'yy-mm-dd'});
    });

    $("#" + formId + " .datetimepicker").each(function () {
        $(this).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT'
        });
    });
}

function transform_form(formTemplate, initFormId, newFormId, object) {
    $("#" + initFormId).attr('id', newFormId);
    $('#' + newFormId).html(formTemplate);
    $('#' + newFormId).trigger('reset');
    $('#' + newFormId).validator('destroy');

    $("#" + newFormId + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + newFormId + " .datepicker").each(function () {
        $(this).datepicker('destroy');
    });
    if (object != null) {
        delete_rows_except_first_from_auto_grow_table(newFormId);
        $('#' + newFormId + ' input[type=hidden]').val('');
        rivets.bind($('#' + newFormId), object);
        index_auto_grow_table('auto-grow-table', newFormId);
    }
    $("#" + newFormId + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });

    $("#" + newFormId + " .datepicker").each(function () {
        $(this).datepicker({dateFormat: 'yy-mm-dd'});
    });

    $("#" + newFormId + " .datetimepicker").each(function () {
        $(this).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT'
        });
    });

    $('#' + newFormId + ' .createOperation').hide();
    $('#' + newFormId + ' .updateOperation').show();
    $('#' + newFormId + ' .operations').show();
    initializeConfirmBox(newFormId);
}

function delete_rows_except_first_from_auto_grow_table(formId) {
    $("#" + formId).find("table.auto-grow-table").each(function () {
        $(this).find("tbody > tr:gt(0)").remove();
    });

    $("#" + formId).find("table.auto-grow-table").each(function () {
        $(this).find(".select, .ajax-select").each(function () {
            $(this).selectpicker('destroy');
        });
    });
}

function new_form(formTemplate, initFormId, newFormId) {
    $("#" + initFormId).attr('id', newFormId);
    $('#' + newFormId).html(formTemplate);
    $('#' + newFormId).trigger('reset');
    $('#' + newFormId).validator('destroy');

    $('#' + newFormId + ' input[type=hidden]').val('');
    delete_rows_except_first_from_auto_grow_table(newFormId);
    $('#' + newFormId + ' .select').selectpicker('refresh');
    $("#" + newFormId + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });
    $('#' + newFormId + ' .createOperation').show();
    $('#' + newFormId + ' .updateOperation').hide();
    $('#' + newFormId + ' .operations').hide();
}

function add_table_row(tableClass, object) {
    var formId = $(object).closest("form").attr('id');
    var table = $('#' + formId + ' .' + tableClass)[0];
    var rowCount = $("#" + formId + " ." + tableClass + " tr").length;
    var colCount = table.rows[0].cells.length;
    $("#" + formId + " ." + tableClass + " .select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " ." + tableClass + " .ajax-select").each(function () {
        $(this).selectpicker('destroy');
    });
    $("#" + formId + " ." + tableClass + " .datepicker").each(function () {
        $(this).datepicker('destroy');
    });
    $("#" + formId + " ." + tableClass + " .datetimepicker").each(function () {
        $(this).datetimepicker('destroy');
    });

    var iterator = 1;
    if ($("#noOfRows") != null) {
        if ($("#noOfRows").val() > 1) {
            iterator = $("#noOfRows").val();
        }
    }

    for (var y = 0; y < iterator; y++) {
        var row = table.insertRow(rowCount);
        for (var i = 0; i < colCount; i++) {
            var newcell = row.insertCell(i);
            $(newcell).addClass("form-group");
            newcell.innerHTML = $(table).find('tbody tr:first').find('td:eq(' + i + ')').html();
            $(newcell).find(':input').each(function () {
                var element = this;
                var tagName = $(element).prop('tagName');
                var type = $(element).attr('type');
                switch (tagName != 'INPUT' ? tagName : type) {
                    case "text":
                        $(element).val("");
                        break;
                    case "TEXTAREA":
                        $(element).val("");
                        break;
                    case "checkbox":
                        $(element).attr("checked", false);
                        $(element).attr("disabled", false);
                        break;
                    case "SELECT":
                        // $(element).val("");
                        break;
                    case "file":
                        $(element).val("");
                        break;
                    case "hidden":
                        $(element).val("");
                        break;
                    case "button":
                        $(element).val("");
                        break;
                }
            });
        }
    }

    index_auto_grow_table(tableClass, formId);
    $("#" + formId + " ." + tableClass + " .select").each(function () {
        $(this).selectpicker('refresh');
    });
    $("#" + formId + " ." + tableClass + " .ajax-select").each(function () {
        $(this).selectpicker().ajaxSelectPicker();
    });
    $("#" + formId + " ." + tableClass + " .datepicker").each(function () {
        $(this).datepicker({dateFormat: 'yy-mm-dd'});
    });
    $("#" + formId + " ." + tableClass + " .datetimepicker").each(function () {
        $(this).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT'
        });
    });
}

function index_auto_grow_table(tableClass, formId) {
    var rowCount = $("#" + formId + " ." + tableClass + " > tbody > tr").length;
    var counter = 0;
    $("#" + formId + " ." + tableClass + " > tbody > tr").each(function () {
        $(this).find(":input").each(function () {
            try {
                if ($(this).attr("name") != null) {
                    $(this).attr("name", $(this).attr("name").replace(/\d+/, counter));
                }
                if ($(this).attr("id") != null) {
                    $(this).attr("id", $(this).attr("id").replace(/\d+/, counter));
                }
            } catch (e) {
                //alert(e);
            }
        });
        counter = counter + 1;
    });
    $("#" + formId).validator("update");
}

function delete_table_row(tableClass, object) {
    try {
        if (confirm("Are you sure you want to Delete the Selected row?")) {
            var formId = $(object).closest("form").attr('id');
            var table = $('#' + formId + ' .' + tableClass)[0];
            var rowCount = $(table).find('tbody > tr').length;
            for (var i = 0; i < rowCount; i++) {
                var row = $(table).find('tbody > tr:eq(' + i + ')');
                var chkbox = $(row).find("td:first").find("input:checkbox:first");
                if (chkbox != null && $(chkbox).is(":checked") == true) {
                    if (rowCount <= 1) {
                        alert("Cannot delete all the rows.");
                        break;
                    }
                    if (table.rows.length > 1) {
                        table.deleteRow(i + 1);
                        rowCount--;
                        i--;
                    }

                }
            }
            index_auto_grow_table(tableClass, formId);
        }
    } catch (e) {
        alert(e);
    }
}


function initializeConfirmBox(formId) {
    $('#' + formId + ' [data-toggle=confirmation]').confirmation({
        rootSelector: '[data-toggle=confirmation]',
    });
}

rivets.formatters.price = function (val) {
    var spl = String(val).split('.'),
        dollarsArray = spl[0].split(''),
        lastDollar = dollarsArray.length - 1,
        pow,
        i;

    if (dollarsArray.length > 3) {
        dollarsArray.reverse();
        for (i = lastDollar; i > -1; i--) {
            if (i % 3 === 0 && i !== 0) {
                dollarsArray.splice(i, 0, ',');
            }
        }
        spl[0] = dollarsArray.reverse().join('');
    }

    if (spl.length > 1) {
        spl[1] = spl[1].substr(0, 2);
        if (spl[1].length < 2) {
            spl[1] += '0';
        }
    } else {
        spl[1] = '00';
    }
    return spl.join('.');
};

function resizeIFrameToFitContent(iFrame) {

    iFrame.width = iFrame.contentWindow.document.body.scrollWidth;
    iFrame.height = iFrame.contentWindow.document.body.scrollHeight;
}

window.addEventListener('DOMContentReady', function (e) {

    var iframes = document.querySelectorAll("iframe");
    for (var i = 0; i < iframes.length; i++) {
        resizeIFrameToFitContent(iframes[i]);
    }
});
//rivets currency formatters for viewing
rivets.formatters.currency = function (val) {
    var value;
    var finalValue;
    var positiveValue = true;
    if (val >= 0) {
        value = val;
    } else {
        value = val * -1;
        positiveValue = false;
    }

    var spl = String(value).split('.'),
        dollarsArray = spl[0].split(''),
        lastDollar = dollarsArray.length - 1,
        pow,
        i;
    if (dollarsArray.length > 3) {
        dollarsArray.reverse();
        for (i = lastDollar; i > -1; i--) {
            if (i % 3 === 0 && i !== 0) {
                dollarsArray.splice(i, 0, ',');
            }
        }
        spl[0] = dollarsArray.reverse().join('');
    }
    if (spl.length > 1) {
        spl[1] = spl[1].substr(0, 2);
        if (spl[1].length < 2) {
            spl[1] += '0';
        }
    } else {
        spl[1] = '00';
    }

    if (positiveValue === false) {
        finalValue = '-' + spl.join('.');
    } else {
        finalValue = spl.join('.');
    }
    return finalValue;
};

function showCreateOptions(newFormId) {
    $('#' + newFormId + ' .createOperation').show();
    $('#' + newFormId + ' .updateOperation').hide();
    $('#' + newFormId + ' .operations').hide();
}

function showUpdateOptions(newFormId) {
    $('#' + newFormId + ' .createOperation').hide();
    $('#' + newFormId + ' .updateOperation').show();
    $('#' + newFormId + ' .operations').show();
}

function readonlyAllInputs(formId, status, statusDescription) {
    $('#' + formId + ' :input').attr('readonly', 'readonly');
    $('#' + formId + ' .select').prop('disabled', true);
    $('#' + formId + ' .ajax-select').prop('disabled', true);
    $('#' + formId + ' .datepicker').prop('disabled', true);
    $('#' + formId).find('button').prop('disabled', true);
    $('#' + formId + ' .alwaysEnabled').prop('disabled', false);
    var exists = $('#' + formId + ' :input[name="status"] option[value="' + status + '"]').length !== 0;
    if (!exists) {
        $('#' + formId + ' :input[name="status"]').empty();
        $('#' + formId + ' :input[name="status"]').append($("<option></option>")
            .attr("value", status)
            .text(statusDescription));
        $('#' + formId + ' :input[name="status"]').selectpicker('refresh');
        $('#' + formId + ' :input[name="status"]').prop('disabled', true);
        $('#' + formId + ' :input[name="updateButton"]').prop('disabled', true);
    } else {
        $('#' + formId + ' :input[name="status"]').prop('disabled', false);
    }
}

function removeReadonlyAllInputs(formId) {
    $('#' + formId + ' :input :not(.ignoreReadOnly)').attr('readonly', false);
    $('#' + formId + ' .select :not(.ignoreReadOnly)').prop('disabled', false);
    $('#' + formId + ' .ajax-select :not(.ignoreReadOnly)').prop('disabled', false);
    $('#' + formId + ' :input[name="status"]').show();
    $('#' + formId + ' :input[name="updateButton"]').show();
    $('#' + formId + ' .datepicker :not(.ignoreReadOnly)').prop('disabled', false);

    $('#' + formId).find('button').each(function () {
        $(this).prop('disabled', false);
    });
    $("#" + formId + " .alwaysEnabled").each(function () {
        $(this).prop('disabled', false);
    });
}

function appendDataTableHiddenElements(tableObject, formData) {
    var nNodes = tableObject.rows({page: 'all'}).nodes();
    $('td', nNodes).each(function (index, ncolumn) {
        if (typeof($("input", ncolumn).attr("name")) != "undefined") {
            if (!formData.has($("input", ncolumn).attr("name"))) {
                formData.append($("input", ncolumn).attr("name"), $("input", ncolumn).val());
            }
        }

        if (typeof($("textarea", ncolumn).attr("name")) != "undefined") {
            if (!formData.has($("textarea", ncolumn).attr("name"))) {
                formData.append($("textarea", ncolumn).attr("name"), $("textarea", ncolumn).val());
            }
        }

        if (typeof($("select", ncolumn).attr("name")) != "undefined") {
            if (!formData.has($("select", ncolumn).attr("name"))) {
                formData.append($("select", ncolumn).attr("name"), $("select", ncolumn).attr("value"));
            }
        }
    });
    return formData;
}

function initializeCustomSelect(elementId, url, secondParamId) {
    $('#' + elementId).selectpicker().ajaxSelectPicker({
        ajax: {
            url: url,
            type: 'GET',
            dataType: 'json',
            data: function () {
                var params = {
                    q: '{{{q}}}',
                    s: $('#' + secondParamId).val()
                };
                return params;
            }
        }
    })
}

function initializeAjaxSelect(elementId, url) {
    $('#' + elementId).selectpicker("destroy");
    $('#' + elementId).selectpicker().ajaxSelectPicker({
        ajax: {
            url: url,
            type: 'GET',
            dataType: 'json',
            data: function () {
                var params = {
                    q: '{{{q}}}'
                };
                return params;
            }
        }
    })
}

function initializeCustomAjaxSelectWithTwoParam(elementId, url, secondParamId, thirdParamId) {
    $('#' + elementId).selectpicker().ajaxSelectPicker({
        ajax: {
            url: url,
            type: 'GET',
            dataType: 'json',
            data: function () {
                var params = {
                    q: '{{{q}}}',
                    s: $('#' + secondParamId).val(),
                    r: $('#' + thirdParamId).val()
                };
                return params;
            }
        }
    })
}

function initializeCustomAjaxByClass(formId, classId, url, secondClassId) {
    $("#" + formId + " ." + classId).each(function () {
        if (!($(this).parent().hasClass("ignoreHack"))) {
            $(this).find('option').filter(function () {
                return !this.value || $.trim(this.value).length == 0 || $.trim(this.text).length == 0;
            }).remove();
        }
        $(this).selectpicker().ajaxSelectPicker({
            ajax: {
                url: url,
                type: 'GET',
                dataType: 'json',
                data: function () {
                    var secondParam = $('#' + formId + ' .' + secondClassId).map(function () {
                        return $(this).val();
                    }).get().join('');
                    var params = {
                        q: '{{{q}}}',
                        s: secondParam
                    };
                    return params;
                }
            }
        })
    });
}

function copy_table_row(tableName, rowNo, clickedButtonId, formId) {
    var tr = $('#' + clickedButtonId + rowNo).closest("tr");
    var newRow = $(tr).clone();
    var lastRow = $('.' + tableName + ' tr:last');
    $(lastRow).after(newRow);
    index_auto_grow_table(tableName, formId);
}

function copy_row(tableName, clickedButton) {
    var tr = $(clickedButton).closest("tr");
    var formId = $(clickedButton).closest("form");
    var newRow = $(tr).clone();
    var lastRow = $(clickedButton).closest("tr");
    $(lastRow).after(newRow);
    index_auto_grow_table(tableName, $(formId).attr("id"));
}


rivets.formatters.round = function (value, decimals) {
    if (value != '' && value != null) {
        if (decimals) {
            var exp = Math.pow(10, decimals);
            value = Math.round(value * exp) / exp;
        } else {
            value = Math.round(value);
        }
        value = parseFloat(value).toFixed(decimals);
    }
    return value;
};

function cleanAddressBook(addressBook) {
    var address = "";
    if (addressBook.address1 !== undefined && addressBook.address1 !== null) {
        address += addressBook.address1 + "\n";
    }
    if (addressBook.address2 !== undefined && addressBook.address2 !== null) {
        address += addressBook.address2 + "\n";
    }
    if (addressBook.city !== undefined && addressBook.city !== null) {
        address += addressBook.city + "\n";
    }
    if (addressBook.state !== undefined && addressBook.state !== null) {
        address += addressBook.state + "\n";
    }
    if (addressBook.zip !== undefined && addressBook.zip !== null) {
        address += addressBook.zip + "\n";
    }
    if (addressBook.country !== undefined && addressBook.country !== null && addressBook.country.countryName !== undefined && addressBook.country.countryName !== null) {
        address += addressBook.country.countryName + "\n";
    }
    if (addressBook.telephone !== undefined && addressBook.telephone !== null) {
        address += addressBook.telephone + "\n";
    }
    if (addressBook.fax !== undefined && addressBook.fax !== null) {
        address += addressBook.fax + "\n";
    }
    return address.trim();
}


function validateFloatKeyPress(el, param) {
    if (el.value !== '' && el.value !== null) {
        var v = parseFloat(el.value);
        el.value = (isNaN(v)) ? '' : v.toFixed(param);
    }
}

function validateIntegerKeyPress(el) {
    if (el.value !== '' && el.value !== null) {
        var v = parseInt(el.value);
        el.value = (isNaN(v)) ? '' : v;
    }
}

function limitIntegerKeyPressValue(el, max) {
    if (el.value !== '' && el.value !== null) {
        var v = parseFloat(el.value);
        el.value = (isNaN(v)) ? '' : v;
        if (v > max) {
            el.value = max;
        }
    }
}

function view_hide(elementId) {
    $("#" + elementId).toggle();
}

function pinSymbol(color) {
    return {
        path: 'M 0,0 C -2,-20 -10,-22 -10,-30 A 10,10 0 1,1 10,-30 C 10,-22 2,-20 0,0 z',
        fillColor: color,
        fillOpacity: 1,
        strokeColor: '#000',
        strokeWeight: 2,
        scale: 2
    };
}

function convertToCurrency(val) {
    var value;
    var finalValue;
    var positiveValue = true;
    if (val >= 0) {
        value = val;
    } else {
        value = val * -1;
        positiveValue = false;
    }

    var spl = String(value).split('.'),
        dollarsArray = spl[0].split(''),
        lastDollar = dollarsArray.length - 1,
        pow,
        i;
    if (dollarsArray.length > 3) {
        dollarsArray.reverse();
        for (i = lastDollar; i > -1; i--) {
            if (i % 3 === 0 && i !== 0) {
                dollarsArray.splice(i, 0, ',');
            }
        }
        spl[0] = dollarsArray.reverse().join('');
    }
    if (spl.length > 1) {
        spl[1] = spl[1].substr(0, 2);
        if (spl[1].length < 2) {
            spl[1] += '0';
        }
    } else {
        spl[1] = '00';
    }

    if (positiveValue === false) {
        finalValue = '-' + spl.join('.');
    } else {
        finalValue = spl.join('.');
    }
    return finalValue;
}