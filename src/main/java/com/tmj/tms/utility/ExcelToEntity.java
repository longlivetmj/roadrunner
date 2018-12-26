package com.tmj.tms.utility;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExcelToEntity {

    public static <T> List<T> parseXLSFileToBeans(final File xlsFile,
                                                  final File jxlsConfigFile)
            throws Exception {
        final List<T> result = new ArrayList<T>();
        try {
            ReaderConfig.getInstance().setSkipErrors(true);
            ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
            convertUtilsBean.deregister(String.class);
            convertUtilsBean.register(new MyStringConverter(), String.class);

            convertUtilsBean.deregister(Date.class);
            convertUtilsBean.register(new MyDateConverter(), Date.class);

            BeanUtilsBean.setInstance(new BeanUtilsBean(convertUtilsBean));

            final XLSReader xlsReader = ReaderBuilder.buildFromXML(jxlsConfigFile);

            final Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("result", result);
            InputStream inputStream = new BufferedInputStream(new FileInputStream(xlsFile));
            xlsReader.read(inputStream, beans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> List<T> parseXLSInputStreamToBeans(final InputStream inputStream,
                                                         final File jxlsConfigFile)
            throws Exception {
        ReaderConfig.getInstance().setSkipErrors(true);
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.deregister(String.class);
        convertUtilsBean.register(new MyStringConverter(), String.class);

        convertUtilsBean.deregister(Date.class);
        convertUtilsBean.register(new MyDateConverter(), Date.class);

        convertUtilsBean.deregister(Double.class);
        convertUtilsBean.register(new MyDoubleConverter(), Double.class);

        BeanUtilsBean.setInstance(new BeanUtilsBean(convertUtilsBean));

        final XLSReader xlsReader = ReaderBuilder.buildFromXML(jxlsConfigFile);
        final List<T> result = new ArrayList<T>();
        final Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", result);
        xlsReader.read(inputStream, beans);
        return result;
    }

    public static boolean isScientificNotation(String numberString) {
        // Validate number
        try {
            new BigDecimal(numberString);
        } catch (NumberFormatException e) {
            return false;
        }
        boolean checkStatus = numberString.toUpperCase().contains("E") && numberString.contains(".");
        return checkStatus;
    }

    public static boolean isLong(String numberString) {
        boolean checkStatus = true;
        try {
            Long.parseLong(numberString);
        } catch (NumberFormatException e) {
            checkStatus = false;
        }
        return checkStatus;
    }

    /**
     * Sets a field value on a given object
     *
     * @param targetObject the object to set the field value on
     * @param fieldName    exact name of the field
     * @param fieldValue   value to set on the field
     * @return true if the value was successfully set, false otherwise
     */
    public static boolean setField(Object targetObject, String fieldName, Object fieldValue) {
        Field field;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        Class superClass = targetObject.getClass().getSuperclass();
        while (field == null && superClass != null) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }
        if (field == null) {
            return false;
        }
        field.setAccessible(true);
        try {
            if (field.getType().getSimpleName().equals("Double")) {
                fieldValue = Double.parseDouble(fieldValue.toString());
            } else if (field.getType().getSimpleName().equals("Integer")) {
                fieldValue = Integer.parseInt(fieldValue.toString());
            } else if (field.getType().getSimpleName().equals("Date")) {
                fieldValue = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse((fieldValue.toString()));
            }
            field.set(targetObject, fieldValue);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String split2(String line, int n) {
        line += " ";
        Pattern pattern = Pattern.compile("\\w*\\s");
        Matcher matcher = pattern.matcher(line);
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        while (matcher.find()) {
            if (i != n)
                list.add(matcher.group().trim());
            else
                break;
            i++;
        }
        String value = list.stream().map(Object::toString).collect(Collectors.joining(" "));
        return value.trim();
    }

    static class MyStringConverter implements Converter {
        public Object convert(Class type, Object value) {
            if (value == null) {
                return (String) null;
            } else {
                if (isScientificNotation(value.toString())) {
                    value = new BigDecimal(value.toString()).toPlainString();
                } else {
                    value = value.toString();
                }
                return (value.toString());
            }
        }
    }

    static class MyDoubleConverter implements Converter {
        public Object convert(Class type, Object value) {
            if (value == null) {
                return (String) null;
            } else {
                Double aDouble = null;
                try {
                    aDouble = Double.parseDouble(value.toString());
                } catch (Exception e) {
                    System.out.println("invalid Format");
                }
                return aDouble;
            }
        }
    }

    static class MyDateConverter implements Converter {
        public Object convert(Class type, Object value) {
            if (value == null) {
                return (String) null;
            } else if (isLong(value.toString())) {
                return DateUtil.getJavaDate(Double.parseDouble(value.toString()));
            } else {
                Date date = null;
                try {
                    date = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse((String) value);
                } catch (ParseException e) {
                    System.out.println("invalid Format");
                }
                if (date == null) {
                    try {
                        date = new SimpleDateFormat("dd-MMM-yy").parse((String) value);
                    } catch (ParseException e) {
                        System.out.println("invalid Format");
                    }
                }
                return date;
            }
        }
    }
}
