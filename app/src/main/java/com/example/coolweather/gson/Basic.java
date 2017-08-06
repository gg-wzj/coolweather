package com.example.coolweather.gson;

/**
 * Created by wzj on 2017/8/5.
 */

public class Basic {

    /**
     * city : 苏州
     * cnty : 中国
     * id : CN101190401
     * lat : 31.29937935
     * lon : 120.61958313
     * update : {"loc":"2017-08-05 13:51","utc":"2017-08-05 05:51"}
     */

    private String city;
    private String id;
    private UpdateBean update;

    @Override
    public String toString() {
        return "Basic{" +
                "city='" + city + '\'' +
                ", id='" + id + '\'' +
                ", update=" + update +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {
        /**
         * loc : 2017-08-05 13:51
         * utc : 2017-08-05 05:51
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}
