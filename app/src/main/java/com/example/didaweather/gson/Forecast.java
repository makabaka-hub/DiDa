package com.example.didaweather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String data;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }
    public class More {
        public String info;
    }
}
