package com.example.palettransfer;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ana_depo_secim extends AppCompatActivity {
    final Context context = this;
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE;
    public String TPLANT,TPLANTTXT,TWAREHOUSE,TSTOCKPLACE,YON;
    TextView tv_tesisadi,tv_depo,tv_stokteri;
    ImageButton btn_exit;
    RecyclerView recyclerView;
    public ArrayList<depolist> nam = new ArrayList<>();
    public ArrayList<depolist> dp = new ArrayList<>();
    public ana_depoAdapter adapter ;
    Bundle g;
    VeriTabani vt =new VeriTabani();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ana_depo);

        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }
  /*
        btn_exit=findViewById(R.id.exitbtn);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
*/
        adapter = new ana_depoAdapter(nam, context);
        g = getIntent().getExtras();
        if (g != null) {
            AYARGRUP = g.getCharSequence("AYARGRUP").toString();
            COMPANY = g.getCharSequence("COMPANY").toString();
            PLANT = g.getCharSequence("PLANT").toString();
            PLANTTXT = g.getCharSequence("PLANTTXT").toString();
            WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
            STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
            TPLANT = PLANT;
            TPLANTTXT=PLANTTXT;
            TWAREHOUSE = WAREHOUSE;
            TSTOCKPLACE = STOCKPLACE;
        }
     if (   "".equals(AYARGRUP)) {
        return;
    }
        // Tanımlar

        tv_tesisadi=findViewById(R.id.TESISADITXT);
        tv_depo=findViewById(R.id.DEPOTXT);
        tv_stokteri=findViewById(R.id.STOKYERI);

        tv_tesisadi.setText(PLANT+"-"+PLANTTXT);
        tv_depo.setText("Depo: ["+WAREHOUSE+"]");
        tv_stokteri.setText("Stok Yeri: ["+STOCKPLACE+"]");
        // RecyclerView tanımları
        recyclerView = (RecyclerView) findViewById(R.id.anaDepoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataList();
        recyclerView.setAdapter(adapter);
        adapter.COMPANY=COMPANY;
        adapter.AYARGRUP=AYARGRUP;
        adapter.PLANTTXT=PLANTTXT;
        adapter.PLANT=PLANT;
        adapter.WAREHOUSE=WAREHOUSE;
        adapter.STOCKPLACE=STOCKPLACE;

        adapter.notifyDataSetChanged();
    }
    public void dataList (){
        nam.clear();
        dp =vt.getDepoListe(AYARGRUP, COMPANY, STOCKPLACE);
        try {
            for (int i = 0; i < dp.size() ; i++) {
                nam.add(new depolist(dp.get(i).getPLA(),dp.get(i).getPLATXT(),dp.get(i).getWH(),dp.get(i).getWHTXT(),dp.get(i).getSP(),dp.get(i).getYON(),dp.get(i).getSPTXT()));
                recyclerView.setAdapter(adapter);
            }
        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }
    }
}