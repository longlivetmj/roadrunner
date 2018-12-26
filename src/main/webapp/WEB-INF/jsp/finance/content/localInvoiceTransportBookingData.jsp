<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="col-md-4">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Request Id</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.bookingNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Vehicle Type</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.vehicleType.vehicleTypeName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Transporter</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.transportBookingVehicleList[0].transportCompany.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Chargeable Km</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.transportBookingFeedback.chargeableKm}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Job No</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.job.jobNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Pickup</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.pickupLocation.destination}</label></h5>
            </div>
        </div>
        <div >
            <label class="col-md-4 col-xs-12 control-label">Vehicle</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.transportBookingVehicleList[0].vehicle.vehicleNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Payment Mode</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.paymentModeDescription}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Customer</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.customer.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Delivery</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.deliveryLocation.destination}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Via Locations</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${financialCharge.transportBooking.viaLocationString}</label></h5>
            </div>
        </div>
    </div>
    <input type="hidden" name="financialChargeSeq" id="financialChargeSeq" value="${financialCharge.financialChargeSeq}"/>
</div>
