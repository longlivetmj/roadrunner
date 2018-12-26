package com.tmj.tms.transport.utility;

import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.utility.PaymentMode;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.PackageTypeService;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.master.utility.UnitCategory;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.utility.TrueOrFalse;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class TransportBookingUtils {

    private final HttpSession httpSession;
    private final ModuleService moduleService;
    private final UnitService unitService;
    private final CurrencyService currencyService;
    private final PackageTypeService packageTypeService;

    @Autowired
    public TransportBookingUtils(HttpSession httpSession,
                                 ModuleService moduleService,
                                 UnitService unitService,
                                 CurrencyService currencyService,
                                 PackageTypeService packageTypeService) {
        this.httpSession = httpSession;
        this.moduleService = moduleService;
        this.unitService = unitService;
        this.currencyService = currencyService;
        this.packageTypeService = packageTypeService;
    }

    @SuppressWarnings("unchecked")
    public TransportBooking getDefaultBooking() {
        TransportBooking transportBooking = new TransportBooking();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        List<Department> departmentList = (List<Department>) this.httpSession.getAttribute("userDepartmentList" + moduleSeq);
        transportBooking.setCompanyProfileSeq(companyProfileSeq);
        transportBooking.setModuleSeq(moduleSeq);
        if (departmentList.size() > 0) {
            transportBooking.setDepartmentSeq(departmentList.get(0).getDepartmentSeq());
        }
        transportBooking.setCashOrCredit(StakeholderCashType.CREDIT.getStakeholderCashTypeSeq());
        transportBooking.setPaymentMode(PaymentMode.POINT_TO_POINT.getPaymentMode());
        transportBooking.setUowSeq(this.unitService.findByUnitName("KG").getUnitSeq());
        transportBooking.setUovSeq(this.unitService.findByUnitName("CBM").getUnitSeq());
        transportBooking.setInvoiceStatus(YesOrNo.Yes.getYesOrNoSeq());
        transportBooking.setCurrentStatus(BookingStatus.ACCEPTED.getCurrentStatus());
        return transportBooking;
    }

    public Model loadFormData(Model model) {
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        model.addAttribute("currencyList", this.currencyService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("cashCreditList", StakeholderCashType.values());
        model.addAttribute("departmentList", this.httpSession.getAttribute("userDepartmentList" + moduleSeq));
        model.addAttribute("packageUnitList", this.unitService.findByUsedForAndStatusOrderByUnitCodeAsc(UnitCategory.valueOf("PIECES").getUnitCategory(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("weightUnitList", this.unitService.findByUsedForAndStatusOrderByUnitCodeAsc(UnitCategory.valueOf("WEIGHT").getUnitCategory(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("volumeUnitList", this.unitService.findByUsedForAndStatusOrderByUnitCodeAsc(UnitCategory.valueOf("VOLUME").getUnitCategory(), MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("packageTypeList", this.packageTypeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("trueOrFalse", TrueOrFalse.values());
        model.addAttribute("paymentModeList", PaymentMode.values());
        model.addAttribute("cashCreditList", StakeholderCashType.values());
        model.addAttribute("dropPickList", DropPick.values());
        return model;
    }
}
