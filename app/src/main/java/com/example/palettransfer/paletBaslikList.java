package com.example.palettransfer;

import java.io.Serializable;

public class paletBaslikList implements Serializable {
    private String PALETADI;
    private String URUNGRP;
    private String YETKIGRP;
    private int SKTGUN;
    private String ETIKETNAME;
    private String PARTNAME;
    private int SKTARTI1;
    private int SKTARTI1GUN;
    private int HATAKODU;
    private String HATATXT;

    public paletBaslikList() {
        this.PALETADI = "";
        this.URUNGRP = "";
        this.YETKIGRP = "";
        this.SKTGUN = 0;
        this.ETIKETNAME = "";
        this.PARTNAME = "";
        this.SKTARTI1 = 0;
        this.SKTARTI1GUN = 0;
        this.HATAKODU = 0;
        this.HATATXT = "";
    }

    public String getPALETADI() {
        return PALETADI;
    }

    public void setPALETADI(String PALETADI) {
        this.PALETADI = PALETADI;
    }

    public String getURUNGRP() {
        return URUNGRP;
    }

    public void setURUNGRP(String URUNGRP) {
        this.URUNGRP = URUNGRP;
    }

    public String getYETKIGRP() {
        return YETKIGRP;
    }

    public void setYETKIGRP(String YETKIGRP) {
        this.YETKIGRP = YETKIGRP;
    }

    public int getSKTGUN() {
        return SKTGUN;
    }

    public void setSKTGUN(int SKTGUN) {
        this.SKTGUN = SKTGUN;
    }

    public String getETIKETNAME() {
        return ETIKETNAME;
    }

    public void setETIKETNAME(String ETIKETNAME) {
        this.ETIKETNAME = ETIKETNAME;
    }

    public String getPARTNAME() {
        return PARTNAME;
    }

    public void setPARTNAME(String PARTNAME) {
        this.PARTNAME = PARTNAME;
    }

    public int getSKTARTI1() {
        return SKTARTI1;
    }

    public void setSKTARTI1(int SKTARTI1) {
        this.SKTARTI1 = SKTARTI1;
    }

    public int getSKTARTI1GUN() {
        return SKTARTI1GUN;
    }

    public void setSKTARTI1GUN(int SKTARTI1GUN) {
        this.SKTARTI1GUN = SKTARTI1GUN;
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
