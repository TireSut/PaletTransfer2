package com.example.palettransfer;

public class urunlist {
    private String ITEMNUM,MATERIAL,MTEXT, SKQUANTITY, BOLUNEN,KALAN,HATA,HATATXT;
    public urunlist(String ITEMNUM,String MATERIAL,String MTEXT,String SKQUANTITY,String BOLUNEN,String KALAN,String HATA,String HATATXT){
        this.ITEMNUM = ITEMNUM;
        this.MATERIAL = MATERIAL;
        this.MTEXT = MTEXT;
        this.SKQUANTITY = SKQUANTITY;
        this.BOLUNEN = BOLUNEN;
        this.KALAN = KALAN;
        this.HATA = HATA;
        this.HATATXT = HATATXT;
    }
    public String getITEMNUM() {
        return ITEMNUM;
    }
    public void setITEMNUM(String ITEMNUM) {
        this.ITEMNUM = ITEMNUM;
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

    public String getSKQUANTITY() {
        return SKQUANTITY;
    }
    public void setSKQUANTITY(String SKQUANTITY) {
        this.SKQUANTITY = SKQUANTITY;
    }

    public String getBOLUNEN() {
        return BOLUNEN;
    }
    public void setBOLUNEN(String BOLUNEN) {
        this.BOLUNEN = BOLUNEN;
    }

    public String getKALAN() {
        return KALAN;
    }
    public void setKALAN(String KALAN) {
        this.KALAN = KALAN;
    }

    public String getHATA() {
        return HATA;
    }
    public void setHATA(String HATA) {
        this.HATA = HATA;
    }

    public String getHATATXT() {
        return HATATXT;
    }
    public void setHATATXT(String HATATXT) {
        this.HATATXT = HATATXT;
    }
}
