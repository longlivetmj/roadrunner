package com.tmj.tms.fleet.integration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.tmj.tms.fleet.datalayer.modal.VehicleLocation;
import com.tmj.tms.fleet.datalayer.service.VehicleLocationService;
import com.wialon.core.Errors;
import com.wialon.core.EventHandler;
import com.wialon.core.EventProvider;
import com.wialon.core.Session;
import com.wialon.extra.UpdateSpec;
import com.wialon.item.Item;
import com.wialon.item.Unit;
import com.wialon.messages.UnitData;
import com.wialon.remote.handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Component
public class GeoIdDataSaver implements ApplicationListener, EventHandler {

    private final VehicleLocationService vehicleLocationService;
    private final FireBaseDataSaver fireBaseDataSaver;
    private final ServletContext servletContext;
    private Session session;

    @Autowired
    public GeoIdDataSaver(VehicleLocationService vehicleLocationService,
                          FireBaseDataSaver fireBaseDataSaver,
                          ServletContext servletContext) {
        this.vehicleLocationService = vehicleLocationService;
        this.fireBaseDataSaver = fireBaseDataSaver;
        this.servletContext = servletContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        this.session = Session.getInstance();
        this.login();
    }

    // Login to server
    private void login() {
        // initialize Wialon session
        session.initSession("http://hst-api.wialon.com");
        // trying login
        session.loginToken("22addabc4184d4611b3ee77d7fd64f8fD9BDFB4A36C9E77E9ADCFD2C2910EAFF69A25DEC", new ResponseHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                // login succeed
                System.out.println(String.format("Logged successfully. User name is %s", session.getCurrUser().getName()));
                //call update data flags
                updateDataFlags();
            }

            @Override
            public void onFailure(int errorCode, Throwable throwableError) {
                super.onFailure(errorCode, throwableError);
                // login failed, print error
                System.out.println(Errors.getErrorText(errorCode));
            }
        });
    }

    private void updateDataFlags() {
        //Create new update specification
        UpdateSpec updateSpec = new UpdateSpec();
        //Set update mode to add specified flags
        updateSpec.setMode(1);
        //Set update type by type
        updateSpec.setType("type");
        //Set update data to units
        updateSpec.setData(Item.ItemType.avl_unit);
        //Set update data flags
        updateSpec.setFlags(Item.dataFlag.base.getValue() | Unit.dataFlag.lastMessage.getValue());
        //Send update by created update specification

        session.updateDataFlags(new UpdateSpec[]{updateSpec}, new ResponseHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                // Update succeed
                System.out.println("Update data flags is successful");
                bindEventsHandler();
            }

            @Override
            public void onFailure(int errorCode, Throwable throwableError) {
                super.onFailure(errorCode, throwableError);
                // update data flags failed, print error
                System.out.println(Errors.getErrorText(errorCode));
            }
        });
    }

    private void bindEventsHandler() {
        //Get items from session
        Collection<Item> sessionItems = session.getItems();
        //Create events handler
        //Add handler to session
        session.addListener(this, EventProvider.events.All);
        if (sessionItems != null && sessionItems.size() > 0) {
            System.out.println(String.format("%d items added to Session\r\nStart binding event listeners to items", sessionItems.size()));
            //Add handler to items
            for (Item item : sessionItems)
                item.addListener(this, EventProvider.events.All);
            System.out.println("Event listeners successfully bound to items\r\nPress enter to logout and exit");
        }
        try {
            String actualReportPath = Paths.get(servletContext.getRealPath("firebase"), "serviceAccountKey.json").toString();
            FileInputStream serviceAccount = new FileInputStream(actualReportPath);
//            FileInputStream serviceAccount = new FileInputStream("E:\\IT Projects\\TMS\\src\\main\\resources\\firebase/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://my-project-1519143246409.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void onEvent(Enum anEnum, Object object, Object oldData, Object newData) {
        try {
            Unit unitData = (Unit) object;
            VehicleLocation vehicleLocation = this.vehicleLocationService.findOne(String.valueOf(unitData.getId()));
            if (vehicleLocation == null) {
                vehicleLocation = new VehicleLocation();
                vehicleLocation.setGpsTerminalKey(String.valueOf(unitData.getId()));
            }
            vehicleLocation.setLatitude(unitData.getPosition().getLatitude());
            vehicleLocation.setLongitude(unitData.getPosition().getLongitude());
            vehicleLocation.setDateAndTime(new Date());
            vehicleLocation.setSpeed(unitData.getPosition().getSpeed());

            Map<String, Object> params = null;
            Double mileage = 0.0;
            try {
                UnitData additionalData = (UnitData) newData;
                if (additionalData != null) {
                    params = additionalData.getParameters();
                    Object value = params.get("odometer");
                    if (value == null) {
                        value = params.get("mileage");
                    }
                    mileage = Double.parseDouble(value.toString());
                }
            } catch (Exception e) {
            }
            vehicleLocation.setMileage(mileage.intValue());
            vehicleLocation.setDirection(unitData.getPosition().getCourse());
            this.vehicleLocationService.save(vehicleLocation);
            this.fireBaseDataSaver.save(vehicleLocation);
        } catch (Exception ex) {
        }
    }
}
