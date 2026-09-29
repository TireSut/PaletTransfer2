package com.example.palettransfer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PaletOlusturmaActivity extends AppCompatActivity {
    
    private ArrayList<paletDetayList> detaylar;

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

        LinearLayout container = findViewById(R.id.llUrunlerContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        if (container != null) {
            if (detaylar.isEmpty()) {
                Toast.makeText(this, "Palet detayı bulunamadı.", Toast.LENGTH_SHORT).show();
            } else if (detaylar.size() == 1 && detaylar.get(0).getHATATXT() != null && !detaylar.get(0).getHATATXT().isEmpty()) {
                Toast.makeText(this, detaylar.get(0).getHATATXT(), Toast.LENGTH_LONG).show();
            } else {
                for (int i = 0; i < detaylar.size(); i++) {
                    final paletDetayList detay = detaylar.get(i);
                    View rowView = inflater.inflate(R.layout.row_palet_detay, container, false);
                    
                    TextView tvMText = rowView.findViewById(R.id.tvMText);
                    EditText etYmiktar = rowView.findViewById(R.id.etYmiktar);

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

                    container.addView(rowView);
                }
            }
        }

        Button btnOlustur = findViewById(R.id.btnPaletiOlustur);
        btnOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Burada detaylar listesi, kullanıcının güncellediği miktarları içerecektir.
                // Sonraki aşamada bu veriler gönderilebilir.
                Toast.makeText(PaletOlusturmaActivity.this, "Veriler hazır. İleri aşamaya geçilecek.", Toast.LENGTH_SHORT).show();
                Log.i("PaletOlusturma", "Mevcut Detaylar: " + detaylar.size() + " adet ürün.");
                for(paletDetayList pd : detaylar) {
                     Log.i("PaletOlusturma", "Urun: " + pd.getMTEXT() + ", Miktar: " + pd.getYMIKTAR());
                }
            }
        });
    }
}
