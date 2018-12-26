package com.tmj.tms.utility;

import java.util.regex.Pattern;

public class CheckSpecialCharacters {

    public boolean isSpecialCharacterInclude(String string){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        return pattern.matcher(string).find();
    }
}
