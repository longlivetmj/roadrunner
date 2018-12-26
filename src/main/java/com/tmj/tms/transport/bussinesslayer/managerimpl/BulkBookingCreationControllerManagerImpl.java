package com.tmj.tms.transport.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.utility.ChargesCalculator;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.EmployeeDesignationService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.VehicleTypeService;
import com.tmj.tms.master.utility.AddressBookUtils;
import com.tmj.tms.transport.bussinesslayer.manager.BulkBookingCreationControllerManager;
import com.tmj.tms.transport.bussinesslayer.manager.TransportBookingManagementControllerManager;
import com.tmj.tms.transport.datalayer.modal.*;
import com.tmj.tms.transport.datalayer.modal.auxiliary.BulkBookingSearch;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingTemplate1;
import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingViaLocationAux;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingVehicleService;
import com.tmj.tms.transport.datalayer.service.TransportBulkBookingService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.transport.utility.DropPick;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BulkBookingCreationControllerManagerImpl implements BulkBookingCreationControllerManager {

    private final UploadedDocumentService uploadedDocumentService;
    private final TransportBulkBookingService transportBulkBookingService;
    private final FinalDestinationService finalDestinationService;
    private final ServletContext servletContext;
    private final VehicleTypeService vehicleTypeService;
    private final VehicleService vehicleService;
    private final EmployeeService employeeService;
    private final EmployeeDesignationService employeeDesignationService;
    private final HttpSession httpSession;
    private final TransportBookingManagementControllerManager transportBookingManagementControllerManager;
    private final TransportBookingVehicleService transportBookingVehicleService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;
    private final DistanceCalculatorService distanceCalculatorService;
    private final ChargesCalculator chargesCalculator;

    @Autowired
    public BulkBookingCreationControllerManagerImpl(UploadedDocumentService uploadedDocumentService,
                                                    TransportBulkBookingService transportBulkBookingService,
                                                    FinalDestinationService finalDestinationService,
                                                    ServletContext servletContext,
                                                    VehicleTypeService vehicleTypeService,
                                                    VehicleService vehicleService,
                                                    EmployeeService employeeService,
                                                    EmployeeDesignationService employeeDesignationService,
                                                    HttpSession httpSession,
                                                    TransportBookingManagementControllerManager transportBookingManagementControllerManager,
                                                    TransportBookingVehicleService transportBookingVehicleService,
                                                    TransportBookingFeedbackService transportBookingFeedbackService,
                                                    DistanceCalculatorService distanceCalculatorService,
                                                    ChargesCalculator chargesCalculator) {
        this.uploadedDocumentService = uploadedDocumentService;
        this.transportBulkBookingService = transportBulkBookingService;
        this.finalDestinationService = finalDestinationService;
        this.servletContext = servletContext;
        this.vehicleTypeService = vehicleTypeService;
        this.vehicleService = vehicleService;
        this.employeeService = employeeService;
        this.employeeDesignationService = employeeDesignationService;
        this.httpSession = httpSession;
        this.transportBookingManagementControllerManager = transportBookingManagementControllerManager;
        this.transportBookingVehicleService = transportBookingVehicleService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
        this.distanceCalculatorService = distanceCalculatorService;
        this.chargesCalculator = chargesCalculator;
    }


    @Override
    public ResponseObject upload(TransportBulkBooking transportBulkBooking, MultipartFile multipartFile) {
        ResponseObject responseObject = new ResponseObject("Files Processed Successfully", true);
        try {
            List<TransportBookingTemplate1> transportBookingTemplate1List;
            Integer driverDesignationSeq = this.employeeDesignationService.findByDesignation("DRIVER").getEmployeeDesignationSeq();
            Integer helperDesignationSeq = this.employeeDesignationService.findByDesignation("HELPER").getEmployeeDesignationSeq();
            if (multipartFile.getBytes().length > 0) {
                UploadedDocument file = new UploadedDocument();
                file.setFileData(multipartFile.getBytes());
                file.setFileName(multipartFile.getOriginalFilename().trim().replace(',', ' '));
                file.setFileType(multipartFile.getContentType());
                Integer uploadedDocumentSeq = this.uploadedDocumentService.save(file);
                transportBulkBooking.setUploadDocumentSeq(uploadedDocumentSeq);
                String filePath = Paths.get(servletContext.getRealPath("/excelToEntity"), "TransportBookingTemplate1.xml").toString();
                transportBookingTemplate1List = ExcelToEntity.parseXLSInputStreamToBeans(new ByteArrayInputStream(file.getFileData()), new File(filePath));
                if (transportBookingTemplate1List.size() > 0) {
                    boolean noErrors = true;
                    Set<String> errorList = new LinkedHashSet<>();
                    List<TransportBooking> transportBookingList = new ArrayList<>();
                    int lineNumber = 2;
                    for (TransportBookingTemplate1 transportBookingTemplate1 : transportBookingTemplate1List) {
                        TransportBooking transportBooking = this.setDefaultValues(transportBulkBooking);
                        Set<ConstraintViolation<TransportBookingTemplate1>> errors = Validation.buildDefaultValidatorFactory().getValidator().validate(transportBookingTemplate1);
                        if (errors.size() > 0) {
                            String errorMessage = ErrorMessageCreator.errorsInRow((HashSet<?>) errors, lineNumber);
                            errorList.add(errorMessage);
                            noErrors = false;
                        } else {
                            transportBooking.setRequestedArrivalTime(transportBookingTemplate1.getArrivalTime());
                            transportBooking.setRequestedDeliveryTime(transportBookingTemplate1.getDeliveryTime());
                            transportBooking.setRemarks(transportBookingTemplate1.getRemarks());
                            if (transportBookingTemplate1.getActualStartLocation() != null) {
                                List<FinalDestination> actualStartList = this.locationFinder(transportBookingTemplate1.getActualStartLocation(), transportBooking.getCompanyProfileSeq());
                                if (actualStartList.size() > 0) {
                                    transportBooking.setActualStartLocationSeq(actualStartList.get(0).getFinalDestinationSeq());
                                } else {
                                    errorList.add("Cannot find Actual Start Location " + transportBookingTemplate1.getActualStartLocation() + " at row " + lineNumber);
                                }
                            }

                            List<FinalDestination> pickupLocationList = this.locationFinder(transportBookingTemplate1.getPickupLocation(), transportBooking.getCompanyProfileSeq());
                            if (pickupLocationList.size() > 0) {
                                transportBooking.setPickupLocationSeq(pickupLocationList.get(0).getFinalDestinationSeq());
                                transportBooking.setPickupLocationAddress(AddressBookUtils.cleanAddressBook(pickupLocationList.get(0).getAddressBook()));
                            } else {
                                errorList.add("Cannot find Pickup Location " + transportBookingTemplate1.getPickupLocation() + " at row " + lineNumber);
                            }

                            List<FinalDestination> deliveryLocationList = this.locationFinder(transportBookingTemplate1.getDeliveryLocation(), transportBooking.getCompanyProfileSeq());
                            if (deliveryLocationList.size() > 0) {
                                transportBooking.setDeliveryLocationSeq(deliveryLocationList.get(0).getFinalDestinationSeq());
                                transportBooking.setDeliveryLocationAddress(AddressBookUtils.cleanAddressBook(deliveryLocationList.get(0).getAddressBook()));
                            } else {
                                errorList.add("Cannot find Delivery Location " + transportBookingTemplate1.getDeliveryLocation() + " at row " + lineNumber);
                            }

                            if (transportBookingTemplate1.getActualEndLocation() != null) {
                                List<FinalDestination> actualEndList = this.locationFinder(transportBookingTemplate1.getActualEndLocation(), transportBooking.getCompanyProfileSeq());
                                if (actualEndList.size() > 0) {
                                    transportBooking.setActualEndLocationSeq(actualEndList.get(0).getFinalDestinationSeq());
                                } else {
                                    errorList.add("Cannot find Actual End Location " + transportBookingTemplate1.getActualEndLocation() + " at row " + lineNumber);
                                }
                            }

                            List<VehicleType> vehicleTypeList = this.vehicleTypeService.findByVehicleTypeNameContainingIgnoreCaseAndStatus(transportBookingTemplate1.getVehicleType(), MasterDataStatus.APPROVED.getStatusSeq());
                            if (vehicleTypeList.size() > 0) {
                                transportBooking.setVehicleTypeSeq(vehicleTypeList.get(0).getVehicleTypeSeq());
                            } else {
                                errorList.add("Cannot find Vehicle Type " + transportBookingTemplate1.getVehicleType() + " at row " + lineNumber);
                            }
                            transportBooking.setCustomerReferenceNo(transportBookingTemplate1.getCustomerReferenceNo());

                            TransportBookingVehicle transportBookingVehicle = new TransportBookingVehicle();
                            List<Vehicle> vehicleList = this.vehicleService.findByVehicleNoContainingIgnoreCaseAndStatusAndCompanyProfileSeq(transportBookingTemplate1.getVehicleNo(), MasterDataStatus.APPROVED.getStatusSeq(), transportBooking.getCompanyProfileSeq());
                            if (vehicleList.size() > 0) {
                                transportBookingVehicle.setTransportCompanySeq(vehicleList.get(0).getStakeholderSeq());
                                transportBookingVehicle.setVehicleSeq(vehicleList.get(0).getVehicleSeq());
                                transportBookingVehicle.setDriverSeq(vehicleList.get(0).getDefaultDriverSeq());
                                transportBookingVehicle.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                            } else {
                                errorList.add("Cannot find Vehicle No " + transportBookingTemplate1.getVehicleNo() + " at row " + lineNumber);
                            }

                            List<Employee> driverList = this.employeeService.findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                                    ExcelToEntity.split2(transportBookingTemplate1.getDriverName(), 2),
                                    MasterDataStatus.APPROVED.getStatusSeq(),
                                    driverDesignationSeq,
                                    transportBooking.getCompanyProfileSeq(),
                                    transportBookingVehicle.getTransportCompanySeq());
                            if (driverList.size() > 0) {
                                transportBookingVehicle.setDriverSeq(driverList.get(0).getEmployeeSeq());
                            }
                            if (transportBookingTemplate1.getCleaner() != null) {
                                List<Employee> helperList = this.employeeService.findByEmployeeNameContainingIgnoreCaseAndStatusAndEmployeeDesignationSeqAndCompanyProfileSeqAndStakeholderSeq(
                                        ExcelToEntity.split2(transportBookingTemplate1.getCleaner(), 2),
                                        MasterDataStatus.APPROVED.getStatusSeq(),
                                        helperDesignationSeq,
                                        transportBooking.getCompanyProfileSeq(),
                                        transportBookingVehicle.getTransportCompanySeq());
                                if (helperList.size() > 0) {
                                    transportBookingVehicle.setHelperSeq(helperList.get(0).getEmployeeSeq());
                                } else {
                                    errorList.add("Cannot find Cleaner " + ExcelToEntity.split2(transportBookingTemplate1.getCleaner(), 2) + " at row " + lineNumber);
                                }
                            }
                            transportBooking.setTransportBookingVehicleTemp(transportBookingVehicle);

                            TransportBookingFeedback transportBookingFeedback = new TransportBookingFeedback();
                            transportBookingFeedback.setArrivedAtPickup(transportBookingTemplate1.getArrivedAtPickup());
                            transportBookingFeedback.setDepartedFromPickup(transportBookingTemplate1.getDepartedFromPickup());
                            transportBookingFeedback.setArrivedAtDelivery(transportBookingTemplate1.getArrivedAtDelivery());
                            transportBookingFeedback.setDepartedFromDelivery(transportBookingTemplate1.getDepartedFromDelivery());
                            transportBookingFeedback.setDocumentsCollectedDate(transportBookingTemplate1.getConfirmationDate());
                            transportBookingFeedback.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                            transportBooking.setTransportBookingFeedbackTemp(transportBookingFeedback);
                            transportBooking = this.setViaLocations(transportBooking, transportBookingTemplate1, lineNumber, errorList);
                            ResponseObject temp = this.transportBookingManagementControllerManager.validateTransportBooking(transportBooking);
                            if (!temp.isStatus()) {
                                errorList.add(temp.getMessage() + " at " + lineNumber);
                            }
                            if (errorList.size() > 0) {
                                noErrors = false;
                            }
                            transportBookingList.add(transportBooking);
                        }
                        lineNumber = lineNumber + 1;
                    }
                    if (noErrors) {
                        for (TransportBooking transportBooking : transportBookingList) {
                            ResponseObject bookingSave = this.transportBookingManagementControllerManager.saveTransportBooking(transportBooking);
                            if (bookingSave.isStatus()) {
                                TransportBooking temp = (TransportBooking) bookingSave.getObject();
                                TransportBookingVehicle transportBookingVehicle = transportBooking.getTransportBookingVehicleTemp();
                                transportBookingVehicle.setTransportBookingSeq(temp.getTransportBookingSeq());
                                this.transportBookingVehicleService.save(transportBookingVehicle);
                                if (transportBooking.getTransportBookingViaLocationListTemp() != null && transportBooking.getTransportBookingViaLocationListTemp().size() > 0) {
                                    TransportBookingViaLocationAux transportBookingViaLocationAux = new TransportBookingViaLocationAux();
                                    transportBookingViaLocationAux.setBookingViaLocations(transportBooking.getTransportBookingViaLocationListTemp());
                                    transportBookingViaLocationAux.setTransportBookingSeq(temp.getTransportBookingSeq());
                                    this.transportBookingManagementControllerManager.addViaLocationsIgnoreValidation(transportBookingViaLocationAux);
                                }

                                TransportBookingFeedback transportBookingFeedback = transportBooking.getTransportBookingFeedbackTemp();
                                transportBookingFeedback.setTransportBookingSeq(temp.getTransportBookingSeq());
                                if (transportBookingFeedback.getChargeableKm() == null) {
                                    transportBookingFeedback.setChargeableKm(this.distanceCalculatorService.calculateChargeableDistance(temp.getTransportBookingSeq()));
                                    transportBookingFeedback.setPlacementKm(this.distanceCalculatorService.calculatePlacementDistance(temp.getTransportBookingSeq()));
                                }
                                this.transportBookingFeedbackService.save(transportBookingFeedback);
                                this.chargesCalculator.revenueCostChargesCalculator(temp.getTransportBookingSeq());
                            }
                        }
                        String errorString = "Successfully Saved";
                        transportBulkBooking.setProcessOutPut(errorString);
                        transportBulkBooking.setCurrentStatus(YesOrNo.Yes.getYesOrNoSeq());
                        this.transportBulkBookingService.save(transportBulkBooking);
                        responseObject.setStatus(true);
                        responseObject.setMessage(errorString);
                    } else {
                        String errorString = errorList.stream().map(Object::toString).collect(Collectors.joining("<br>"));
                        transportBulkBooking.setProcessOutPut(errorString);
                        transportBulkBooking.setCurrentStatus(YesOrNo.NO.getYesOrNoSeq());
                        this.transportBulkBookingService.save(transportBulkBooking);
                        responseObject.setStatus(false);
                        responseObject.setMessage(errorString);
                    }
                } else {
                    String errorString = "No Records found";
                    transportBulkBooking.setProcessOutPut(errorString);
                    transportBulkBooking.setCurrentStatus(YesOrNo.NO.getYesOrNoSeq());
                    this.transportBulkBookingService.save(transportBulkBooking);
                    responseObject.setStatus(false);
                    responseObject.setMessage(errorString);
                }
            } else {
                String errorString = "No file found";
                transportBulkBooking.setProcessOutPut(errorString);
                transportBulkBooking.setCurrentStatus(YesOrNo.NO.getYesOrNoSeq());
                this.transportBulkBookingService.save(transportBulkBooking);
                responseObject.setStatus(false);
                responseObject.setMessage(errorString);
            }
        } catch (Exception e) {
            String errorString = e.getMessage();
            transportBulkBooking.setProcessOutPut(errorString);
            transportBulkBooking.setCurrentStatus(YesOrNo.NO.getYesOrNoSeq());
            this.transportBulkBookingService.save(transportBulkBooking);
            responseObject.setStatus(false);
            responseObject.setMessage(errorString);
            responseObject.setStatus(false);
            responseObject.setMessage(errorString);
            e.printStackTrace();
        }
        return responseObject;
    }

    @Override
    public List<TransportBulkBooking> search(BulkBookingSearch bulkBookingSearch) {
        List<TransportBulkBooking> transportBulkBookingList = null;
        try {
            QTransportBulkBooking transportBulkBooking = QTransportBulkBooking.transportBulkBooking;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(transportBulkBooking.companyProfileSeq.eq(companyProfileSeq));
            if (bulkBookingSearch.getCustomerSeq() != null) {
                builder.and(transportBulkBooking.customerSeq.eq(bulkBookingSearch.getCustomerSeq()));
            }
            if (bulkBookingSearch.getStartDate() != null && bulkBookingSearch.getEndDate() != null) {
                builder.and(transportBulkBooking.createdDate.between(bulkBookingSearch.getStartDate(), DateFormatter.getEndOfTheDay(bulkBookingSearch.getEndDate())));
            }
            transportBulkBookingList = (List<TransportBulkBooking>) this.transportBulkBookingService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBulkBookingList;
    }

    private TransportBooking setDefaultValues(TransportBulkBooking transportBulkBooking) {
        TransportBooking transportBooking = new TransportBooking();
        transportBooking.setCompanyProfileSeq(transportBulkBooking.getCompanyProfileSeq());
        transportBooking.setModuleSeq(transportBulkBooking.getModuleSeq());
        transportBooking.setDepartmentSeq(transportBulkBooking.getDepartmentSeq());
        transportBooking.setCustomerSeq(transportBulkBooking.getCustomerSeq());
        transportBooking.setShipperSeq(transportBulkBooking.getShipperSeq());
        transportBooking.setInvoiceCustomerSeq(transportBulkBooking.getInvoiceCustomerSeq());
        transportBooking.setUowSeq(transportBulkBooking.getUowSeq());
        transportBooking.setUovSeq(transportBulkBooking.getUovSeq());
        transportBooking.setInvoiceStatus(transportBulkBooking.getInvoiceStatus());
        transportBooking.setPackageTypeSeq(transportBulkBooking.getPackageTypeSeq());
        transportBooking.setCurrentStatus(BookingStatus.JOB_COMPLETED.getCurrentStatus());
        transportBooking.setPaymentMode(transportBulkBooking.getPaymentMode());
        transportBooking.setCashOrCredit(transportBulkBooking.getCashOrCredit());
        return transportBooking;
    }

    private TransportBooking setViaLocations(TransportBooking transportBooking, TransportBookingTemplate1 transportBookingTemplate1, Integer lineNumber, Set<String> errorList) {
        List<TransportBookingViaLocation> transportBookingViaLocationList = new ArrayList<>();
        if (transportBookingTemplate1.getViaLocation1() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation1(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location1 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation2() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation2(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location2 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation3() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation3(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location3 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation4() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation4(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location4 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation5() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation5(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location5 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation6() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation6(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location6 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation7() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation7(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location7 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation8() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation8(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location8 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation9() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation9(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location9 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation10() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation10(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location10 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation11() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation11(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location11 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation12() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation12(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location12 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation13() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation13(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location13 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation14() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation14(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location14 Location at row " + lineNumber);
            }
        }

        if (transportBookingTemplate1.getViaLocation15() != null) {
            List<FinalDestination> viaLocationList = this.locationFinder(transportBookingTemplate1.getViaLocation15(), transportBooking.getCompanyProfileSeq());
            if (viaLocationList.size() > 0) {
                transportBookingViaLocationList.add(this.createViaLocation(viaLocationList.get(0), transportBooking));
            } else {
                errorList.add("Cannot find Via Location15 Location at row " + lineNumber);
            }
        }

        if (transportBookingViaLocationList.size() > 0) {
            transportBooking.setTransportBookingViaLocationListTemp(transportBookingViaLocationList);
        }
        return transportBooking;
    }

    private TransportBookingViaLocation createViaLocation(FinalDestination finalDestination, TransportBooking transportBooking) {
        TransportBookingViaLocation transportBookingViaLocation = new TransportBookingViaLocation();
        transportBookingViaLocation.setViaLocationSeq(finalDestination.getFinalDestinationSeq());
        transportBookingViaLocation.setRequestedArrivalTime(transportBooking.getRequestedArrivalTime());
        transportBookingViaLocation.setViaLocationAddress(AddressBookUtils.cleanAddressBook(finalDestination.getAddressBook()));
        transportBookingViaLocation.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        transportBookingViaLocation.setUovSeq(transportBooking.getUovSeq());
        transportBookingViaLocation.setUowSeq(transportBooking.getUowSeq());
        transportBookingViaLocation.setDropPickStatus(DropPick.DROP.getDropPickStatus());
        return transportBookingViaLocation;
    }

    private List<FinalDestination> locationFinder(String location, Integer companyProfileSeq) {
        List<FinalDestination> finalDestinationList = this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(location, MasterDataStatus.APPROVED.getStatusSeq(), companyProfileSeq);
        if (finalDestinationList == null || finalDestinationList.size() == 0) {
            if (location.contains("-")) {
                finalDestinationList = this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(location.split("-")[1].trim(), MasterDataStatus.APPROVED.getStatusSeq(), companyProfileSeq);
            } else {
                finalDestinationList = this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(location.split(" ")[0].trim(), MasterDataStatus.APPROVED.getStatusSeq(), companyProfileSeq);
            }
        }
        return finalDestinationList;
    }
}
