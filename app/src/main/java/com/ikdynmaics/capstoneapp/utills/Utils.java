package com.ikdynmaics.capstoneapp.utills;

import java.text.DecimalFormat;

public class Utils {

    public static String convertTempFromKTOC(Double temp){
        double newTemp = temp - 273.15;

        String Celsius = new DecimalFormat("##").format(newTemp);

        return Celsius;

    }
}
