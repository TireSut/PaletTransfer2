package com.example.palettransfer;

public class depolist {
    private String PLA,PLATXT,WH,WHTXT,SP,YON,SPTXT;
    public depolist(String PLA,String PLATXT,String WH,String WHTXT,String SP,String YON,String SPTXT){
        this.PLA = PLA;
        this.PLATXT = PLATXT;
        this.WH = WH;
        this.WHTXT = WHTXT;
        this.SP = SP;
            this.YON = YON;
        this.SPTXT = SPTXT;
    }
    public String getPLA() {
        return PLA;
    }
    public void setPLA(String PLA) {
        this.PLA = PLA;
    }

    public String getPLATXT() {
        return PLATXT;
    }
    public void setPLATXT(String PLATXT) {
        this.PLATXT = PLATXT;
    }

    public String getWH() {
        return WH;
    }
    public void setWH(String WH) {
        this.WH = WH;
    }

    public String getWHTXT() {
        return WHTXT;
    }
    public void setWHTXT(String WHTXT) {
        this.WHTXT = WHTXT;
    }

    public String getSP() {
        return SP;
    }
    public void setSP(String SP) {
        this.SP = SP;
    }

    public String getYON() {
        return YON;
    }
    public void setYON(String YON) {
        this.YON = YON;
    }

    public String getSPTXT() {
        return SPTXT;
    }
    public void setSPTXT(String SPTXT) {
        this.SPTXT = SPTXT;
    }
}
