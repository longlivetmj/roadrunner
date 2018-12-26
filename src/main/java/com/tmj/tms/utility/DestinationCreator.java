package com.tmj.tms.utility;

import com.tmj.tms.master.datalayer.modal.AddressBook;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Location;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.LocationService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Service
@Profile(value = "dev")
public class DestinationCreator {

    private FinalDestinationService finalDestinationService;
    private LocationService locationService;

    @Autowired
    public DestinationCreator(FinalDestinationService finalDestinationService,
                              LocationService locationService) {
        this.finalDestinationService = finalDestinationService;
        this.locationService = locationService;
    }

//    @Scheduled(fixedRate = 60000 * 420)
    private void uploadChargeableKmsFroJobs() {
        String filePath = "C:/Excel/";
        String fileName = "\\destinations.xlsx";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        try {
            InputStream inp = new FileInputStream(filePath + fileName);
            String fileExtn = GetFileExtension(fileName);
            Workbook wb_xssf; //Declare XSSF WorkBook
            Workbook wb_hssf; //Declare HSSF WorkBook
            Sheet sheet = null;
            if (fileExtn.equalsIgnoreCase("xlsx")) {
                wb_xssf = new XSSFWorkbook(inp);
                sheet = wb_xssf.getSheetAt(0);
            }
            if (fileExtn.equalsIgnoreCase("xls")) {
                POIFSFileSystem fs = new POIFSFileSystem(inp);
                wb_hssf = new HSSFWorkbook(fs);
                sheet = wb_hssf.getSheetAt(0);
            }
            System.out.println("Heeeeee");
            Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
            boolean correctFile = true;
            List<Temp> tempList = new ArrayList<Temp>();
            while (rows.hasNext()) {
                Temp temp = new Temp();
                Row row = (Row) rows.next();
                if (!correctFile) {
                    break;
                }
                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    Cell cell = (Cell) cells.next();
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            if (cell.getColumnIndex() == 0) {
                                try {
                                    temp.setDestination(cell.getStringCellValue().trim());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (cell.getColumnIndex() == 1) {
                                try {
                                    temp.setAddress(cell.getStringCellValue().trim());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (cell.getColumnIndex() == 2) {
                                try {
                                    temp.setLocation(cell.getStringCellValue().trim());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case NUMERIC:
                            if (cell.getColumnIndex() == 3) {
                                try {
                                    temp.setLatitude(cell.getNumericCellValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (cell.getColumnIndex() == 4) {
                                try {
                                    temp.setLongitude(cell.getNumericCellValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:
                    }
                }
                if (temp != null && temp.getDestination() != null) {
                    tempList.add(temp);
                }
            }
            inp.close();
            DateFormatter dateFormatter = new DateFormatter();
            Set<String> locationList = new HashSet<>();
            Set<String> exceptionList = new HashSet<>();
            for (Temp temp : tempList) {
                try {
                    if (temp.getLocation() != null) {
                        Location location = this.locationService.findByLocationNameIgnoreCase(temp.getLocation());
                        if (location != null) {
                            FinalDestination finalDestination = new FinalDestination();
                            finalDestination.setCompanyProfileSeq(2);
                            finalDestination.setStatus(2);
                            finalDestination.setDestination(temp.getDestination());
                            finalDestination.setLocationSeq(location.getLocationSeq());
                            finalDestination.setLatitude(temp.getLatitude());
                            finalDestination.setLongitude(temp.getLongitude());
                            finalDestination.setCreatedBy("thilanga");
                            finalDestination.setLastModifiedBy("thilanga");
                            finalDestination.setCreatedDate(new Date());
                            finalDestination.setLastModifiedDate(new Date());
                            AddressBook addressBook =  new AddressBook();
                            addressBook.setAddress1(temp.address);
                            addressBook.setCountrySeq(210);
                            addressBook.setCreatedBy("thilanga");
                            addressBook.setLastModifiedBy("thilanga");
                            addressBook.setCreatedDate(new Date());
                            addressBook.setLastModifiedDate(new Date());
                            finalDestination.setAddressBook(addressBook);
                            this.finalDestinationService.save(finalDestination);

                        }
                    } else {
                        System.out.println(">>>>Dest" + temp.getDestination());
                    }

                } catch (Exception e) {
                    exceptionList.add(temp.location);
                    e.printStackTrace();
                }
            }
            System.out.println(">>>Loca" + locationList.toString());
            System.out.println(">>>Exce" + exceptionList.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private String GetFileExtension(String fName) {
        String ext = "";
        int mid = fName.lastIndexOf(".");
        ext = fName.substring(mid + 1, fName.length());
        return ext;
    }

    class Temp {
        private String destination;
        private String address;
        private String location;
        private Double latitude;
        private Double longitude;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
}
