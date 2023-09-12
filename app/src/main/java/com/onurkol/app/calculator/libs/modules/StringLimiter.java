package com.onurkol.app.calculator.libs.modules;

public class StringLimiter {
    public static String Limit(String string, int Limit){
        return ((string!=null) ? ((string.length() > Limit) ? string.substring(0,Limit)+"..." : string) : "");
    }
    public static String Limit(String string, int Start, int Limit){
        return ((string!=null) ? ((string.length() > (Limit-Start)) ? string.substring(Start,Limit)+"..." : string) : "");
    }
}
