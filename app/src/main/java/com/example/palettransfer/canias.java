package com.example.palettransfer;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.BasicHttpContext;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HttpContext;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;
import com.google.zxing.client.result.AddressBookAUResultParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class canias {
    private static final String NAMESPACE = "https://api.authorize.net/soap/v1/";
    private static final String SOAP_ACTION = "https://api.authorize.net/soap/v1/AuthenticateTest";
    private static final String METHOD_NAME = "AuthenticateTest";
    private static final String HATAXML ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><TBLMSG><ROW><HATAKODU>#HATAKODU#</HATAKODU><HATATXT>#HATATXT#</HATATXT></ROW></TBLMSG>\n";
    String postparam;

    public String palettransfer(String strPltNum, String strHrkNum, String strCompany,String strPlant,String strWareHouse,String strStockPlace,String strCompanyT,String strPlantT,String strWareHouseT,String strStockPlaceT,String strPtur,String strMiktar,String strTarih, String strBiltek) {
        String strIsBiltek="0";
        if (!strBiltek.equals("")) {strIsBiltek="8";}
        postparam = strPltNum+ "," + strHrkNum + "," + strCompany + "," + strPlant + "," + strWareHouse + "," + strStockPlace;
    postparam = postparam + "," + strCompanyT + "," + strPlantT + "," + strWareHouseT + "," + strStockPlaceT+","+strPtur+","+strMiktar+","+strTarih;
        postparam = postparam + "," +strIsBiltek + "," +strBiltek;
      return iasgetdatatbl("PltTransfer", postparam, "", "");
        }
    private static String iasgetdatatbl(String srvname, String ppostparam, String usrname, String pwrd) {
        int YesNo = 0, hata=0,bas=0, bit=0;
        String execurl = "http://canias.tiresutkoop.org:8888/caniasWS603/services/iasWebService?method=callIASService&sessionid=puserid&serviceid=psrvname&args=pargs&returntype=STRING&permanent=false";
        String logouturl = "http://canias.tiresutkoop.org:8888/caniasWS603/services/iasWebService?method=logout&p_strSessionId=puserid";
        String loginurl = "http://canias.tiresutkoop.org:8888/caniasWS603/services/iasWebService?method=login&p_strClient=00&p_strLanguage=T&p_strDBName=TIRESUT&p_strDBServer=CANIAS&p_strAppServer=192.168.87.219:27499/WEB&p_strUserName=IASWS&p_strPassword=ias!123";
        String sourcecode = "";
        String userid="";
        String xmltblstr;
//**************************** bağlan ve suse id al *************************************
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(loginurl);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                sourcecode= EntityUtils.toString(entity);
                bas=sourcecode.indexOf("<loginReturn xsi:type=",0);
                bas=sourcecode.indexOf(">",bas)+1;
                bit=sourcecode.indexOf("</loginReturn>");
                userid=sourcecode.substring(bas,bit);
            }
            else{
                sourcecode=HATAXML;
                sourcecode=sourcecode.replace("#HATAKODU#","2");
                sourcecode=sourcecode.replace("#HATATXT#","HATA : Login sonucu boş döndü.");
                return sourcecode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            sourcecode=HATAXML;
            sourcecode=sourcecode.replace("#HATAKODU#","3");
            sourcecode=sourcecode.replace("#HATATXT#","HATA : Bağlanamadı."+e.toString());
            return sourcecode;
        }
        //********************* işlemler ************************************
        execurl=execurl.replace("puserid",userid);
        execurl=execurl.replace("psrvname",srvname);
        execurl=execurl.replace("pargs",ppostparam);
        try {
            HttpGet httpGet1 = new HttpGet(execurl);
            HttpResponse response1 = httpClient.execute(httpGet1, localContext);
            HttpEntity entity1 = response1.getEntity();

            if(entity1 != null){
                 String tirnak = Character.toString((char)34) ;
                sourcecode= EntityUtils.toString(entity1);
                sourcecode=sourcecode.replace("Ok@", "");
                sourcecode=sourcecode.replace("No@", "");
                sourcecode=sourcecode.replace("&lt;", "<");
                sourcecode=sourcecode.replace("&gt;", ">");
                sourcecode=sourcecode.replace("&quot;",tirnak);
                bas=sourcecode.indexOf("<?xml version",40);
                bit=sourcecode.indexOf("</callIASServiceReturn>");
                sourcecode=sourcecode.substring(bas,bit);
            }
            else{
                sourcecode=HATAXML;
                sourcecode=sourcecode.replace("#HATAKODU#","4");
                sourcecode=sourcecode.replace("#HATATXT#","HATA : "+srvname+" sonucu boş döndü.");
                return sourcecode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            sourcecode=HATAXML;
            sourcecode=sourcecode.replace("#HATAKODU#","5");
            sourcecode=sourcecode.replace("#HATATXT#","HATA : "+srvname+" / "+e.toString());
            return sourcecode;
        }
        //****************** çıkış *******************************************/
        logouturl=logouturl.replace("puserid",userid);
         httpGet = new HttpGet(logouturl);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
         } catch (IOException e) {
            e.printStackTrace();
        }
        return sourcecode;
    }

}
