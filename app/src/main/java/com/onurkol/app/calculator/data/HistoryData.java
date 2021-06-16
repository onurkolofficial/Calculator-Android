package com.onurkol.app.calculator.data;

public class HistoryData {
    private String processExpression,processValue;

    public HistoryData(String Expression, String Value){
        processExpression=Expression;
        processValue=Value;
    }

    public void setProcessExpression(String newProcessExpression){
        processExpression=newProcessExpression;
    }
    public String getProcessExpression(){
        return processExpression;
    }
    public void setProcessValue(String newProcessValue){
        processValue=newProcessValue;
    }
    public String getProcessValue(){
        return processValue;
    }
}
