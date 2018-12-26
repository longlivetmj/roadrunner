package com.tmj.tms.utility;

public class SpecialCharRemover {
    public static String removeAllSpecialCharacters(String input) {
        String result = input.replaceAll("[&()+\\]\\[<>:?|]", " ");
        return result;
    }

    public static String removeNewLine(String input) {
        String result = input.replaceAll("\n", "");
        result = result.replaceAll("\r", " ");
        return result;
    }
}


