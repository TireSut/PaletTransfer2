package com.example.palettransfer;

public class stokDurumId {
    String MATERIAL,MTEXT,SKUNIT,DEVIR,GIRIS,CIKIS,KALAN;

    public stokDurumId(String MATERIAL, String MTEXT, String SKUNIT,String DEVIR,String GIRIS,String CIKIS,String KALAN) {
        this.MATERIAL = MATERIAL;
        this.MTEXT = MTEXT;
        this.SKUNIT = SKUNIT;
        this.DEVIR = DEVIR;
        this.GIRIS = GIRIS;
        this.CIKIS = CIKIS;
        this.KALAN = KALAN;

    }

    public String getMATERIAL() {
        return MATERIAL;
    }
    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public String getMTEXT() {return MTEXT;}
    public void setMTEXT(String MTEXT) { this.MTEXT = MTEXT;}

    public String getSKUNIT() {
        return SKUNIT;
    }
    public void setSKUNIT(String SKUNIT) { this.SKUNIT = SKUNIT;}

    public String getDEVIR() {
        return DEVIR;
    }
    public void setDEVIR(String DEVIR) { this.DEVIR = DEVIR;}

    public String getGIRIS() {
        return GIRIS;
    }
    public void setGIRIS(String GIRIS) { this.GIRIS = GIRIS;}

    public String getCIKIS() {
        return CIKIS;
    }
    public void setCIKIS(String CIKIS) { this.CIKIS = CIKIS;}

    public String getKALAN() {
        return KALAN;
    }
    public void setKALAN(String KALAN) { this.KALAN = KALAN;}

}
