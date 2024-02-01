package com.example.palettransfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.renderscript.Script;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VeriTabani {
    public Connection conn;
    public static String url = "jdbc:jtds:sqlserver://192.168.87.213/TIRESUT604;integratedSecurity=true";
    public static String urltest = "jdbc:jtds:sqlserver://192.168.87.213/TEST802;integratedSecurity=true";
    public static String usr = "IAS";
    public static String pwd = "IAS";
    public static String driver = "net.sourceforge.jtds.jdbc.Driver";
    public Statement query;
    public Statement query1;
    public String HRKNUM;


    public int sqlBaglan() {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usr, pwd);
            query = conn.createStatement();
            query1 = conn.createStatement();
            return 1;
        } catch (Exception e) {
            String a = e.toString();

        }
        return 0;
    }

    public ArrayList<depolist> getDepoListe (String prg, String comp,String sp) {
        ArrayList<depolist> depoliste = new ArrayList<>();
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT P.PLANT AS PLA,P.STEXT AS PLATXT,D.WAREHOUSE AS WH,D.STEXT AS WHTXT,SUBSTRING(A.DEGER,11,30) AS SP,S.STEXT AS SPTXT ";
            sqltxt = sqltxt + " FROM TIRAYARLAR A,IASBAS005 P,IASBAS039X D,IASINV007X S ";
            sqltxt = sqltxt + " WHERE A.PRG='"+prg+"' AND A.PARAM='DEPO' AND SUBSTRING(A.DEGER,1,2)='"+comp+"' ";
            sqltxt = sqltxt + " AND P.CLIENT='00' AND P.COMPANY='"+comp+"' AND P.PLANT=SUBSTRING(A.DEGER,4,2) ";
            sqltxt = sqltxt + " AND D.CLIENT='00' AND D.COMPANY=P.COMPANY AND D.PLANT=P.PLANT AND D.WAREHOUSE=SUBSTRING(A.DEGER,7,3) ";
            sqltxt = sqltxt + " AND SUBSTRING(A.DEGER,11,30)!='"+sp+"' ";
            sqltxt = sqltxt + " AND S.CLIENT=D.CLIENT AND S.COMPANY=D.COMPANY AND S.PLANT=D.PLANT AND S.WAREHOUSE=D.WAREHOUSE AND S.STOCKPLACE=SUBSTRING(A.DEGER,11,30) AND S.LANGU='T'; ";

            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    depoliste.add(new depolist(rs.getString("PLA"), rs.getString("PLATXT"), rs.getString("WH"), rs.getString("WHTXT"), rs.getString("SP"),"", rs.getString("SPTXT") ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return depoliste;
    }
    public String getAyarString (String prg, String param, String alan) {
        String rtnstr="";
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT PRG,PARAM,DEGER,ACIKLAMA   FROM TIRAYARLAR WHERE PRG='"+prg+"' AND PARAM='"+param+"'";
            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    rtnstr= rs.getString(alan);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rtnstr;
    }
    public String getTesisAdi (String comp, String pla) {
        String rtnstr="";
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT STEXT FROM IASBAS005 WHERE CLIENT='00' AND COMPANY='"+comp+"' AND PLANT='"+pla+"'";
            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    rtnstr= rs.getString("STEXT");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rtnstr;
    }
    public int getAutid () {
        int rtnint=0;
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT MAX(ID) AS ID FROM TIRAYARLAR ";
            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    rtnint= rs.getInt("ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rtnint=rtnint+1;
        return rtnint;
    }
    public void setCihazId (String CihazID) {
        ResultSet rs=null;
        int autoid=getAutid();
        PreparedStatement ps;
        if (sqlBaglan() > 0) {
            try {
                String sqltxt = "DELETE FROM TIRAYARLAR WHERE PRG='CIHAZ' AND PARAM=?";
                ps=conn.prepareStatement(sqltxt);
                ps.setString(1,CihazID);
                int x=ps.executeUpdate();
                sqltxt = "INSERT INTO TIRAYARLAR (ID,PRG,PARAM) VALUES (?,'CIHAZ',?)";
                ps=conn.prepareStatement(sqltxt);
                ps.setInt(1,autoid);
                ps.setString(2,CihazID);
                x=ps.executeUpdate();
                x=x;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void setAnaDepo (String agrp,String pla,String tpla,String wh,String twh,String sp,String tsp) {
        ResultSet rs=null;
        int autoid=getAutid();
        PreparedStatement ps;
        if (sqlBaglan() > 0) {
            try {
                String sqltxt = "UPDATE TIRAYARLAR SET DEGER='"+tpla+"' WHERE PRG='"+agrp+"' AND PARAM='PLANT' AND DEGER='"+pla+"' ";
                ps=conn.prepareStatement(sqltxt);
                int x=ps.executeUpdate();
                sqltxt = "UPDATE TIRAYARLAR SET DEGER='"+twh+"' WHERE PRG='"+agrp+"' AND PARAM='WAREHOUSE' AND DEGER='"+wh+"' ";
                ps=conn.prepareStatement(sqltxt);
                x=ps.executeUpdate();
                sqltxt = "UPDATE TIRAYARLAR SET DEGER='"+tsp+"' WHERE PRG='"+agrp+"' AND PARAM='STOCKPLACE' AND DEGER='"+sp+"' ";
                ps=conn.prepareStatement(sqltxt);
                x=ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<urunlist> getUrunListe (String barkod, String comp,String pla,String wh, String sp,String tpla,String twh, String tsp,Boolean tur,String miktar) {
        ArrayList<urunlist> urunliste = new ArrayList<>();
        ResultSet rs=null;
        String sqltxt;
        String hatatxt;
        if (sqlBaglan() > 0) {
            if (tur.equals(false)){
                //****************** palet işlemleri *****************************************************
                urunliste.clear();
                sqltxt = "";
                sqltxt = sqltxt + " SELECT TOP 1 HRKNUM,PALETNUM FROM TIRPLTHAREKET ";
                sqltxt = sqltxt + " WHERE CLIENT='00' AND PALETNUM='" + barkod + "' AND ISDELETE=0 AND DURUM=1";
                sqltxt = sqltxt + " AND COMPANY ='" + comp + "' AND PLANT LIKE '" + pla + "' AND WAREHOUSE LIKE '" + wh + "'";
                sqltxt = sqltxt + " AND STOCKPLACE LIKE '" +sp + "' ";
                sqltxt = sqltxt + " ORDER BY HRKNUM DESC";
                try {
                    rs = query.executeQuery(sqltxt);
                    if (!rs.isBeforeFirst()) {
                        // palet ilgili depo ve stok yerinde değil ise
                        hatatxt=barkod+" Numaralı palet Firma:"+comp+" Tesis:"+pla+" Depo:"+wh+" Stokyeri:"+sp+" de bulunamadı.";
                        urunliste.add(new urunlist("","","", "", "","","1",hatatxt) );
                        return urunliste;
                    } else{
                        while(rs.next()) {
                            HRKNUM= rs.getString("HRKNUM");
                        }
                        sqltxt = "";
                        sqltxt = sqltxt + " SELECT ITEMNUM,MATERIAL,MTEXT, FORMAT(SKQUANTITY,'###,###,###.###') AS SKQUANTITY, BOLUNEN,SKQUANTITY AS KALAN,0 AS HATA, '' AS HATATXT " ;
                        sqltxt = sqltxt + " FROM TIRPLTITEM ";
                        sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY ='" + comp + "' AND PLANT LIKE '" + pla + "' ";
                        sqltxt = sqltxt + " AND PALETNUM ='" + barkod + "' AND ISDELETE=0 ";
                        rs = query.executeQuery(sqltxt);
                        while(rs.next()) {
                            urunliste.add(new urunlist(rs.getString("ITEMNUM"), rs.getString("MATERIAL"), rs.getString("MTEXT"), rs.getString("SKQUANTITY"), rs.getString("BOLUNEN"), rs.getString("KALAN"), rs.getString("HATA"), rs.getString("HATATXT")) );
                        }
                    }
                    return urunliste;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //****************** ÜRÜN BAKODUNDAN BULAM *****************************************************
                sqltxt = "";
                sqltxt = sqltxt + " SELECT DISTINCT 10 AS ITEMNUM,B.MATERIAL,M.STEXT AS MTEXT,0 AS SKQUANTITY,0 AS BOLUNEN,0 AS KALAN,0 AS HATA,'' AS HATATXT " ;
                sqltxt = sqltxt + " FROM IASMATCODES B,IASMATX M,IASMATSTOCK D1,IASMATSTOCK D2 " ;
                sqltxt = sqltxt + " WHERE B.CLIENT='00' AND B.COMPANY='"+comp+"' AND B.BARCODE='"+barkod+"' " ;
                sqltxt = sqltxt + " AND B.CLIENT=M.CLIENT AND B.COMPANY=M.COMPANY AND B.MATERIAL=M.MATERIAL AND M.PLANT='*' AND M.LANGU='T' AND M.TEXTTYPE='B' " ;
                sqltxt = sqltxt + " AND B.CLIENT=D1.CLIENT AND B.COMPANY=D1.COMPANY AND B.MATERIAL=D1.MATERIAL AND D1.PLANT='"+pla+"' AND D1.WAREHOUSE='"+wh+"' AND D1.STOCKPLACE='"+sp+"' " ;
                sqltxt = sqltxt + " AND B.CLIENT=D2.CLIENT AND B.COMPANY=D2.COMPANY AND B.MATERIAL=D2.MATERIAL AND D2.PLANT='"+tpla+"' AND D2.WAREHOUSE='"+twh+"' AND D2.STOCKPLACE='"+tsp+"' " ;
                try {
                    rs = query.executeQuery(sqltxt);
                    if (!rs.isBeforeFirst()) {
                        // palet ilgili depo ve stok yerinde değil ise
                        hatatxt=barkod+" Numaralı barkod yok yada  Kaynak:"+comp+"."+pla+"."+wh+"."+sp+" Hedef:"+comp+"."+tpla+"."+twh+"."+tsp+" depolarında tanımsız";
                        urunliste.add(new urunlist("","","", "", "","","1",hatatxt) );
                        return urunliste;
                    } else{
                        rs = query.executeQuery(sqltxt);
                        while(rs.next()) {
                            urunliste.add(new urunlist(rs.getString("ITEMNUM"), rs.getString("MATERIAL"), rs.getString("MTEXT"), miktar, rs.getString("BOLUNEN"), rs.getString("KALAN"), rs.getString("HATA"), rs.getString("HATATXT")) );
                        }                    }
                    return urunliste;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else{
            hatatxt="SQL Veritabanına bağlanamadı";
            urunliste.add(new urunlist("","","", "", "","","99",hatatxt) );
        }
        return urunliste;
    }
    public ArrayList<deposayimId> getDepoSayim (String comp,String pla,String wh,String sp,String trh) {
        ArrayList<deposayimId> deposayim = new ArrayList<>();
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT PALETNUM,MATERIAL,MTEXT,SKUNIT,SKQUANTITY";
            sqltxt = sqltxt + " FROM TIRPLTSAYIM ";
            sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY='"+comp +"'";
            sqltxt = sqltxt + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
            sqltxt = sqltxt + " AND TARIH='"+trh +"'";
            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    deposayim.add(new deposayimId(rs.getString("PALETNUM"), rs.getString("MATERIAL"), rs.getString("MTEXT"), rs.getString("SKUNIT"), rs.getString("SKQUANTITY"))) ;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deposayim;
    }

    public String setSayim (String comp,String pla,String wh,String sp,String trh,String paletnum, String mat,String mtext,String skunit,String miktar,Boolean tur,Boolean ekle,int sil) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        String hatatxt="";
        Double toplam=0.00;
        // kayit varmi sorgusu
        String sqltxt = "SELECT PALETNUM,MATERIAL,MTEXT,SKUNIT,SKQUANTITY";
        sqltxt = sqltxt + " FROM TIRPLTSAYIM";
        sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY='"+comp +"'";
        sqltxt = sqltxt + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
        sqltxt = sqltxt + " AND MATERIAL='"+mat+"' AND PALETNUM='"+paletnum+"'";
        sqltxt = sqltxt + " AND TARIH='"+trh +"'";

        // yeni kayit ekleme sorgusu
        String sqlins="INSERT INTO TIRPLTSAYIM (CLIENT,COMPANY,PALETNUM,TARIH,PLANT,WAREHOUSE,STOCKPLACE,MATERIAL,MTEXT,SKUNIT,SKQUANTITY) ";
        sqlins =sqlins+ " VALUES ('00','"+comp+"','"+paletnum+"','"+trh+"','"+pla+"','"+wh+"','"+sp+"','"+mat+"','"+mtext+"','"+skunit+"',"+miktar+") ";

        // Sayim satir silme sorgusu
        String sqldel = "DELETE FROM TIRPLTSAYIM ";
        sqldel = sqldel + " WHERE CLIENT='00' AND COMPANY='"+comp +"' AND TARIH='"+trh +"'";
        sqldel = sqldel + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
        sqldel = sqldel + " AND MATERIAL='"+mat+"' AND PALETNUM='"+paletnum+"' ";

        // sayim satir güncelle sorgusu
        String sqlupd = "UPDATE TIRPLTSAYIM ";
        sqlupd = sqlupd + " SET SKQUANTITY = #miktar# ";
        sqlupd = sqlupd + " WHERE CLIENT='00' AND COMPANY='"+comp +"' AND TARIH='"+trh +"'";
        sqlupd = sqlupd + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
        sqlupd = sqlupd + " AND MATERIAL='"+mat+"' AND PALETNUM='"+paletnum+"' ";
        int kayitvar=0;
        if (sqlBaglan() > 0) {
            try {
                if (sil==1) {
                    ps=conn.prepareStatement(sqldel);
                    Boolean x=ps.execute();
                    if (x.equals("true")){
                        hatatxt ="del hatası";
                    }
                    return hatatxt;
                }
                rs = query.executeQuery(sqltxt);
                kayitvar = 0;
                while (rs.next()) {
                    toplam=rs.getDouble("SKQUANTITY");
                    kayitvar = 1;
                }
                if (kayitvar == 0) {
                //  KAYİT YOKSA EKLE
                        ps=conn.prepareStatement(sqlins);
                    Boolean x=ps.execute();
                    if (x.equals("true")){
                        hatatxt ="insert hatası";
                    }
                        return hatatxt;
                } else{
                    // KAYIT VAR İSE
                    if (tur.equals(true)) {
                        // PALET İSE HİÇBİRŞEY YAPMA - ÜRÜN İSE MİKTARI AYARLAYIP GÜNCELLE
                        if (ekle.equals(false)){
                            toplam=toplam+Double.parseDouble(miktar);
                        } else {
                            toplam=Double.parseDouble(miktar);
                        }
                        sqltxt=sqlupd;
                        sqltxt=sqltxt.replace("#miktar#",toplam.toString() );
                        ps = conn.prepareStatement(sqltxt);
                        Boolean x=ps.execute();
                        if (x.equals("true")){
                            hatatxt ="update hatası";
                        }
                        return hatatxt;
                    }
            }
            } catch (SQLException e) {
                e.printStackTrace();
                hatatxt=e.toString();
            }
        } else {
            hatatxt="SQL Veritabanına bağlanamadı";
        }
        return hatatxt ;
    }
    public String getSayimUrunBul (String barkod, String comp,String pla,String wh, String sp,String trh,String miktar,Boolean tur,Boolean ekle,int sil) {
        ResultSet rs=null;
        String sqltxt="";
        String hatatxt="";
        if (sqlBaglan() > 0) {
            if (tur.equals(false)){
                //****************** palet işlemleri *****************************************************
                sqltxt = "";
                sqltxt = sqltxt + " SELECT TOP 1 HRKNUM,PALETNUM FROM TIRPLTHAREKET ";
                sqltxt = sqltxt + " WHERE CLIENT='00' AND PALETNUM='" + barkod + "' AND ISDELETE=0 AND DURUM=1";
                sqltxt = sqltxt + " AND COMPANY ='" + comp + "' AND PLANT LIKE '" + pla + "' AND WAREHOUSE LIKE '" + wh + "'";
                sqltxt = sqltxt + " AND STOCKPLACE LIKE '" +sp + "' ";
                sqltxt = sqltxt + " ORDER BY HRKNUM DESC";
                try {
                    rs = query.executeQuery(sqltxt);
                    if (!rs.isBeforeFirst()) {
                        // palet ilgili depo ve stok yerinde değil ise
                        hatatxt=barkod+" Numaralı palet Firma:"+comp+" Tesis:"+pla+" Depo:"+wh+" Stokyeri:"+sp+" de bulunamadı.";
                        return hatatxt;
                    } else{
                        while(rs.next()) {
                            HRKNUM= rs.getString("HRKNUM");
                        }
                        sqltxt = "";
                        sqltxt = sqltxt + " SELECT MATERIAL,MTEXT,SKUNIT, SKQUANTITY " ;
                        sqltxt = sqltxt + " FROM TIRPLTITEM ";
                        sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY ='" + comp + "' AND PLANT LIKE '" + pla + "' ";
                        sqltxt = sqltxt + " AND PALETNUM ='" + barkod + "' AND ISDELETE=0 AND STOCKTYPE=0";
                        rs = query.executeQuery(sqltxt);
                        while(rs.next()) {
                          hatatxt = setSayim(comp,pla, wh,sp,trh,barkod,rs.getString("MATERIAL"),rs.getString("MTEXT"),rs.getString("SKUNIT"),rs.getString("SKQUANTITY"),tur,ekle,sil);
                        }
                    }
                    return hatatxt;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //****************** ÜRÜN BAKODUNDAN BULAM *****************************************************
                sqltxt = "";
                sqltxt = sqltxt + " SELECT DISTINCT B.MATERIAL,M.STEXT AS MTEXT,B.BUNIT AS SKUNIT " ;
                sqltxt = sqltxt + " FROM IASMATCODES B,IASMATX M,IASMATSTOCK D1 " ;
                sqltxt = sqltxt + " WHERE B.CLIENT='00' AND B.COMPANY='"+comp+"' AND B.BARCODE='"+barkod+"' " ;
                sqltxt = sqltxt + " AND B.CLIENT=M.CLIENT AND B.COMPANY=M.COMPANY AND B.MATERIAL=M.MATERIAL AND M.PLANT='*' AND M.LANGU='T' AND M.TEXTTYPE='B' " ;
                sqltxt = sqltxt + " AND B.CLIENT=D1.CLIENT AND B.COMPANY=D1.COMPANY AND B.MATERIAL=D1.MATERIAL AND D1.PLANT='"+pla+"' AND D1.WAREHOUSE='"+wh+"' AND D1.STOCKPLACE='"+sp+"' " ;
                try {
                    rs = query.executeQuery(sqltxt);
                    if (!rs.isBeforeFirst()) {
                        // palet ilgili depo ve stok yerinde değil ise
                        hatatxt=barkod+" Numaralı barkod yok yada  Kaynak:"+comp+"."+pla+"."+wh+"."+sp+" deposunda tanımsız";
                        return hatatxt;
                    } else{
                      //  rs = query.executeQuery(sqltxt);
                        while(rs.next()) {
                            hatatxt = setSayim(comp,pla, wh,sp,trh,"URUN",rs.getString("MATERIAL"),rs.getString("MTEXT"),rs.getString("SKUNIT"),miktar,tur,ekle,sil);
                       return hatatxt;
                        }                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    hatatxt = e.toString();
                }
            }
        } else{
            hatatxt="SQL Veritabanına bağlanamadı";
        }
        return hatatxt;
    }
    public String getPaletBilgi (String paletnum) {
        String rtnstr="";
        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT TOP 1 'Trh:'+FORMAT(TARIH,'dd.MM.yyy')+'#No:'+HRKNUM+' iasNo:'+DOCTYPE+'-'+DOCNUM+'" +
                    "#Tes:['+PLANT+'] Depo:['+WAREHOUSE+'] SY:['+STOCKPLACE+']' AS ACIKLAMA " +
                    "FROM TIRPLTHAREKET " +
                    "WHERE CLIENT='00' AND COMPANY='01' AND PALETNUM='"+paletnum+"' AND DURUM=1 " +
                    "ORDER BY TARIH DESC;";
            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    rtnstr= rs.getString("ACIKLAMA");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rtnstr;
    }
    public ArrayList<stokDurumId> getDepoStokHareket (String comp,String pla,String wh,String sp,String trh) {
        ArrayList<stokDurumId> depoStok = new ArrayList<>();

        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT MATERIAL,MTEXT,SKUNIT,0.00 AS DEVIR";
            sqltxt = sqltxt + " ,SUM(CASE WHEN QPOSTWAY=0 THEN SKQUANTITY ELSE 0 END) AS GIRIS ";
            sqltxt = sqltxt + " ,SUM(CASE WHEN QPOSTWAY=1 THEN SKQUANTITY ELSE 0 END) AS CIKIS ";
            sqltxt = sqltxt + " ,0.00 AS KALAN ";
            sqltxt = sqltxt + " FROM IASINVITEM ";
            sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY='"+comp +"'";
            sqltxt = sqltxt + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
            sqltxt = sqltxt + " AND DOCDATE='"+trh +"'";
            sqltxt = sqltxt + " AND (MATTYPE='URUN' OR MATTYPE='YRMM') " ;
            sqltxt = sqltxt + " GROUP BY MATERIAL,MTEXT,SKUNIT " ;

            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    depoStok.add(new stokDurumId(rs.getString("MATERIAL"), rs.getString("MTEXT"), rs.getString("SKUNIT"), rs.getString("DEVIR"), rs.getString("GIRIS"), rs.getString("CIKIS"), rs.getString("KALAN") ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return depoStok;
    }
    public ArrayList<stokDurumId> getDepoDevir (String comp,String pla,String wh,String sp,String trh) {
        ArrayList<stokDurumId> depoStok = new ArrayList<>();

        ResultSet rs=null;
        if (sqlBaglan() > 0) {
            String sqltxt = "SELECT MATERIAL,MTEXT,SKUNIT";
            sqltxt = sqltxt + " ,SUM((1-(QPOSTWAY*2))*SKQUANTITY) AS DEVIR ";
            sqltxt = sqltxt + " ,0.00 AS GIRIS,0.00 AS CIKIS, 0.00 AS KALAN ";
            sqltxt = sqltxt + " FROM IASINVITEM ";
            sqltxt = sqltxt + " WHERE CLIENT='00' AND COMPANY='"+comp +"'";
            sqltxt = sqltxt + " AND PLANT='"+pla+"' AND WAREHOUSE='"+wh+"' AND STOCKPLACE='"+sp+"'";
            sqltxt = sqltxt + " AND DOCDATE<'"+trh +"'";
            sqltxt = sqltxt + " AND (MATTYPE='URUN' OR MATTYPE='YRMM') " ;
            sqltxt = sqltxt + " GROUP BY MATERIAL,MTEXT,SKUNIT " ;

            try {
                rs = query.executeQuery(sqltxt);
                while(rs.next()) {
                    depoStok.add(new stokDurumId(rs.getString("MATERIAL"), rs.getString("MTEXT"), rs.getString("SKUNIT"), rs.getString("DEVIR"), rs.getString("GIRIS"), rs.getString("CIKIS"), rs.getString("KALAN") ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return depoStok;
    }
}
