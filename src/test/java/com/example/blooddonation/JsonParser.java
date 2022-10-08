package com.example.blooddonation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String,String> parseJsonObject(JSONObject object){
      //Initialize Hash map
        HashMap<String,String> datalist = new HashMap<>();
        //Get name from object
        try {
            String name = object.getString("name");
            //Get Latitude from object
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            //Get longitude from object
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            //Put all value in hash map
            datalist.put("name", name);
            datalist.put("lng", latitude);
            datalist.put("lng", longitude);




        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Return Hash map
        return datalist;

    }
    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray) {
        //Initialize hash map list
        List<HashMap<String, String>> datalist = new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++){

            try {
                //Initialize hash map
                HashMap<String,String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                //Add data in hash map list
                datalist.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Return hash map List
        return datalist;

    }
    public List<HashMap<String,String>> parseResult(JSONObject object){
        //Initialize json array
        JSONArray jsonArray = null;
        //Get result array
        try {
            jsonArray= object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return array
        return parseJsonArray(jsonArray);
    }
}
