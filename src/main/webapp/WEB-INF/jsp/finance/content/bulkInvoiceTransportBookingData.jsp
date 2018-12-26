<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript">
    $(function () {
        table = $("#transportBookingData").DataTable({
            fixedColumns: {
                "leftColumns": 2
            },
            scrollX: true,
            paging: false,
            fnDrawCallback: function () {
                editableInit();
            },
            aaSorting: [
                [0, 'desc']
            ]
        });
    });

    function load_finance_charge(referenceSeq, moduleSeq, targetType, referenceType) {
        window.open('#/finance/financialChargeManagement?referenceSeq=' + referenceSeq + '&moduleSeq=' + moduleSeq + '&targetType=' + targetType + '&referenceType=' + referenceType, '_blank');
    }

    function editableInit() {
        $('.chargeableKm').each(function () {
            var $this = $(this);
            $this.editable('finance/bulkInvoice/changeChargeableKm', {
                type: 'text',
                tooltip: 'Click to edit...',
                loadtype: 'POST',
                event: 'click',
                width: '100px',
                height: '20px',
                cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
                submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
                submitdata: {id: $this.parent().attr('id')},
                callback: function (data, settings) {
                    var transportBookingSeq = $this.parent().attr('id');
                    var responseData = $.parseJSON(data);
                    if (responseData.status === true) {
                        $(this).text(responseData.message);
                    } else {
                        new_notification(responseData.message, 'error')
                    }
                }
            });
        });
    }
</script>
<div class="col-md-12">
    <table class="table data-table-specific datatable" id="transportBookingData">
        <thead>
        <tr>
            <th width="5%">Booking Id</th>
            <th width="5%">Job No</th>
            <th width="5%">Vehicle Type</th>
            <th width="5%">Current Status</th>
            <%--32--%>
            <th width="6%">Transporter</th>
            <th width="6%">Driver</th>
            <th width="6%">Pickup</th>
            <th width="7%">Via Location</th>
            <th width="6%">Delivery</th>
            <%--46--%>
            <th width="4%">Reference No</th>
            <th width="5%">Document Collect</th>
            <th width="3%">Total Km</th>
            <th width="3%">Char. Km</th>
            <th width="4%">Remarks</th>
            <th width="3%"></th>
            <%--22--%>
        </tr>
        </thead>
        <tbody>
        <c:set var="index" value="${0}"/>
        <c:forEach items="${financialChargeList}" var="financialCharge">
            <tr id="${financialCharge.referenceSeq}">
                <td onclick="load_finance_charge('${financialCharge.referenceSeq}','${financialCharge.moduleSeq}','${financialCharge.targetType}','${financialCharge.referenceType}')" class="clickable">${financialCharge.transportBooking.bookingNo}</td>
                <td>${financialCharge.transportBooking.job.jobNo}</td>
                <td>${financialCharge.transportBooking.vehicleType.vehicleTypeName}</td>
                <td bgcolor="${financialCharge.transportBooking.transportBookingStatus.colorCode}">${financialCharge.transportBooking.transportBookingStatus.currentStatusDescription}</td>

                <td>${financialCharge.transportBooking.transportBookingVehicleList[0].transportCompany.stakeholderName}</td>
                <td>${financialCharge.transportBooking.transportBookingVehicleList[0].driver.employeeName}</td>
                <td bgcolor="#6699FF">${financialCharge.transportBooking.pickupLocation.destination}</td>
                <td bgcolor="#6699FF">${financialCharge.transportBooking.viaLocationString}</td>
                <td bgcolor="#6699FF">${financialCharge.transportBooking.deliveryLocation.destination}</td>

                <td>${financialCharge.transportBooking.customerReferenceNo}</td>
                <td><fmt:formatDate
                        value="${financialCharge.transportBooking.transportBookingFeedback.documentsCollectedDate}"
                        pattern="yyyy-MM-dd"/></td>
                <td bgcolor="#FF9999">${(financialCharge.transportBooking.transportBookingFeedback.cargoInHandKm eq null ? financialCharge.transportBooking.estimatedKm:financialCharge.transportBooking.transportBookingFeedback.cargoInHandKm) + financialCharge.transportBooking.transportBookingFeedback.placementKm}</td>
                <td bgcolor="#FF9999"
                    class="chargeableKm">${financialCharge.transportBooking.transportBookingFeedback.chargeableKm}</td>
                <td>${financialCharge.transportBooking.remarks}</td>
                <td><input type="checkbox" id="financialChargeSeq${index}" name="financialChargeSeq" checked="true"
                           value="${financialCharge.financialChargeSeq}" onchange="load_summed_charge()"/></td>
            </tr>
            <c:set var="index" value="${index + 1}"/>
        </c:forEach>
        </tbody>
    </table>
</div>

<input type="hidden" name="moduleSeq" value="${bulkInvoiceSearchAux.moduleSeq}"/>
<input type="hidden" name="departmentSeq" value="${bulkInvoiceSearchAux.departmentSeq}"/>
<input type="hidden" name="exchangeRateSourceType" value="${bulkInvoiceSearchAux.exchangeRateSourceType}"/>
<input type="hidden" name="exchangeRateSeq" value="${bulkInvoiceSearchAux.exchangeRateSeq}"/>
<input type="hidden" name="sourceBankSeq" value="${bulkInvoiceSearchAux.sourceBankSeq}"/>
<input type="hidden" name="currencySeq" value="${bulkInvoiceSearchAux.currencySeq}"/>
<input type="hidden" name="targetType" value="${bulkInvoiceSearchAux.targetType}"/>
<input type="hidden" name="dateFilterType" value="${bulkInvoiceSearchAux.dateFilterType}"/>
<input type="hidden" name="customerSeq" value="${bulkInvoiceSearchAux.customerSeq}"/>
<input type="hidden" name="shipperSeq" value="${bulkInvoiceSearchAux.shipperSeq}"/>
<input type="hidden" name="referenceType" value="${bulkInvoiceSearchAux.referenceType}"/>
<input type="hidden" name="vehicleSeq" value="${bulkInvoiceSearchAux.vehicleSeq}"/>
<input type="hidden" name="dateFilterType" value="${bulkInvoiceSearchAux.dateFilterType}"/>
<c:forEach items="${bulkInvoiceSearchAux.finalDestinationSeq}" var="finalDestination">
    <input type="hidden" name="finalDestinationSeq" value="${finalDestination}"/>
</c:forEach>
<input type="hidden" name="startDate"
       value="<fmt:formatDate value="${bulkInvoiceSearchAux.startDate}" pattern="yyyy-MM-dd"/>"/>
<input type="hidden" name="endDate"
       value="<fmt:formatDate value="${bulkInvoiceSearchAux.endDate}" pattern="yyyy-MM-dd"/>"/>