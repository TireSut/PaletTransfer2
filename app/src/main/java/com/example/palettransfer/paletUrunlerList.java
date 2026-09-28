package com.example.palettransfer;

import java.io.Serializable;

public class paletUrunlerList implements Serializable {
    private String MATERIAL;
    private String MTEXT;
    private int HATAKODU;
    private String HATATXT;

    public paletUrunlerList(String MATERIAL, String MTEXT, int HATAKODU, String HATATXT) {
        this.MATERIAL = MATERIAL;
        this.MTEXT = MTEXT;
        this.HATAKODU = HATAKODU;
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

    public int getHATAKODU() {
        return HATAKODU;
    }

    public void setHATAKODU(int HATAKODU) {
        this.HATAKODU = HATAKODU;
    }

    public String getHATATXT() {
        return HATATXT;
    }

    public void setHATATXT(String HATATXT) {
        this.HATATXT = HATATXT;
    }

    @Override
    public String toString() {
        return "paletUrunlerList{" +
                "MATERIAL='" + MATERIAL + '\'' +
                ", MTEXT='" + MTEXT + '\'' +
                ", HATAKODU=" + HATAKODU +
                ", HATATXT='" + HATATXT + '\'' +
                '}';
    }
}
