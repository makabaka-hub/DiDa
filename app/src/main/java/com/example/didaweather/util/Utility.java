package com.example.didaweather.util;

import android.text.TextUtils;

import com.example.didaweather.db.City;
import com.example.didaweather.db.County;
import com.example.didaweather.db.Province;
import com.example.didaweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utility {
    //处理和解析省份的数据
    public static boolean hanldeProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                //json的对象的数组，用来接收传回的多个省份的数据
                JSONArray allProvinces = new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++){
                    //取出每一个省份
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    //解析出省份的id并将其赋值给province对象
                    province.setProvinceCode(provinceObject.getInt("id"));
                    //解析出省份的name并将其赋值给province对象
                    province.setProvinceName(provinceObject.getString("name"));
                    //将这一个省份保存到表中
                    province.save();
                }
                //处理成功返回真
                return true;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        //处理失败返回假
        return false;
    }
    //处理和解析市的数据
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCity = new JSONArray(response);
                for(int i=0;i<allCity.length();i++){
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    //处理和解析县的数据
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounty = new JSONArray(response);
                for(int i=0;i<allCounty.length();i++){
                    JSONObject countyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}