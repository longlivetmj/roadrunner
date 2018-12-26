package com.tmj.tms.utility;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

public class ReportWriter {
    public static void write(HttpServletResponse response, ByteArrayOutputStream baos) {
        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            baos.writeTo(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
