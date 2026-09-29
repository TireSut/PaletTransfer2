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
        String wh = VeriTabani.getAyarString2(cihazadi, "WAREHOUSE");
        String sp = VeriTabani.getAyarString2(cihazadi, "STOCKPLACE");
        
        // Hata kontrolü
        if(wh == null || wh.startsWith("#HATA")) wh = "001";
        if(sp == null || sp.startsWith("#HATA")) sp = "URETIM";

        detaylar = VeriTabani.getPaletDetay(company, paletyetkigrubu, paletturStr, urunGrubuStr, pltad, wh, sp);

        final LinearLayout container = findViewById(R.id.llUrunlerContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        final EditText etTarih = findViewById(R.id.etTarih);
        final EditText etSKT = findViewById(R.id.etSKT);
        final TextView tvPartiNo = findViewById(R.id.tvPartiNo);
        setupDatePicker(etTarih);
        setupDatePicker(etSKT);

        final int[] sktGun = {0};
        final String[] partName = {""};
        paletBaslikList baslik = VeriTabani.getPaletBaslik(company, paletyetkigrubu, paletturStr, urunGrubuStr, pltad, "96b506030f07ba1abe37e8df3c46ec6eb56550316de93f9440b9d0b996bc4f57");
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
                            sktCal.add(Calendar.DAY_OF_YEAR, sktGun[0]);
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
                    Toast.makeText(PaletOlusturmaActivity.this, "Veriler hazır. İleri aşamaya geçilecek.", Toast.LENGTH_SHORT).show();
                    Log.i("PaletOlusturma", "Mevcut Detaylar: " + detaylar.size() + " adet ürün.");
                    for(paletDetayList pd : detaylar) {
                         Log.i("PaletOlusturma", "Urun: " + pd.getMTEXT() + ", Miktar: " + pd.getYMIKTAR());
                    }
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
