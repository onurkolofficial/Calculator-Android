package com.onurkol.app.calculator.tools;

public class LimitCharTool {
    public static String LimitChar(String string, int Limit){
        return ((string!=null) ? ((string.length() > Limit) ? string.substring(0,Limit)+" ..." : string) : "");
    }
    public static String LimitChar(String string, int Start, int Limit){
        return ((string!=null) ? ((string.length() > (Limit-Start)) ? string.substring(Start,Limit)+" ..." : string) : "");
    }
}
