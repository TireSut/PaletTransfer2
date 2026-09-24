package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PaletIcinUrunGrubuSecActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_icin_urun_grubu_sec);

        paletTuruList paletTuru = (paletTuruList) getIntent().getSerializableExtra("paletTuru");
        String cihazadi=getIntent().getStringExtra("CIHAZADI");

        // İleriki adımlar için bu obje kullanılacak
    }
}
