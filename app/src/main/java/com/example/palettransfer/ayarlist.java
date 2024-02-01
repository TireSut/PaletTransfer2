package com.example.palettransfer;

public class ayarlist {
    private String PRG,PARAM,DEGER,ACIKLAMA;
    public ayarlist(String PRG,String PARAM,String DEGER,String ACIKLAMA){
        this.PRG = PRG;
        this.PARAM = PARAM;
        this.DEGER = DEGER;
        this.ACIKLAMA = ACIKLAMA;
    }
    public String getPRG() {
        return PRG;
    }
    public void setPRG(String PRG) {
        this.PRG = PRG;
    }

    public String getPARAM() {
        return PARAM;
    }
    public void setPARAM(String PARAM) {
        this.PARAM = PARAM;
    }

    public String getDEGER() {
        return DEGER;
    }
    public void setDEGER(String DEGER) {
        this.DEGER = DEGER;
    }

    public String getACIKLAMA() {
        return ACIKLAMA;
    }
    public void setACIKLAMA(String ACIKLAMA) {
        this.ACIKLAMA = ACIKLAMA;
    }
}
