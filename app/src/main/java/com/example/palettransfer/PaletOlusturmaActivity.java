package com.example.palettransfer;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaletOlusturmaActivity extends AppCompatActivity {
    
    private ArrayList<paletDetayList> detaylar;
    private int selectedRowIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_olusturma);

        final String cihazadi = getIntent().getStringExtra("CIHAZADI");
        final String company = getIntent().getStringExtra("COMPANY");
        final String paletyetkigrubu = getIntent().getStringExtra("paletyetkigrubu");
        final paletTuruList paletTuru = (paletTuruList) getIntent().getSerializableExtra("paletTuru");
        final paletUrunGrubuList urunGrubu = (paletUrunGrubuList) getIntent().getSerializableExtra("urunGrubu");
        final paletUrunlerList paletUrun = (paletUrunlerList) getIntent().getSerializableExtra("paletUrun");

        String paletturStr = paletTuru != null ? String.valueOf(paletTuru.getPALETTURU()) : "";
        String urunGrubuStr = urunGrubu != null ? urunGrubu.getURUNGRP() : "";
        String pltad = paletUrun != null ? paletUrun.getPALETADI() : "";

        // wh ve sp ayarlardan alalım
        String tempWh = VeriTabani.getAyarString2(cihazadi, "WAREHOUSE");
        String tempSp = VeriTabani.getAyarString2(cihazadi, "STOCKPLACE");
        
        // Hata kontrolü
        if(tempWh == null || tempWh.startsWith("#HATA")) tempWh = "001";
        if(tempSp == null || tempSp.startsWith("#HATA")) tempSp = "URETIM";
        
        final String wh = tempWh;
        final String sp = tempSp;

        detaylar = VeriTabani.getPaletDetay(company, paletyetkigrubu, paletturStr, urunGrubuStr, pltad, wh, sp);

        final LinearLayout container = findViewById(R.id.llUrunlerContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        final EditText etTarih = findViewById(R.id.etTarih);
        final EditText etSKT = findViewById(R.id.etSKT);
        final TextView tvPartiNo = findViewById(R.id.tvPartiNo);
        final CheckBox cbPlusOne = findViewById(R.id.cbPlusOne);
        setupDatePicker(etTarih);
        setupDatePicker(etSKT);

        final int[] sktGun = {0};
        final String[] partName = {""};
        final paletBaslikList baslik = VeriTabani.getPaletBaslik(company, paletyetkigrubu, paletturStr, urunGrubuStr, pltad, "96b506030f07ba1abe37e8df3c46ec6eb56550316de93f9440b9d0b996bc4f57");
        if (baslik != null) {
            if (baslik.getSKTGUN() > 0) {
                sktGun[0] = baslik.getSKTGUN();
            }
            if (baslik.getPARTNAME() != null && !baslik.getPARTNAME().trim().isEmpty()) {
                partName[0] = baslik.getPARTNAME().trim();
            }
        }
        
        if (sktGun[0] == 0 && detaylar != null && !detaylar.isEmpty()) {
            for (paletDetayList pd : detaylar) {
                if (pd.getSKTGUN() > sktGun[0]) {
                    sktGun[0] = pd.getSKTGUN();
                }
            }
        }

        etTarih.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                        Date date = sdf.parse(s.toString());
                        if (date != null) {
                            // SKT Calculation
                            Calendar sktCal = Calendar.getInstance();
                            sktCal.setTime(date);
                            int extraDays = (cbPlusOne != null && cbPlusOne.isChecked()) ? 1 : 0;
                            sktCal.add(Calendar.DAY_OF_YEAR, sktGun[0] + extraDays);
                            etSKT.setText(sdf.format(sktCal.getTime()));
                            
                            // Parti No Calculation
                            Calendar cal = Calendar.getInstance(new Locale("tr", "TR"));
                            cal.setFirstDayOfWeek(Calendar.MONDAY);
                            cal.setMinimalDaysInFirstWeek(4);
                            cal.setTime(date);
                            
                            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
                            int dayOfWeekIso = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1; // 1=Mon, 7=Sun
                            int year2Digit = cal.get(Calendar.YEAR) % 100;
                            
                            String pno = String.format(Locale.getDefault(), "%02d%02d%s%02d", 
                                weekOfYear, dayOfWeekIso, partName[0], year2Digit);
                            tvPartiNo.setText(pno);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (cbPlusOne != null) {
            cbPlusOne.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                    String s = etTarih.getText().toString();
                    if (s.length() > 0) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                            Date date = sdf.parse(s);
                            if (date != null) {
                                Calendar sktCal = Calendar.getInstance();
                                sktCal.setTime(date);
                                int extraDays = isChecked ? 1 : 0;
                                sktCal.add(Calendar.DAY_OF_YEAR, sktGun[0] + extraDays);
                                etSKT.setText(sdf.format(sktCal.getTime()));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        // Populate today's date
        Calendar cal = Calendar.getInstance();
        String todayDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        etTarih.setText(todayDate);
        // etSKT is automatically set by the TextWatcher above


        if (container != null) {
            if (detaylar.isEmpty()) {
                Toast.makeText(this, "Palet detayı bulunamadı.", Toast.LENGTH_SHORT).show();
            } else if (detaylar.size() == 1 && detaylar.get(0).getHATATXT() != null && !detaylar.get(0).getHATATXT().isEmpty()) {
                Toast.makeText(this, detaylar.get(0).getHATATXT(), Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < detaylar.size(); i++) {
                    final paletDetayList detay = detaylar.get(i);
                    final View rowView = inflater.inflate(R.layout.row_palet_detay, container, false);
                    
                    TextView tvIndex = rowView.findViewById(R.id.tvIndex);
                    TextView tvMText = rowView.findViewById(R.id.tvMText);
                    EditText etYmiktar = rowView.findViewById(R.id.etYmiktar);

                    tvIndex.setText(String.valueOf(i + 1));
                    tvMText.setText(detay.getMTEXT());
                    etYmiktar.setText(String.valueOf(detay.getYMIKTAR()));

                    etYmiktar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                detay.setYMIKTAR(Double.parseDouble(s.toString()));
                            } catch (NumberFormatException e) {
                                detay.setYMIKTAR(0.0);
                            }
                        }
                    });

                    rowView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // deselect all
                            for (int j = 0; j < container.getChildCount(); j++) {
                                View child = container.getChildAt(j);
                                child.setBackgroundColor(Color.WHITE);
                            }
                            v.setBackgroundColor(Color.LTGRAY);
                            selectedRowIndex = container.indexOfChild(v);
                        }
                    });

                    container.addView(rowView);
                }
            }
        }

        View btnSatirSil = findViewById(R.id.btnSatirSil);
        if (btnSatirSil != null) {
            btnSatirSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRowIndex != -1 && container != null) {
                        detaylar.remove(selectedRowIndex);
                        container.removeViewAt(selectedRowIndex);
                        selectedRowIndex = -1;
                        // re-index
                        for (int j = 0; j < container.getChildCount(); j++) {
                            View child = container.getChildAt(j);
                            TextView tvIndex = child.findViewById(R.id.tvIndex);
                            if (tvIndex != null) {
                                tvIndex.setText(String.valueOf(j + 1));
                            }
                        }
                    } else {
                        Toast.makeText(PaletOlusturmaActivity.this, "Lütfen silinecek satırı seçin.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        View btnKaydet = findViewById(R.id.btnKaydet);
        if (btnKaydet != null) {
            btnKaydet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder headBuilder = new StringBuilder();
                    headBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

                    int itemNum = 10;
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
                    String currentDate = dateTimeFormat.format(new Date());

                    String partiNo = tvPartiNo.getText().toString();
                    String tarih = etTarih.getText().toString() + " 00:00:00";
                    String skt = etSKT.getText().toString() + " 00:00:00";
                    String pName = partName[0];
                    
                    EditText etPltSayisi = findViewById(R.id.tvPltSayisi);
                    String pltSayisi = etPltSayisi != null ? etPltSayisi.getText().toString() : "0";

                    // --- TIRPLTHEAD ---
                    headBuilder.append("<TIRPLTHEAD>\n");
                    headBuilder.append("  <SATIR>\n");
                    headBuilder.append("    <CLIENT>00</CLIENT>\n");
                    headBuilder.append("    <COMPANY>").append(company != null ? company : "01").append("</COMPANY>\n");
                    headBuilder.append("    <PLANT>01</PLANT>\n");
                    headBuilder.append("    <PALETNUM></PALETNUM>\n");
                    headBuilder.append("    <PARTINUM>").append(partiNo).append("</PARTINUM>\n");
                    headBuilder.append("    <TARIH>").append(tarih).append("</TARIH>\n");
                    headBuilder.append("    <DURUM>1</DURUM>\n");
                    headBuilder.append("    <SONHRKNUM></SONHRKNUM>\n");
                    headBuilder.append("    <ISDELETE>0</ISDELETE>\n");
                    headBuilder.append("    <PURORDTYPE></PURORDTYPE>\n");
                    headBuilder.append("    <PURORDNUM></PURORDNUM>\n");
                    headBuilder.append("    <PURORDITEM>0</PURORDITEM>\n");
                    headBuilder.append("    <VENDOR></VENDOR>\n");
                    headBuilder.append("    <LTEXT>").append(pltad != null ? pltad : "").append("</LTEXT>\n");
                    String etiketName = (baslik != null && baslik.getETIKETNAME() != null) ? baslik.getETIKETNAME() : "";
                    headBuilder.append("    <ETIKETNAME>").append(etiketName).append("</ETIKETNAME>\n");
                    headBuilder.append("    <PALETSIRA>").append(pltSayisi).append("</PALETSIRA>\n");
                    headBuilder.append("    <CREATEDBY></CREATEDBY>\n");
                    headBuilder.append("    <CREATEDAT>").append(currentDate).append("</CREATEDAT>\n");
                    headBuilder.append("    <CHANGEDBY>IASWS</CHANGEDBY>\n");
                    headBuilder.append("    <CHANGEDAT>").append(currentDate).append("</CHANGEDAT>\n");
                    headBuilder.append("  </SATIR>\n");
                    headBuilder.append("</TIRPLTHEAD>");
                    
                    String xmlHeadResult = headBuilder.toString();

                    // --- TIRPLTITEM ---
                    StringBuilder itemBuilder = new StringBuilder();
                    itemBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                    itemBuilder.append("<TIRPLTITEM>\n");
                    for (paletDetayList pd : detaylar) {
                        itemBuilder.append("  <SATIR>\n");
                        itemBuilder.append("    <CLIENT>00</CLIENT>\n");
                        itemBuilder.append("    <COMPANY>").append(company != null ? company : "01").append("</COMPANY>\n");
                        itemBuilder.append("    <PLANT>01</PLANT>\n");
                        itemBuilder.append("    <PALETNUM></PALETNUM>\n"); // Palet numarası sonradan eklenebilir
                        itemBuilder.append("    <ITEMNUM>").append(itemNum).append("</ITEMNUM>\n");
                        itemBuilder.append("    <PARTINUM>").append(partiNo).append("</PARTINUM>\n");
                        itemBuilder.append("    <TARIH>").append(tarih).append("</TARIH>\n");
                        itemBuilder.append("    <MATERIAL>").append(pd.getMATERIAL() != null ? pd.getMATERIAL() : "").append("</MATERIAL>\n");
                        itemBuilder.append("    <MTEXT>").append(pd.getMTEXT() != null ? pd.getMTEXT() : "").append("</MTEXT>\n");
                        itemBuilder.append("    <SKUNIT>AD</SKUNIT>\n");
                        itemBuilder.append("    <WAREHOUSE>").append(wh).append("</WAREHOUSE>\n");
                        itemBuilder.append("    <STOCKPLACE>").append(sp).append("</STOCKPLACE>\n");
                        itemBuilder.append("    <SKQUANTITY>").append(pd.getYMIKTAR()).append("</SKQUANTITY>\n");
                        itemBuilder.append("    <ISDELETE>0</ISDELETE>\n");
                        itemBuilder.append("    <STOCKTYPE>").append(pd.getSTOCKTYPE()).append("</STOCKTYPE>\n");
                        itemBuilder.append("    <ISSTOCK>").append(pd.getISTOCK()).append("</ISSTOCK>\n");
                        itemBuilder.append("    <ISPRICE>0</ISPRICE>\n"); // ISPRICE paletDetayList'te olmadığı için 0 varsayıldı
                        itemBuilder.append("    <ISIRSALIYE>").append(pd.getISIRSALIYE()).append("</ISIRSALIYE>\n");
                        itemBuilder.append("    <SKT>").append(skt).append("</SKT>\n");
                        itemBuilder.append("    <ETIKETSAYISI>0</ETIKETSAYISI>\n");
                        itemBuilder.append("    <KOPYA>0</KOPYA>\n");
                        itemBuilder.append("    <CREATEDBY></CREATEDBY>\n"); // Kullanıcı adı eklenebilir
                        itemBuilder.append("    <CREATEDAT>").append(currentDate).append("</CREATEDAT>\n");
                        itemBuilder.append("    <CHANGEDBY>IASWS</CHANGEDBY>\n");
                        itemBuilder.append("    <CHANGEDAT>").append(currentDate).append("</CHANGEDAT>\n");
                        itemBuilder.append("    <PARTNAME>").append(pName != null ? pName : "").append("</PARTNAME>\n");
                        itemBuilder.append("    <BOLUNEN>0.0</BOLUNEN>\n");
                        itemBuilder.append("  </SATIR>\n");
                        itemNum += 10;
                    }
                    itemBuilder.append("</TIRPLTITEM>");

                    final String xmlItemResult = itemBuilder.toString();
                    final String finalPltSayisi = pltSayisi;
                    final String finalXmlHeadResult = xmlHeadResult;
                    
                    Log.i("PaletOlusturmaXML", "--- HEAD XML ---\n" + finalXmlHeadResult);
                    Log.i("PaletOlusturmaXML", "--- ITEM XML ---\n" + xmlItemResult);
                    
                    final String token = "e9000db68b72689c9db78db8076bbab62235d822ca6f9f6218c04ffbb44aff09";
                    
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String urlString = "https://webservis.tiresutkoop.org/iot/tsiotws.asmx/paletkayit";
                                java.net.URL url = new java.net.URL(urlString);
                                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                conn.setDoOutput(true);
                                
                                String postData = "etiketsayisi=" + java.net.URLEncoder.encode(finalPltSayisi, "UTF-8") +
                                                  "&headxml=" + java.net.URLEncoder.encode(finalXmlHeadResult, "UTF-8") +
                                                  "&itemxml=" + java.net.URLEncoder.encode(xmlItemResult, "UTF-8") +
                                                  "&token=" + java.net.URLEncoder.encode(token, "UTF-8");
                                                  
                                java.io.OutputStream os = conn.getOutputStream();
                                os.write(postData.getBytes("UTF-8"));
                                os.flush();
                                os.close();
                                
                                int responseCode = conn.getResponseCode();
                                String resultText = "";
                                if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
                                    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();
                                    
                                    String xmlResponse = response.toString();
                                    int startIndex = xmlResponse.indexOf("<string");
                                    if (startIndex != -1) {
                                        int closeBracketIndex = xmlResponse.indexOf(">", startIndex);
                                        int endIndex = xmlResponse.indexOf("</string>", closeBracketIndex);
                                        if (closeBracketIndex != -1 && endIndex != -1 && closeBracketIndex < endIndex) {
                                            resultText = xmlResponse.substring(closeBracketIndex + 1, endIndex);
                                            resultText = resultText.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
                                        } else {
                                            resultText = xmlResponse;
                                        }
                                    } else {
                                        resultText = xmlResponse;
                                    }
                                    
                                    // Eğer dönen string içinde bir tablo yapısı ve HATATXT varsa onu al
                                    if (resultText.contains("<HATATXT>")) {
                                        int startHata = resultText.indexOf("<HATATXT>") + 9;
                                        int endHata = resultText.indexOf("</HATATXT>");
                                        if (endHata > startHata) {
                                            resultText = resultText.substring(startHata, endHata);
                                        }
                                    }
                                } else {
                                    resultText = "HTTP_HATA_" + responseCode;
                                }
                                
                                final String finalResult = resultText;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        android.widget.Toast.makeText(PaletOlusturmaActivity.this, finalResult, android.widget.Toast.LENGTH_LONG).show();
                                    }
                                });
                                
                            } catch (Exception e) {
                                final String errMsg = e.getMessage();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        android.widget.Toast.makeText(PaletOlusturmaActivity.this, "Hata: " + errMsg, android.widget.Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
        }
    }

    private void setupDatePicker(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PaletOlusturmaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, monthOfYear + 1, year);
                                editText.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}
