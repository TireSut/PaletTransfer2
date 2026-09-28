package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class PaletIcinUrunSecActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_icin_urun_sec);

        final paletTuruList paletTuru = (paletTuruList) getIntent().getSerializableExtra("paletTuru");
        final paletUrunGrubuList urunGrubu = (paletUrunGrubuList) getIntent().getSerializableExtra("urunGrubu");
        final String cihazadi = getIntent().getStringExtra("CIHAZADI");
        final String company = getIntent().getStringExtra("COMPANY");
        final String paletyetkigrubu = getIntent().getStringExtra("paletyetkigrubu");

        String localCihazadi = cihazadi;
        if (localCihazadi == null) localCihazadi = "";
        String paletturStr = paletTuru != null ? String.valueOf(paletTuru.getPALETTURU()) : "";
        String urunGrubuStr = urunGrubu != null ? urunGrubu.getURUNGRP() : "";

        ArrayList<paletUrunlerList> urunler = VeriTabani.getPaletUrunler(paletyetkigrubu, paletturStr, urunGrubuStr);

        LinearLayout buttonContainer = findViewById(R.id.buttonContainerUrunler);

        if (buttonContainer != null) {
            if (urunler.isEmpty()) {
                Toast.makeText(this, "Ürün bulunamadı.", Toast.LENGTH_SHORT).show();
            } else {
                for (final paletUrunlerList urun : urunler) {
                    if (urun.getHATAKODU() != 0) {
                        Toast.makeText(this, "Hata: " + urun.getHATATXT(), Toast.LENGTH_LONG).show();
                        continue;
                    }
                    Button btn = new Button(this);
                    String btnText = urun.getMTEXT() != null && !urun.getMTEXT().isEmpty() ? urun.getMTEXT() : urun.getMATERIAL();
                    btn.setText(btnText);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("Urun", "Secilen Urun: " + urun.getMATERIAL() + " - " + urun.getMTEXT());
                            // İleriki adımlar için bu obje kullanılacak
                        }
                    });
                    buttonContainer.addView(btn);
                }
            }
        }
    }
}
