package com.example.palettransfer;

public class deposayimId {

    private String PALETNUM,MATERIAL,MTEXT,SKUNIT,SKQUANTITY;
    public deposayimId(String PALETNUM,String MATERIAL,String MTEXT,String SKUNIT,String SKQUANTITY){
        this.PALETNUM = PALETNUM;
        this.MATERIAL = MATERIAL;
        this.MTEXT = MTEXT;
        this.SKUNIT = SKUNIT;
        this.SKQUANTITY = SKQUANTITY;
    }
    public String getPALETNUM() {
        return PALETNUM;
    }
    public void setPALETNUM(String PALETNUM) {
        this.PALETNUM = PALETNUM;
    }

    public String getMATERIAL() {return MATERIAL;}
    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public String getMTEXT() {
        return MTEXT;
    }
    public void setMTEXT(String MTEXT) {
        this.MTEXT = MTEXT;
    }

    public String getSKUNIT() {
        return SKUNIT;
    }
    public void setSKUNIT(String SKUNIT) {
        this.SKUNIT = SKUNIT;
    }

    public String getSKQUANTITY() {
        return SKQUANTITY;
    }
    public void setSKQUANTITY(String SMIKTAR) {
        this.SKQUANTITY = SKQUANTITY;
    }


}

