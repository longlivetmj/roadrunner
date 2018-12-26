$.editable.addInputType('datetimepicker', {
    element: function (settings, original) {
        var input = $('<input>');
        if (settings.width != 'none') {
            input.width(settings.width);
        }
        if (settings.height != 'none') {
            input.height(settings.height);
        }
        input.attr('autocomplete', 'off');
        $(this).append(input);
        return (input);
    },
    plugin: function (settings, original) {
        /* Workaround for missing parentNode in IE */
        var currDate = new Date();
        var currHour = currDate.getHours() - 2;
        currDate.setHours(currHour, 0, 0);
        var form = this;
        settings.onblur = 'ignore';
        $(this).find('input').datetimepicker({
            controlType: 'select',
            dateFormat: 'yy-mm-dd',
            timeFormat: 'hh:mm TT',
            numberOfMonths: 1
            //minDate: currDate
        }).bind('click', function () {
            $(this).datetimepicker('show');
            return false;
        }).bind('dateSelected', function (e, selectedDate, $td) {
            $(form).submit();
        });
    }
});// JavaScript Document

$.editable.addInputType('datepicker', {
    element: function (settings, original) {
        var input = $('<input>');
        if (settings.width != 'none') {
            input.width(settings.width);
        }
        if (settings.height != 'none') {
            input.height(settings.height);
        }
        input.attr('autocomplete', 'off');
        $(this).append(input);
        return (input);
    },
    plugin: function (settings, original) {
        /* Workaround for missing parentNode in IE */
        var form = this;
        settings.onblur = 'ignore';
        $(this).find('input').datepicker({
            controlType: 'select',
            dateFormat: 'yy-mm-dd',
            numberOfMonths: 1
        }).bind('click', function () {
            $(this).datepicker('show');
            return false;
        }).bind('dateSelected', function (e, selectedDate, $td) {
            $(form).submit();
        });
    }
});

$.editable.addInputType('supplierNegotiation', {
    element: function (settings, original) {
        var valueEnter = $('<input type="text" id="supplierNegotiationValue_" style="width: 75px" onkeypress="return isNumber(event,this.id)">');
        var typeSelect = $('<select id="supplierNegotiationType_">');
        var remarks = $('<textarea id="supplierNegotiationRemarks_">');
        typeSelect.append($('<option>').val("Transport Rate").append("Transport Rate"));
        typeSelect.append($('<option>').val("Additional KM").append("Additional KM"));
        typeSelect.append($('<option>').val("Labour Charge").append("Labour Charge"));

        $(this).append(valueEnter);
        $(this).append(typeSelect);
        $(this).append(remarks);

        /* Hidden input to store value which is submitted to server. */
        var hidden = $('<input type="hidden">');
        $(this).append(hidden);
        return (hidden);
    },
    submit: function (settings, original) {
        var value = $("#supplierNegotiationValue_").val() + "~" + $("#supplierNegotiationType_").val() + "~" + $("#supplierNegotiationRemarks_").val();
        $("input", this).val(value);
    },
    content: function (string, settings, original) {
        $("#supplierNegotiationValue_").val("");
        $("#supplierNegotiationRemarks_").val("");
    }
});

$.editable.addInputType('finalDestination', {
    element: function (settings, original) {
        var select = $('<select id="tempFinalDestinationSeq"' +
            ' data-validate="true"' +
            ' data-abs-ajax-type="get"' +
            ' data-abs-request-delay="500"' +
            ' data-key="finalDestinationSeq"' +
            ' data-value="destination"' +
            ' data-subText="location.locationName"' +
            ' data-abs-ajax-url="transport/transportBookingStatus/findFinalDestination" ' +
            ' data-live-search="true" ' +
            ' style="width: 250px"  />');
        $(this).append(select);
        return (select);
    },
    plugin: function (settings, original) {
        settings.onblur = 'ignore';
        $("#tempFinalDestinationSeq").selectpicker().ajaxSelectPicker();
    }
});

$.editable.addInputType('driver', {
    element: function (settings, original) {
        var transportBookingSeq = $(original).parent().attr("id");
        var select = $('<select id="tempDriverSeq"' +
            ' data-validate="true"' +
            ' data-abs-ajax-type="get"' +
            ' data-abs-request-delay="500"' +
            ' data-key="employeeSeq"' +
            ' data-value="employeeName"' +
            ' data-abs-ajax-url="transport/vehicleAssignment/findDriver/' + transportBookingSeq + '" ' +
            ' data-live-search="true" ' +
            ' style="width: 250px"  />');
        $(this).append(select);
        return (select);
    },
    plugin: function (settings, original) {
        settings.onblur = 'ignore';
        $("#tempDriverSeq").selectpicker().ajaxSelectPicker();
    }
});

$.editable.addInputType('helper', {
    element: function (settings, original) {
        var transportBookingSeq = $(original).parent().attr("id");
        var select = $('<select id="tempHelperSeq"' +
            ' data-validate="true"' +
            ' data-abs-ajax-type="get"' +
            ' data-abs-request-delay="500"' +
            ' data-key="employeeSeq"' +
            ' data-value="employeeName"' +
            ' data-abs-ajax-url="transport/vehicleAssignment/findHelper/' + transportBookingSeq + '" ' +
            ' data-live-search="true" ' +
            ' style="width: 250px"  />');
        $(this).append(select);
        return (select);
    },
    plugin: function (settings, original) {
        settings.onblur = 'ignore';
        $("#tempHelperSeq").selectpicker().ajaxSelectPicker();
    }
});