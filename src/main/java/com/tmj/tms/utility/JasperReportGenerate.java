package com.tmj.tms.utility;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class JasperReportGenerate {

    public void generatePdf(JasperPrint jp, ByteArrayOutputStream out) {
        try {
            List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
            JRPdfExporter pdfExporter = new JRPdfExporter();
            jasperPrints.add(jp);

            pdfExporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setMetadataAuthor("Road Runner");
            configuration.setCreatingBatchModeBookmarks(true);
            pdfExporter.setConfiguration(configuration);
            pdfExporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateXlxs(JasperPrint jp, ByteArrayOutputStream out) {
        try {
            List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            jasperPrints.add(jp);

            xlsxExporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setForcePageBreaks(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            xlsxExporter.setConfiguration(configuration);

            xlsxExporter.exportReport();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateRtf(JasperPrint jp, ByteArrayOutputStream out) {
        try {
            List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
            JRRtfExporter rtfExporter = new JRRtfExporter();
            jasperPrints.add(jp);
            rtfExporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            SimpleWriterExporterOutput writerExporterOutput = new SimpleWriterExporterOutput(out);
            rtfExporter.setExporterOutput(writerExporterOutput);
            rtfExporter.exportReport();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mergeJasperPdf(List<JasperPrint> jpList,ByteArrayOutputStream out) {
        try {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(jpList)); //Set as export input my list with JasperPrint s
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out)); //or any other out streaam
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setMetadataAuthor("Road Runner");
            configuration.setCreatingBatchModeBookmarks(true); //add this so your bookmarks work, you may set other parameters
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
