package com.onurkol.app.calculator.libs.modules;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class ListToJson {
    static final Gson gson=new Gson();

    public static <T> String getJson(ArrayList<T> list){
        int listSize=list.size();
        // Variables
        String newData;
        if(listSize>0) {
            StringBuilder processData = new StringBuilder();
            for (int i = 0; i < listSize; i++) {
                if (i == (listSize - 1))
                    processData.append(gson.toJson(list.get(i)));
                else
                    processData.append(gson.toJson(list.get(i))).append(",");
            }
            newData = "[" + processData + "]";
        }
        else
            newData="";
        return newData;
    }

    public static <T> ArrayList<T> getArrayList(String json, Type dataType){
        ArrayList<T> getAList=new ArrayList<>();

        if(!json.equals(""))
            getAList=gson.fromJson(json, dataType);

        return getAList;
    }

    public static <T> Collection<T> getCollection(String json, Type dataType){
        Collection<T> getCollectionList=new ArrayList<>();

        if(!json.equals(""))
            getCollectionList=gson.fromJson(json, dataType);

        return getCollectionList;
    }
}