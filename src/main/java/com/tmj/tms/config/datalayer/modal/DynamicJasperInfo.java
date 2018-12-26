package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dynamic_jasper_info")
public class DynamicJasperInfo {
    private Integer dynamicJasperInfoSeq;
    private String fieldName;
    private Integer status;
    private String displayName;
    private String variableName;
    private String variableType;
    private Integer columnWidth;
    private Integer reportSeq;
    private Integer columnOrder;
    private String horizontalAlignment;
    private Integer columnFilterStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "dynamic_jasper_info_seq", allocationSize = 1)
    @Column(name = "dynamic_jasper_info_seq", nullable = false, precision = 0, unique = true)
    public Integer getDynamicJasperInfoSeq() {
        return dynamicJasperInfoSeq;
    }

    public void setDynamicJasperInfoSeq(Integer dynamicJasperInfoSeq) {
        this.dynamicJasperInfoSeq = dynamicJasperInfoSeq;
    }

    @Basic
    @Column(name = "field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "variable_name")
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Basic
    @Column(name = "variable_type")
    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    @Basic
    @Column(name = "column_width")
    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Basic
    @Column(name = "report_seq")
    public Integer getReportSeq() {
        return reportSeq;
    }

    public void setReportSeq(Integer reportSeq) {
        this.reportSeq = reportSeq;
    }

    @Basic
    @Column(name = "column_order")
    public Integer getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }

    @Basic
    @Column(name = "horizontal_alignment")
    public String getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    @Basic
    @Column(name = "column_filter_status")
    public Integer getColumnFilterStatus() {
        return columnFilterStatus;
    }

    public void setColumnFilterStatus(Integer columnFilterStatus) {
        this.columnFilterStatus = columnFilterStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicJasperInfo that = (DynamicJasperInfo) o;
        return dynamicJasperInfoSeq == that.dynamicJasperInfoSeq &&
                Objects.equals(fieldName, that.fieldName) &&
                Objects.equals(status, that.status) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(variableName, that.variableName) &&
                Objects.equals(variableType, that.variableType) &&
                Objects.equals(columnWidth, that.columnWidth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicJasperInfoSeq, fieldName, status, displayName, variableName, variableType, columnWidth);
    }
}
