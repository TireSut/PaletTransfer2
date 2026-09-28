package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        final paletTuruList paletTuru = (paletTuruList) getIntent().getSerializableExtra("paletTuru");
        final String cihazadi = getIntent().getStringExtra("CIHAZADI");
        final String company = getIntent().getStringExtra("COMPANY");
        final String paletyetkigrubu = getIntent().getStringExtra("paletyetkigrubu");

        //paletyetkigrubu
        
        String localCihazadi = cihazadi;
        if (localCihazadi == null) localCihazadi = "";

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
                        Log.i("UrunGrubu", "Secilen Urun Grubu: " + urunGrup.getURUNGRP() + "-" + paletyetkigrubu);
                        Intent intent = new Intent(PaletIcinUrunGrubuSecActivity.this, PaletIcinUrunSecActivity.class);
                        intent.putExtra("paletTuru", paletTuru);
                        intent.putExtra("urunGrubu", urunGrup);
                        intent.putExtra("CIHAZADI", cihazadi);
                        intent.putExtra("COMPANY", company);
                        intent.putExtra("paletyetkigrubu", paletyetkigrubu);

                        startActivity(intent);
                    }
                });
                buttonContainer.addView(btn);
            }
        }
    }
}
