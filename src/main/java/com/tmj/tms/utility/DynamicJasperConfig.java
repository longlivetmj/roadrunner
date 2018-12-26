package com.tmj.tms.utility;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.StatusLightCondition;
import com.tmj.tms.config.datalayer.modal.DynamicJasperInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

@Service
public class DynamicJasperConfig {

    public FastReportBuilder defaultPageSettings(Integer width) {
        FastReportBuilder fastReportBuilder = new FastReportBuilder();
        Page page = new Page();
        page.setHeight(1500);
        page.setWidth(width);
        page.setOrientationPortrait(false);
        fastReportBuilder.setPageSizeAndOrientation(page);

        Style headerStyle = new Style();
        headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        headerStyle.setFont(Font.ARIAL_BIG_BOLD);
        headerStyle.setBackgroundColor(Color.WHITE);
        headerStyle.setPadding(10);
        headerStyle.setTransparent(false);

        Style subHeader = new Style();
        subHeader.setOverridesExistingStyle(true);
        subHeader.setStreching(Stretching.NO_STRETCH);
        subHeader.setHorizontalAlign(HorizontalAlign.LEFT);
        subHeader.setVerticalAlign(VerticalAlign.MIDDLE);
        subHeader.setBackgroundColor(Color.WHITE);
        subHeader.setFont(Font.ARIAL_SMALL_BOLD);

        Style columnStyle = new Style();
        columnStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        columnStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        columnStyle.setStreching(Stretching.RELATIVE_TO_BAND_HEIGHT);
        columnStyle.setStretchWithOverflow(true);
        columnStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
        columnStyle.setBorder(Border.THIN());
        columnStyle.setBackgroundColor(Color.ORANGE);
        columnStyle.setTransparent(false);
        columnStyle.setStretchWithOverflow(true);

        Style bodyStyle = new Style();
        bodyStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        bodyStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        bodyStyle.setStretchWithOverflow(true);
        bodyStyle.setBorder(Border.THIN());
        bodyStyle.setFont(Font.ARIAL_SMALL_BOLD);
        bodyStyle.setPadding(5);
        bodyStyle.setTransparent(false);
        bodyStyle.setStretchWithOverflow(true);

//        ArrayList conditionalStyles = this.createConditionalStyles(bodyStyle);

        fastReportBuilder.setDefaultStyles(headerStyle, subHeader, columnStyle, bodyStyle);
        return fastReportBuilder;
    }


    public FastReportBuilder addColumnToReportBuilder(FastReportBuilder fastReportBuilder, DynamicJasperInfo dynamicJasperInfo) {
        try {
            if (dynamicJasperInfo.getVariableType().equals("String")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), String.class.getName(), dynamicJasperInfo.getColumnWidth(), this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            } else if (dynamicJasperInfo.getVariableType().equals("DateTime")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), Date.class.getName(), dynamicJasperInfo.getColumnWidth(), false, "yyyy-MM-dd hh:mm a", this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            } else if (dynamicJasperInfo.getVariableType().equals("Integer")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), Number.class.getName(), dynamicJasperInfo.getColumnWidth(), this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            } else if (dynamicJasperInfo.getVariableType().equals("Double")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), Number.class.getName(), dynamicJasperInfo.getColumnWidth(), false, "#,##0.00", this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            } else if (dynamicJasperInfo.getVariableType().equals("Date")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), Date.class.getName(), dynamicJasperInfo.getColumnWidth(), false, "yyyy-MM-dd", this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            } else if (dynamicJasperInfo.getVariableType().equals("Currency")) {
                fastReportBuilder.addColumn(dynamicJasperInfo.getDisplayName(), dynamicJasperInfo.getVariableName(), Number.class.getName(), dynamicJasperInfo.getColumnWidth(), false, "#,##0.00", this.columnStyle(dynamicJasperInfo.getHorizontalAlignment()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReportBuilder;
    }

    public Style columnStyle(String alignment) {
        Style bodyStyle = new Style();
        bodyStyle.setOverridesExistingStyle(true);
        if (alignment.equals("L")) {
            bodyStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        } else if (alignment.equals("R")) {
            bodyStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        } else if (alignment.equals("C")) {
            bodyStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        } else if (alignment.equals("J")) {
            bodyStyle.setHorizontalAlign(HorizontalAlign.JUSTIFY);
        } else {
            bodyStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        }

        bodyStyle.setOverridesExistingStyle(true);
        bodyStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        bodyStyle.setStretchWithOverflow(true);
        bodyStyle.setBorder(Border.THIN());
        bodyStyle.setFont(Font.ARIAL_SMALL_BOLD);
        bodyStyle.setPadding(5);
        bodyStyle.setTransparent(false);
        return bodyStyle;
    }


    public Style subHeaderStyle() {
        Style subHeader = new Style();
        subHeader.setOverridesExistingStyle(true);
        subHeader.setStreching(Stretching.NO_STRETCH);
        subHeader.setHorizontalAlign(HorizontalAlign.LEFT);
        subHeader.setVerticalAlign(VerticalAlign.MIDDLE);
        subHeader.setBackgroundColor(Color.WHITE);
        subHeader.setFont(Font.ARIAL_SMALL_BOLD);
        return subHeader;
    }

    public Style amountStyle() {
        Style subHeader = new Style();
        subHeader.setOverridesExistingStyle(true);
        subHeader.setStreching(Stretching.NO_STRETCH);
        subHeader.setHorizontalAlign(HorizontalAlign.RIGHT);
        subHeader.setVerticalAlign(VerticalAlign.MIDDLE);
        subHeader.setBackgroundColor(Color.WHITE);
        subHeader.setFont(Font.ARIAL_MEDIUM_BOLD);
        subHeader.setBorderBottom(Border.THIN());
        subHeader.setBorderTop(Border.THIN());
        return subHeader;
    }

    private ArrayList createConditionalStyles(Style baseStyle) {
        ArrayList conditionalStyles = new ArrayList();
        try {
            Style style0 = (Style) BeanUtils.cloneBean(baseStyle);
            style0.setBackgroundColor(Color.RED);
            style0.setFont(Font.GEORGIA_MEDIUM_BOLD);
            Style style1 = (Style) BeanUtils.cloneBean(baseStyle);
            style1.setBackgroundColor(new Color(128, 128, 0));
            Style style2 = (Style) BeanUtils.cloneBean(baseStyle);
            style2.setBackgroundColor(new Color(0, 128, 0)); //dark green

            StatusLightCondition status0 = new StatusLightCondition(new Double(0), new Double(3000)); //TODO ENHANCEMENT make it come from a parameter??? $P{...}
            StatusLightCondition status1 = new StatusLightCondition(new Double(5000), new Double(6000));
            StatusLightCondition status2 = new StatusLightCondition(new Double(6000), new Double(100000));

            ConditionalStyle condition0 = new ConditionalStyle(status0, style0);
            ConditionalStyle condition1 = new ConditionalStyle(status1, style1);
            ConditionalStyle condition2 = new ConditionalStyle(status2, style2);

            conditionalStyles.add(condition0);
            conditionalStyles.add(condition1);
            conditionalStyles.add(condition2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conditionalStyles;
    }
}
