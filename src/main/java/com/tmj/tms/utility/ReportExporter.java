package com.tmj.tms.utility;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import java.io.ByteArrayOutputStream;

public class ReportExporter {

    public static void exportToXLSX(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        // Create a JRXlsExporter instance
        JRXlsxExporter exporter = new JRXlsxExporter();

        // Here we assign the parameters jp and baos to the exporter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        // Excel specific parameters
        // Check the Jasper (not DynamicJasper) docs for a description of these settings. Most are
        // self-documenting
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        // Retrieve the exported report in XLS format
        exporter.exportReport();
    }

    public static void exportToXLS(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        // Create a JRXlsExporter instance
        JRXlsExporter exporter = new JRXlsExporter();

        // Here we assign the parameters jp and baos to the exporter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        // Excel specific parameters
        // Check the Jasper (not DynamicJasper) docs for a description of these settings. Most are
        // self-documenting
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        // Retrieve the exported report in XLS format
        exporter.exportReport();
    }

    public static void exportToXls(JasperPrint jasperPrint,
                                   ByteArrayOutputStream baos) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);

        exporter.exportReport();
    }

    public static void exportToPdf(JasperPrint jasperPrint, ByteArrayOutputStream baos) throws JRException {
        try {
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.setParameter(JRPdfExporterParameter.IS_TAGGED, Boolean.TRUE);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void exportToHtml(JasperPrint jasperPrint, ByteArrayOutputStream baos) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, true);

        exporter.exportReport();
    }

}
