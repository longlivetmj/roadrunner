package com.tmj.tms.utility;

import javax.activation.DataSource;

public class EmailAttachment {

    private String fileName;

    private DataSource dataSource;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
