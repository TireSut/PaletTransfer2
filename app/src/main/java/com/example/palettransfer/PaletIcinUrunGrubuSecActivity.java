package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PaletIcinUrunGrubuSecActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_icin_urun_grubu_sec);

        paletTuruList paletTuru = (paletTuruList) getIntent().getSerializableExtra("paletTuru");
        String cihazadi = getIntent().getStringExtra("CIHAZADI");
        String company = getIntent().getStringExtra("COMPANY");
        if (cihazadi == null) cihazadi = "";

        String paletyetkigrubu = VeriTabani.getPALETYETKIGRUP(cihazadi);
        String paletturStr = paletTuru != null ? String.valueOf(paletTuru.getPALETTURU()) : "";
        ArrayList<paletUrunGrubuList> urunGruplari = VeriTabani.getPaletUrunGrubu(company, paletyetkigrubu, paletturStr);

        LinearLayout buttonContainer = findViewById(R.id.buttonContainerUrunGrubu);

        if (buttonContainer != null) {
            for (final paletUrunGrubuList urunGrup : urunGruplari) {
                Button btn = new Button(this);
                btn.setText(urunGrup.getURUNGRP());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("UrunGrubu", "Secilen Urun Grubu: " + urunGrup.getURUNGRP());
                        // İleriki adımlar için bu obje kullanılacak
                    }
                });
                buttonContainer.addView(btn);
            }
        }
    }
}
