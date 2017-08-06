package com.example.coolweather.gson;

/**
 * Created by wzj on 2017/8/5.
 */

public class Forecast {

    /**
     * astro : {"mr":"16:59","ms":"02:52","sr":"05:17","ss":"18:51"}
     * cond : {"code_d":"302","code_n":"101","txt_d":"雷阵雨","txt_n":"多云"}
     * date : 2017-08-05
     * hum : 66
     * pcpn : 1.2
     * pop : 52
     * pres : 1002
     * tmp : {"max":"36","min":"29"}
     * uv : 11
     * vis : 18
     * wind : {"deg":"261","dir":"西风","sc":"微风","spd":"12"}
     */

    private CondBean cond;
    private String date;
    private TmpBean tmp;
    public static class TmpBean {

        /**
         * max : 36
         * min : 29
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }

    public  static class  CondBean{

        /**
         * code_d : 302
         * code_n : 101
         * txt_d : 雷阵雨
         * txt_n : 多云
         */

        private String txt_d;
        private String txt_n;

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }
    }

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }
}
