package com.example.coolweather.gson;

/**
 * Created by wzj on 2017/8/5.
 */

public class Aqi {


    @Override
    public String toString() {
        return "Aqi{" +
                "city=" + city +
                '}';
    }

    /**
     * city : {"aqi":"40","pm10":"40","pm25":"16","qlty":"优"}
     */

    private CityBean city;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * aqi : 40
         * pm10 : 40
         * pm25 : 16
         * qlty : 优
         */

        private String aqi;
        private String pm10;
        private String pm25;
        private String qlty;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        @Override
        public String toString() {
            return "CityBean{" +
                    "aqi='" + aqi + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", qlty='" + qlty + '\'' +
                    '}'+"\r\n";
        }
    }
}
