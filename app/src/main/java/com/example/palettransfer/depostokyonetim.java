package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class depostokyonetim extends AppCompatActivity {
    final Context context = this;
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE,TARIH;
    public String TPLANT,TPLANTTXT,TWAREHOUSE,TSTOCKPLACE;
    TextView tv_tesisadi,tv_depo,tv_stokteri;
    ImageButton btn_exit,depoStokMik;;
    Button btn_deposec;
    public ana_depoAdapter adapter ;
    Bundle g;
    VeriTabani vt = new VeriTabani();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_depostokyonetim);

        }catch (Exception e) {
            Log.e("dataList",e.toString());
            e.toString();
        }
        g = getIntent().getExtras();
        if (g != null) {
            AYARGRUP = g.getCharSequence("AYARGRUP").toString();
            COMPANY = g.getCharSequence("COMPANY").toString();
            PLANT = g.getCharSequence("PLANT").toString();
            PLANTTXT = g.getCharSequence("PLANTTXT").toString();
            WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
            STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
        }
        if ("".equals(AYARGRUP)) {
            return;
        }
        // Tanımlar
        depoStokMik =findViewById(R.id.depoStokMik);
        tv_tesisadi=findViewById(R.id.TESISADITXT);
        tv_depo=findViewById(R.id.DEPOTXT);
        tv_stokteri=findViewById(R.id.STOKYERI);


        tv_tesisadi.setText(PLANT+"-"+PLANTTXT);
        tv_depo.setText("Depo: ["+WAREHOUSE+"]");
        tv_stokteri.setText("Stok Yeri: ["+STOCKPLACE+"]");
        // Ana DEpo Secim
        btn_deposec=findViewById(R.id.DepoSecimBtn);
        btn_deposec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(depostokyonetim.this,ana_depo_secim.class);
                intent.putExtra("AYARGRUP",AYARGRUP );
                intent.putExtra("COMPANY",COMPANY);
                intent.putExtra("PLANT", PLANT);
                intent.putExtra("PLANTTXT", PLANTTXT);
                intent.putExtra("WAREHOUSE",WAREHOUSE);
                intent.putExtra("STOCKPLACE", STOCKPLACE);
                startActivity(intent);
            }
        });



        depoStokMik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),depoStokDurum.class);
                intent.putExtra("AYARGRUP",AYARGRUP );
                intent.putExtra("COMPANY",COMPANY);
                intent.putExtra("PLANT", PLANT);
                intent.putExtra("PLANTTXT", PLANTTXT);
                intent.putExtra("WAREHOUSE",WAREHOUSE);
                intent.putExtra("STOCKPLACE", STOCKPLACE);
                startActivity(intent);
                
            }
        });


        btn_exit=findViewById(R.id.exitbtn);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });



    }
}