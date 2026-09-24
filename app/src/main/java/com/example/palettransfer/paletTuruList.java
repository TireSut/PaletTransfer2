package com.example.palettransfer;

public class paletTuruList {
    private int PALETTURU;
    private String PALETTURADI;
    private int HATAKODU;
    private String HATATXT;

    public paletTuruList(int PALETTURU, String PALETTURADI, int HATAKODU, String HATATXT) {
        this.PALETTURU = PALETTURU;
        this.PALETTURADI = PALETTURADI;
        this.HATAKODU = HATAKODU;
        this.HATATXT = HATATXT;
    }

    public int getPALETTURU() {
        return PALETTURU;
    }

    public void setPALETTURU(int PALETTURU) {
        this.PALETTURU = PALETTURU;
    }

    public String getPALETTURADI() {
        return PALETTURADI;
    }

    public void setPALETTURADI(String PALETTURADI) {
        this.PALETTURADI = PALETTURADI;
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
}
