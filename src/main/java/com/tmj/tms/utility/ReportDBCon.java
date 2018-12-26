package com.tmj.tms.utility;

import org.springframework.stereotype.Service;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class ReportDBCon {

    public Connection getReportDBCon() {
        Connection connection = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/TMS_DS");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}