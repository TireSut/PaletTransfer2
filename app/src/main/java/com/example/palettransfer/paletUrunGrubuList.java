package com.example.palettransfer;

import java.io.Serializable;

public class paletUrunGrubuList implements Serializable {
    private String URUNGRP;
    private int HATAKODU;
    private String HATATXT;

    public paletUrunGrubuList(String URUNGRP, int HATAKODU, String HATATXT) {
        this.URUNGRP = URUNGRP;
        this.HATAKODU = HATAKODU;
        this.HATATXT = HATATXT;
    }

    public String getURUNGRP() {
        return URUNGRP;
    }

    public void setURUNGRP(String URUNGRP) {
        this.URUNGRP = URUNGRP;
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
        return "paletUrunGrubuList{" +
                "URUNGRP='" + URUNGRP + '\'' +
                ", HATAKODU=" + HATAKODU +
                ", HATATXT='" + HATATXT + '\'' +
                '}';
    }
}
