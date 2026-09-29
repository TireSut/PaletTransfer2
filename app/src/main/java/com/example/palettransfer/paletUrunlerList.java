package com.example.palettransfer;

import java.io.Serializable;

public class paletUrunlerList implements Serializable {
    private String PALETADI;
    private int HATAKODU;
    private String HATATXT;

    public paletUrunlerList(String PALETADI, int HATAKODU, String HATATXT) {
        this.PALETADI = PALETADI;
        this.HATAKODU = HATAKODU;
        this.HATATXT = HATATXT;
    }

    public String getPALETADI() {
        return PALETADI;
    }

    public void setPALETADI(String PALETADI) {
        this.PALETADI = PALETADI;
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
                "PALETADI='" + PALETADI + '\'' +
                ", HATAKODU=" + HATAKODU +
                ", HATATXT='" + HATATXT + '\'' +
                '}';
    }
}
