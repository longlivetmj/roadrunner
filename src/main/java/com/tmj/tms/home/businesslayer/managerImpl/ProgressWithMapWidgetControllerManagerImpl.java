package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleLocation;
import com.tmj.tms.fleet.datalayer.service.VehicleLocationService;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.home.businesslayer.manager.ProgressWithMapWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.KeyValuePairCustom;
import com.tmj.tms.home.datalayer.model.auxilary.ProgressWidgetWithLocations;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.utility.ConvertToJSON;
import com.tmj.tms.utility.LatLong;
import com.tmj.tms.utility.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressWithMapWidgetControllerManagerImpl implements ProgressWithMapWidgetControllerManager {

    private final HttpSession httpSession;
    private final WidgetService widgetService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final ModuleService moduleService;
    private final StakeholderService stakeholderService;
    private final FinalDestinationService finalDestinationService;
    private final VehicleLocationService vehicleLocationService;
    private final VehicleService vehicleService;

    @Autowired
    public ProgressWithMapWidgetControllerManagerImpl(HttpSession httpSession,
                                                      WidgetService widgetService,
                                                      TempRevenueAndCostService tempRevenueAndCostService,
                                                      ModuleService moduleService,
                                                      StakeholderService stakeholderService,
                                                      FinalDestinationService finalDestinationService,
                                                      VehicleLocationService vehicleLocationService,
                                                      VehicleService vehicleService) {
        this.httpSession = httpSession;
        this.widgetService = widgetService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.moduleService = moduleService;
        this.stakeholderService = stakeholderService;
        this.finalDestinationService = finalDestinationService;
        this.vehicleLocationService = vehicleLocationService;
        this.vehicleService = vehicleService;
    }

    @Override
    public ProgressWidgetWithLocations transportCustomerWiseRevenue(Integer widgetSeq) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        ConvertToJSON convertToJSON = new ConvertToJSON();
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        String[] colorList = {"danger", "success", "warning", "info", "default"};
        ProgressWidgetWithLocations progressWidget = this.initProgressWidget(widgetSeq);
        List<KeyValuePairCustom> keyValuePairCustomList = new ArrayList<>();
        Double totalValue = 0.0;
        List<Object[]> valueList = this.tempRevenueAndCostService.findCustomerWiseRevenue(companyProfileSeq, module.getModuleSeq());
        for (Object[] objects : valueList) {
            Double value = Double.parseDouble(objects[1].toString());
            totalValue = totalValue + value;
        }

        if (valueList.size() > 4) {
            for (int x = 0; x < 4; x++) {
                keyValuePairCustomList.add(this.populateKeyValuePairCustom(valueList, x, colorList, totalValue));
            }
            double others = 0.0;
            for (int x = 4; x < valueList.size(); x++) {
                others = others + Double.parseDouble(valueList.get(x)[1].toString());
            }
            KeyValuePairCustom keyValuePairCustom = new KeyValuePairCustom();
            keyValuePairCustom.setKey("Others");
            Double percentage = others * 100 / totalValue;
            keyValuePairCustom.setValue(String.valueOf(percentage.intValue()));
            String label = NumberFormatter.convertToMillion(others);
            keyValuePairCustom.setLabel(label);
            keyValuePairCustom.setColor(colorList[4]);
            keyValuePairCustomList.add(keyValuePairCustom);
        } else {
            for (int x = 0; x < valueList.size(); x++) {
                keyValuePairCustomList.add(this.populateKeyValuePairCustom(valueList, x, colorList, totalValue));
            }
        }
        progressWidget.setKeyValuePairCustomList(keyValuePairCustomList);

        List<VehicleLocation> vehicleLocationList = this.vehicleLocationService.findAll();
        List<LatLong> latLongList = new ArrayList<>();
        for (VehicleLocation vehicleLocation : vehicleLocationList) {
            Vehicle vehicle = this.vehicleService.findByGpsTerminalKey(vehicleLocation.getGpsTerminalKey());
            if (vehicle != null) {
                LatLong latLong = new LatLong();
                latLong.setLocation(vehicle.getVehicleNo());
                latLong.setLatitude(vehicleLocation.getLatitude());
                latLong.setLongitude(vehicleLocation.getLongitude());
                latLong.setSpeed(vehicleLocation.getSpeed());
                latLong.setDirection(vehicleLocation.getDirection());
                latLongList.add(latLong);
            }
        }
        progressWidget.setLatLongData(convertToJSON.convertToBasicJson(latLongList));
        return progressWidget;
    }

    private KeyValuePairCustom populateKeyValuePairCustom(List<Object[]> valueList, int x, String[] colorList, Double totalValue) {
        Integer stakeholderSeq = Integer.parseInt(valueList.get(x)[0].toString());
        KeyValuePairCustom keyValuePairCustom = new KeyValuePairCustom();
        keyValuePairCustom.setKey(this.stakeholderService.findOne(stakeholderSeq).getStakeholderName());
        Double percentage = Double.parseDouble(valueList.get(x)[1].toString()) * 100 / totalValue;
        keyValuePairCustom.setValue(String.valueOf(percentage.intValue()));
        String label = NumberFormatter.convertToMillion(Double.parseDouble(valueList.get(x)[1].toString()));
        keyValuePairCustom.setLabel(label);
        keyValuePairCustom.setColor(colorList[x]);
        return keyValuePairCustom;
    }

    private ProgressWidgetWithLocations initProgressWidget(Integer widgetSeq) {
        Widget widget = this.widgetService.findOne(widgetSeq);
        ProgressWidgetWithLocations progressWidget = new ProgressWidgetWithLocations();
        progressWidget.setHeading(widget.getHeading());
        progressWidget.setSubHeading(widget.getSubHeading());
        progressWidget.setMapHeading(widget.getWidgetText1());
        progressWidget.setMapSubHeading(widget.getWidgetText2());
        progressWidget.setWidget(widget);
        return progressWidget;
    }
}
