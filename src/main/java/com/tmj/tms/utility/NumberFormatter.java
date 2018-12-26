package com.tmj.tms.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class NumberFormatter {
    DecimalFormat ds = null;

    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100;
    }

    public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValuesDesc(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValuesAsc(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public static String convertToSixDigit(Integer value) {
        String convertedString;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        convertedString = decimalFormat.format(value);
        return convertedString;
    }

    public static Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String convertToMillion(double v) {
        double millions = v / 1000000;
        String s;
        try {
            DecimalFormat ds = new DecimalFormat("0.00");
            s = ds.format(millions);
        } catch (Exception e) {
            System.out.println("Error");
            s = "";
        }
        return s + " (M)";
    }

    public String convertToTwoDecimal(double df) {
        String s;
        try {
            ds = new DecimalFormat("0.00");
            s = ds.format(df);
        } catch (Exception e) {
            System.out.println("Error");
            s = "";
        }
        return s;
    }

    public double convertStringToDouble(String df) {
        double s = 0;
        try {
            s = Double.parseDouble(df);
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception");
        }
        return s;
    }

    public String convertToTwoDigits(double df) {
        String s;
        ds = new DecimalFormat("00");
        s = ds.format(df);
        return s;
    }

    public String generateRandomNumber() {
        String number = "123456789";
        try {
            Double aDouble = Math.random() * 1000000;
            number = String.valueOf(aDouble.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public String convertToOneDecimal(Double df) {
        String s = "0.0";
        if (df != null) {
            ds = new DecimalFormat("0.0");
            s = ds.format(df);
        }
        return s;
    }

    public String getFormattedTimeString(String time) {
        String formattedTime = time.substring(0, 2) + ":" + time.substring(2, 4) + ":00";
        return formattedTime;
    }

    public String restrict_String(String strCode, int count) {
        String tempcode = strCode;
        int intCodeCount = tempcode.length();
        if (intCodeCount >= count) {
            tempcode = tempcode.substring(0, count);
        }
        tempcode = tempcode.replaceAll("&quot;", " ");
        tempcode = tempcode.replaceAll("&#x9;", " ");
        tempcode = tempcode.replaceAll("&amp;", " ");
        tempcode = tempcode.replaceAll("UTF-8", " ");
        tempcode = tempcode.replaceAll("UTF-16", " ");
        tempcode = tempcode.replaceAll("é", "e");
        tempcode = tempcode.replaceAll("Â", "A");
        tempcode = tempcode.replaceAll("¿", " ");
        tempcode = tempcode.replaceAll("&", " ");
        tempcode = tempcode.replaceAll("\"", "");
        tempcode = tempcode.replace("[", " ");
        tempcode = tempcode.replace("]", " ");
        tempcode = tempcode.replace("<", " ");
        tempcode = tempcode.replace(">", " ");
        tempcode = tempcode.replace(":", " ");
        tempcode = tempcode.replace("?", " ");
        tempcode = tempcode.replace("|", " ");
        tempcode = tempcode.replace("#", " ");
        tempcode = tempcode.replaceAll("\\P{Print}", "");
        String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800-\udc00-\udbff\udfff"
                + "]";
        tempcode = tempcode.replaceAll(xml10pattern, tempcode);
        return tempcode;
    }

    public double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return dist * meterConversion;
    }

    public String convertToThreeDigit(int counter) {
        String s;
        ds = new DecimalFormat("000");
        s = ds.format(counter);
        return s;
    }

    public Boolean checkTheDecimalPointsInANumber(Double d, Integer decimalPoint) {
        Boolean status = false;
        String[] splitter = d.toString().split("\\.");
        splitter[0].length();   // Before Decimal Count
        int decimalLength = splitter[1].length();  // After Decimal Count

        if (decimalLength <= decimalPoint) {
            status = true;
        }
        return status;
    }

    public String convertToMillion(Double counter) {
        String s = null;
        try {
            if (counter != null) {
                ds = new DecimalFormat("0.00M");
                s = ds.format(counter / 1000000);
            }
        } catch (Exception e) {
            System.out.println("convertToMillion Error");
        }
        return s;
    }

    public String convertToTonnes(Double counter) {
        String s = null;
        try {
            ds = new DecimalFormat("0.000");
            s = ds.format(counter / 1000);
        } catch (Exception e) {
            System.out.println("convertToTonnes Error");
        }
        return s;
    }
}
