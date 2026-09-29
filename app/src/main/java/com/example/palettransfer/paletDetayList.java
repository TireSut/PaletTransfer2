package com.example.palettransfer;

import java.io.Serializable;

public class paletDetayList implements Serializable {
    private String MATERIAL;
    private String MTEXT;
    private double PALETMIKTAR;
    private double URUNMIKTAR;
    private String HATAKODU;
    private String HATATXT;
    private int STOCKTYPE;
    private int ISIRSALIYE;
    private int ISTOCK;
    private double YMIKTAR;

    public paletDetayList(String MATERIAL, String MTEXT, double PALETMIKTAR, double URUNMIKTAR) {
        this.MATERIAL = MATERIAL;
        this.MTEXT = MTEXT;
        this.PALETMIKTAR = PALETMIKTAR;
        this.URUNMIKTAR = URUNMIKTAR;
        this.HATAKODU = "";
        this.HATATXT = "";
        this.STOCKTYPE = 0;
        this.ISIRSALIYE = 0;
        this.ISTOCK = 0;
        this.YMIKTAR = 0.0;
    }

    public int getSTOCKTYPE() {
        return STOCKTYPE;
    }

    public void setSTOCKTYPE(int STOCKTYPE) {
        this.STOCKTYPE = STOCKTYPE;
    }

    public int getISIRSALIYE() {
        return ISIRSALIYE;
    }

    public void setISIRSALIYE(int ISIRSALIYE) {
        this.ISIRSALIYE = ISIRSALIYE;
    }

    public int getISTOCK() {
        return ISTOCK;
    }

    public void setISTOCK(int ISTOCK) {
        this.ISTOCK = ISTOCK;
    }

    public double getYMIKTAR() {
        return YMIKTAR;
    }

    public void setYMIKTAR(double YMIKTAR) {
        this.YMIKTAR = YMIKTAR;
    }

    public String getHATAKODU() {
        return HATAKODU;
    }

    public void setHATAKODU(String HATAKODU) {
        this.HATAKODU = HATAKODU;
    }

    public String getHATATXT() {
        return HATATXT;
    }

    public void setHATATXT(String HATATXT) {
        this.HATATXT = HATATXT;
    }

    public String getMATERIAL() {
        return MATERIAL;
    }

    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public String getMTEXT() {
        return MTEXT;
    }

    public void setMTEXT(String MTEXT) {
        this.MTEXT = MTEXT;
    }

    public double getPALETMIKTAR() {
        return PALETMIKTAR;
    }

    public void setPALETMIKTAR(double PALETMIKTAR) {
        this.PALETMIKTAR = PALETMIKTAR;
    }

    public double getURUNMIKTAR() {
        return URUNMIKTAR;
    }

    public void setURUNMIKTAR(double URUNMIKTAR) {
        this.URUNMIKTAR = URUNMIKTAR;
    }
}
