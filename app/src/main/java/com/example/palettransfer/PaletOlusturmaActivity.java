package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class PaletOlusturmaActivity extends AppCompatActivity {
    String paletyetkigrubu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_olusturma);
        String cihazAdi = getIntent().getStringExtra("CIHAZADI");
        paletyetkigrubu=VeriTabani.getPALETYETKIGRUP(cihazAdi);
        ArrayList<paletTuruList> paletturleri=VeriTabani.getPaletTurleri(paletyetkigrubu);
        for (paletTuruList ee:paletturleri) {
            Log.i("TAG", "onCreate: "+ee.toString());
        }

        Log.i("PaletOlusturmaActivity", "paletyetkigrubu: "+paletyetkigrubu);
    }
}
