package com.example.palettransfer;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class transfer extends AppCompatActivity {
    final Context context = this;
    public String AYARGRUP, COMPANY, PLANT, PLANTTXT, WAREHOUSE, STOCKPLACE;
    public String TPLANT, TPLANTTXT, TWAREHOUSE, TSTOCKPLACE, YON, HRKNUM, PTUR, TARIH, BILTEKPARAM, ATUR;
    int info;
    String etesis, edepo, esp;
    TextView tv_tesisadi, tv_depo, tv_stokteri, lbl_miktar, tv_tarih, tv_yesildepo, tv_kirmizidepo;
    EditText edt_miktar, edt_barkod;
    public String barkodTxt;
    ImageButton btn_tara, btn_ara, btn_info, btn_exit;
    ImageView img_palet, img_kaynakdepo, img_hedefdepo;
    public TranslateAnimation animation = null;
    RecyclerView recyclerView;
    public ArrayList<urunlist> nam = new ArrayList<>();
    public ArrayList<urunlist> urn = new ArrayList<>();
    public urunAdaptor adapter;
    Switch sw_tursec;
    Bundle g;
    VeriTabani vt = new VeriTabani();
    canias ias = new canias();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.transfer);


        } catch (Exception e) {
            Log.e("dataList", e.toString());
        }
        sw_tursec = (Switch) findViewById(R.id.tursec);
        adapter = new urunAdaptor(nam, context);
        g = getIntent().getExtras();
        if (g != null) {
            AYARGRUP = g.getCharSequence("AYARGRUP").toString();
            COMPANY = g.getCharSequence("COMPANY").toString();
            YON = g.getCharSequence("YON").toString();
            TARIH = g.getCharSequence("TARIH").toString();
            BILTEKPARAM = g.getCharSequence("BILTEKPARAM").toString();
            ATUR = g.getCharSequence("ATUR").toString();
            if (YON.equals("CIKIS")) {
                PLANT = g.getCharSequence("PLANT").toString();
                PLANTTXT = g.getCharSequence("PLANTTXT").toString();
                WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
                STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
                TPLANT = g.getCharSequence("TPLANT").toString();
                TPLANTTXT = g.getCharSequence("TPLANTTXT").toString();
                TWAREHOUSE = g.getCharSequence("TWAREHOUSE").toString();
                TSTOCKPLACE = g.getCharSequence("TSTOCKPLACE").toString();
            }
            if (YON.equals("GIRIS")) {
                PLANT = g.getCharSequence("TPLANT").toString();
                PLANTTXT = g.getCharSequence("TPLANTTXT").toString();
                WAREHOUSE = g.getCharSequence("TWAREHOUSE").toString();
                STOCKPLACE = g.getCharSequence("TSTOCKPLACE").toString();
                TPLANT = g.getCharSequence("PLANT").toString();
                TPLANTTXT = g.getCharSequence("PLANTTXT").toString();
                TWAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
                TSTOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
            }
        }

        btn_exit = findViewById(R.id.exitbtn);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });


        if (AYARGRUP.equals("")) {
            return;
        }
        // Tanımlar
        tv_kirmizidepo=findViewById(R.id.depokirmiziTextView);
        tv_yesildepo=findViewById(R.id.depoyesilTextView);
        tv_tarih = findViewById(R.id.tarih);
        btn_ara = findViewById(R.id.paletAraBtn);
        btn_tara = findViewById(R.id.paletTaramaBtn);
        tv_tesisadi = findViewById(R.id.TESISADITXT);
        tv_depo = findViewById(R.id.DEPOTXT);
        tv_stokteri = findViewById(R.id.STOKYERI);
        edt_barkod = findViewById(R.id.paletBarkod);
        edt_barkod.setInputType(InputType.TYPE_NULL);
        edt_miktar = findViewById(R.id.miktarbox);
        edt_miktar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_miktar.setText(edt_miktar.getText().toString().replaceAll(",","."));
            }
        });
        lbl_miktar = findViewById(R.id.miktarlbl);
        img_palet = findViewById(R.id.paletimg);
        img_hedefdepo = findViewById(R.id.hedefdepoimg);
        img_kaynakdepo = findViewById(R.id.kaynakdepoimg);
        tv_tesisadi.setText(PLANT + "-" + PLANTTXT);
        tv_depo.setText("Depo: [" + WAREHOUSE + "]  /  [" + TWAREHOUSE + "]");
        tv_stokteri.setText("Stok Yeri: [" + STOCKPLACE + "]  /  [" + TSTOCKPLACE + "]");
        tv_tarih.setText(TARIH);
        edt_barkod.setInputType(InputType.TYPE_CLASS_NUMBER);
        // RecyclerView tanımları
        recyclerView = (RecyclerView) findViewById(R.id.rvUrunListe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        nam.clear();
        info = 0;
        if (ATUR.equals("true")) {
            PTUR = "S";
            sw_tursec.setChecked(true);
            sw_tursec.setText(sw_tursec.getTextOn());
            lbl_miktar.setVisibility(View.VISIBLE);
            edt_miktar.setVisibility(View.VISIBLE);
        } else {
            PTUR = "P";
            sw_tursec.setChecked(false);
            sw_tursec.setText(sw_tursec.getTextOff());
            lbl_miktar.setVisibility(View.INVISIBLE);
            edt_miktar.setVisibility(View.INVISIBLE);
        }
        //*******************************************

        sw_tursec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw_tursec.isChecked()) {
                    PTUR = "S";
                    sw_tursec.setText(sw_tursec.getTextOn());
                    lbl_miktar.setVisibility(View.VISIBLE);
                    edt_miktar.setVisibility(View.VISIBLE);
                } else {
                    PTUR = "P";
                    sw_tursec.setText(sw_tursec.getTextOff());
                    lbl_miktar.setVisibility(View.INVISIBLE);
                    edt_miktar.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (YON.equals("GIRIS")) {
            animation = new TranslateAnimation(420.0f, 0.0f, 0.0f, 0.0f);
            animation.setDuration(3000);
            animation.setRepeatCount(500);
            animation.setRepeatMode(1);
            animation.setFillAfter(true);
            img_palet.startAnimation(animation);
            tv_kirmizidepo.setText(WAREHOUSE+"/"+STOCKPLACE);
            tv_yesildepo.setText(TWAREHOUSE+"/"+TSTOCKPLACE);
/*
            tv_depo.setText("Depo: [" + WAREHOUSE + "]  /  [" + TWAREHOUSE + "]");
            tv_stokteri.setText("Stok Yeri: [" + STOCKPLACE + "]  /  [" + TSTOCKPLACE + "]");
  */
        } else {
            animation = new TranslateAnimation(0.0f, 420.0f, 0.0f, 0.0f);
            animation.setDuration(3000);
            animation.setRepeatCount(500);
            animation.setRepeatMode(1);
            animation.setFillAfter(true);
            img_palet.startAnimation(animation);
            tv_yesildepo.setText(WAREHOUSE+"/"+STOCKPLACE);
            tv_kirmizidepo.setText(TWAREHOUSE+"/"+TSTOCKPLACE);
        }

        //   edt_barkod.setText("2109270101");
        btn_tara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(transfer.this);
                scanIntegrator.initiateScan();
            }
        });
        btn_ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paletbul();
            }
        });
        edt_barkod.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    paletbul();
                }
                return false;
            }
        });

        btn_info = findViewById(R.id.infobtn);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info == 0) {
                    info = 1;
                    barkodTxt = "";
                    edt_barkod.setText(barkodTxt);
                    sw_tursec.setChecked(false);
                    sw_tursec.setVisibility(View.INVISIBLE);
                    btn_info.setBackgroundResource(R.drawable.infon);
                    img_palet.clearAnimation();
                    img_palet.setVisibility(View.INVISIBLE);
                    img_hedefdepo.setVisibility(View.INVISIBLE);
                    img_kaynakdepo.setVisibility(View.INVISIBLE);
                    nam.clear();
                    adapter.notifyDataSetChanged();
                    etesis = tv_tesisadi.getText().toString();
                    edepo = tv_depo.getText().toString();
                    esp = tv_stokteri.getText().toString();
                    tv_tesisadi.setText("");
                    tv_depo.setText("");
                    tv_stokteri.setText("");
                } else {
                    info = 0;
                    barkodTxt = "";
                    edt_barkod.setText(barkodTxt);
                    sw_tursec.setVisibility(View.VISIBLE);
                    btn_info.setBackgroundResource(R.drawable.infooff);
                    img_palet.startAnimation(animation);
                    img_palet.setVisibility(View.VISIBLE);
                    img_hedefdepo.setVisibility(View.VISIBLE);
                    img_kaynakdepo.setVisibility(View.VISIBLE);
                    nam.clear();
                    adapter.notifyDataSetChanged();
                    tv_tesisadi.setText(etesis);
                    tv_depo.setText(edepo);
                    tv_stokteri.setText(esp);
                }
            }
        });

    }

    //*************************************************************************
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                barkodTxt = intent.getStringExtra("SCAN_RESULT");
                edt_barkod.setText(barkodTxt);
                paletbul();
            }
        } catch (Exception e) {
            String hatatxt = e.toString();
        }
    }

    protected void paletbul() {
        img_palet.clearAnimation();
        // getUrunListe (String barkod, String comp,String pla,String wh, String sp,Boolean tur,int miktar)
        barkodTxt = edt_barkod.getText().toString();
        if (barkodTxt.equals("")) {
            return;
        }
        if (barkodTxt.substring(0, 2).equals("27") || barkodTxt.substring(0, 2).equals("28")) {
            PTUR = "S";
            sw_tursec.setChecked(true);
            sw_tursec.setText(sw_tursec.getTextOn());
            lbl_miktar.setVisibility(View.VISIBLE);
            edt_miktar.setVisibility(View.VISIBLE);
            edt_miktar.setText(barkodTxt.substring(12, 13) +barkodTxt.substring(7, 9) + "." + barkodTxt.substring(9, 11));
            barkodTxt = barkodTxt.substring(0, 7);
        }
        double mik = Double.parseDouble("0" + edt_miktar.getText().toString());
        if (sw_tursec.isChecked() && mik <= 0.0) {
            hata_dlg("Palet / Ürün Bulma Hatası", "Miktar (0)Sıfır olamaz.", true);
            return;
        }
        nam.clear();
        if (info == 0) {
            urn = vt.getUrunListe(barkodTxt, COMPANY, PLANT, WAREHOUSE, STOCKPLACE, TPLANT, TWAREHOUSE, TSTOCKPLACE, sw_tursec.isChecked(), "0" + edt_miktar.getText().toString());
        } else {
            urn = vt.getUrunListe(barkodTxt, COMPANY, "%", "%", "%", "%", "%", "%", false, "0");
        }
        try {
            for (int i = 0; i < urn.size(); i++) {
                if (!urn.get(i).getHATA().equals("0")) {
                    // ***************** hata var ise hatayı yaz
                    hata_dlg("Palet / Ürün Bulma Hatası", urn.get(i).getHATATXT(), true);
                    return;
                } else {
                    //ITEMNUM,MATERIAL,MTEXT, SKQUANTITY, BOLUNEN,KALAN,HATA,HATATXT;
                    nam.add(new urunlist(urn.get(i).getITEMNUM(), urn.get(i).getMATERIAL(), urn.get(i).getMTEXT(), urn.get(i).getSKQUANTITY(), urn.get(i).getBOLUNEN(), urn.get(i).getKALAN(), urn.get(i).getHATA(), urn.get(i).getHATATXT()));
                }
            }
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("dataList", e.toString());
        }
        adapter.notifyDataSetChanged();
        if (info == 0) {
            transferkayit();
        } else {
            String ack = vt.getPaletBilgi(barkodTxt);
            ack = ack + "";
            if (!ack.equals("")) {
                int bas = 0;
                int bit = ack.indexOf("#", 0);
                String deg = ack.substring(bas, bit);
                tv_depo.setText(deg);
                bas = bit + 1;
                bit = ack.indexOf("#", bas);
                deg = ack.substring(bas, bit);
                tv_stokteri.setText(deg);
                bas = bit + 1;
                bit = ack.length();
                deg = ack.substring(bas, bit);
                tv_tesisadi.setText(deg);
            }
        }
        return;
    }

    protected void transferkayit() {
        // ************************canias transfer hareketi yap ******************************
        HRKNUM = vt.HRKNUM;
        String trsonuc = ias.palettransfer(barkodTxt, HRKNUM, COMPANY, PLANT, WAREHOUSE, STOCKPLACE, COMPANY, TPLANT, TWAREHOUSE, TSTOCKPLACE, PTUR, edt_miktar.getText().toString() + "", TARIH, BILTEKPARAM);
        String hata = "";
        hata = XMLdegeroku(trsonuc, "//TBLMSG/ROW/HATAKODU");
        hata = hata + "";
        if (!hata.equals("0")) {
            hata = XMLdegeroku(trsonuc, "//TBLMSG/ROW/HATATXT");
            hata_dlg("Palet / Ürün Transfer", hata, true);
            return;
        }
        // *************************************************************************************
        hata = "Transfer işlemi gerçekleştirildi\n " + XMLdegeroku(trsonuc, "//TBLMSG/ROW/HATATXT");
        hata_dlg("Palet / Ürün Transfer", hata, false);
        img_palet.startAnimation(animation);
        // img_palet.startAnimation(animation);
        return;
    }

    protected void hata_dlg(String baslik, String hatatxt, boolean ishata) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(baslik);
        builder.setMessage(hatatxt);

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_miktar.setText("");
                edt_barkod.setText("");
                nam.clear();
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = builder.create();
// set the gravity
        alertDialog.getWindow().setGravity(Gravity.TOP);
// set the margin
        alertDialog.getWindow().getAttributes().verticalMargin = 0.1F;
        if (ishata) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
        } else {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_light);

        }
        alertDialog.show();
    }

    //*************** XML NODE okuma ********************************
    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        if (node != null) {
            if (node.hasChildNodes()) {
                Node child = node.getFirstChild();
                while (child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    protected String XMLdegeroku(String xmltxt, String root) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = domFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document dDoc = null;
        try {
            dDoc = builder.parse(new InputSource(new ByteArrayInputStream(xmltxt.getBytes("UTF-8"))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = null;  // these 2 lines
        try {
            expr = xPath.compile(root);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        String str = null;  // are different
        try {
            str = (String) expr.evaluate(dDoc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return str;
    }
}
