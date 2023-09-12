package com.onurkol.app.calculator.data;

public class HistoryData {
    String calcExpression, calcValue;

    public HistoryData(String Expression, String Value){
        calcExpression=Expression;
        calcValue=Value;
    }

    public void setExpression(String newProcessExpression){
        calcExpression=newProcessExpression;
    }
    public String getExpression(){
        return calcExpression;
    }
    public void setValue(String newProcessValue){
        calcValue=newProcessValue;
    }
    public String getValue(){
        return calcValue;
    }
}
