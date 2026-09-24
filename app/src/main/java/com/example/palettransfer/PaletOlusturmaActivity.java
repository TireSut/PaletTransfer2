package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Intent;

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
        
        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);

        for (final paletTuruList ee:paletturleri) {
            Log.i("TAG", "onCreate: "+ee.toString());
            Button btn = new Button(this);
            btn.setText(ee.getPALETTURADI());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PaletOlusturmaActivity.this, PaletIcinUrunGrubuSecActivity.class);
                    intent.putExtra("paletTuru", ee);
                    intent.putExtra("CIHAZADI", cihazAdi);
                    startActivity(intent);
                }
            });
            buttonContainer.addView(btn);
        }

        Log.i("PaletOlusturmaActivity", "paletyetkigrubu: "+paletyetkigrubu);
    }
}
