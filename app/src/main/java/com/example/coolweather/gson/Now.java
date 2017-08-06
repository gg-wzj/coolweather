package com.example.coolweather.gson;

/**
 * Created by wzj on 2017/8/5.
 */

public class Now {


    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    /**
     * cond : {"code":"300","txt":"阵雨"}
     * fl : 34
     * hum : 75
     * pcpn : 0
     * pres : 1003
     * tmp : 31
     * vis : 5
     * wind : {"deg":"357","dir":"北风","sc":"3-4","spd":"12"}
     */

    private CondBean cond;
    private String tmp;

    public static class CondBean {
        /**
         * code : 300
         * txt : 阵雨
         */

        private String code;
        private String txt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }
}
