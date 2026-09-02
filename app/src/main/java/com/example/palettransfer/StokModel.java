package com.example.palettransfer;

public class StokModel {
    private String material;
    private String stext;
    private double stok;

    public StokModel(String material, String stext, double stok) {
        this.material = material;
        this.stext = stext;
        this.stok = stok;
    }

    public String getMaterial() { return material; }
    public String getStext() { return stext; }
    public double getStok() { return stok; }
}
