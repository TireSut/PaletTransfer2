package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PaletOlusturmaActivity extends AppCompatActivity {
    String paletyetkigrubu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palet_olusturma);
        String cihazAdi = getIntent().getStringExtra("CIHAZADI");
        paletyetkigrubu=VeriTabani.getPALETYETKIGRUP(cihazAdi);
        Log.i("PaletOlusturmaActivity", "paletyetkigrubu: "+paletyetkigrubu);
    }
}
