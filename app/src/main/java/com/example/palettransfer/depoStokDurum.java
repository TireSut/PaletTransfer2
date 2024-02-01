package com.example.palettransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class depoStokDurum extends AppCompatActivity {
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE,TARIH;
    Switch devirHesap;
    Calendar stokTarih;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageButton takvimBtn;
    TextView tv_tesisadi,tv_depo,tv_stokteri,tarihTxt,devirBaslik;
    final Context context = this;
    VeriTabani vt =new VeriTabani();
    public ArrayList<stokDurumId> nam = new ArrayList<stokDurumId>();
    public ArrayList<stokDurumId> dp = new ArrayList<>();
    public ArrayList<stokDurumId> devir = new ArrayList<>();
    public stokDurumAdapter adapter ;
    RecyclerView recyclerView;
    Bundle g;
    private static final String TAG = "stokDepoDurum";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    DecimalFormat numf = new DecimalFormat("00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_stok_durum);

        devirBaslik=findViewById(R.id.devirBaslik);
        tv_tesisadi=findViewById(R.id.TESISADITXT);
        tv_depo=findViewById(R.id.DEPOTXT);
        tv_stokteri=findViewById(R.id.STOKYERI);
        tarihTxt = findViewById(R.id.tarihTxt);

        TARIH=sdf.format(Calendar.getInstance().getTime());
        takvimBtn=findViewById(R.id.takvimBtn);
        takvimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        depoStokDurum.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        tarihTxt.setText(TARIH);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd.MM.yyyy: " + day + "." + month + "." + year);

                String date = numf.format(day) + "." + numf.format(month) + "." + year;
                TARIH = date;
                tarihTxt.setText(TARIH);

            }
        };



        g = getIntent().getExtras();
        if (g != null) {
            AYARGRUP = g.getCharSequence("AYARGRUP").toString();
            COMPANY = g.getCharSequence("COMPANY").toString();
            PLANT = g.getCharSequence("PLANT").toString();
            PLANTTXT = g.getCharSequence("PLANTTXT").toString();
            WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
            STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
        }
        if (AYARGRUP.equals("")) {
            return;
        }
        // Tanımlar

        tv_depo.setText(WAREHOUSE);
        tv_stokteri.setText(STOCKPLACE);


        devirHesap=findViewById(R.id.devirHesap);
        devirHesap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(devirHesap.isChecked()){
                    devirBaslik.setText("Devir(Hayır)");
                }else {
                    devirBaslik.setText("Devir(Evet)");
                }
            }
        });

        adapter = new stokDurumAdapter(nam, context);
        recyclerView = (RecyclerView) findViewById(R.id.durumRcyecler );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        nam.clear();
        dataList();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout =findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nam.clear();
                dataList();
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });



    }




    public void dataList (){
        String trhtxt= TARIH.substring(6,10)+TARIH.substring(2,6)+TARIH.substring(0,2);
        if(!devirHesap.isChecked()){
            devirHesap.setChecked(true);
            devirBaslik.setText("Devir(Hayır)");
            devirList(trhtxt);
        }
        nam.clear();
        dp =vt.getDepoStokHareket(COMPANY,PLANT,WAREHOUSE,STOCKPLACE,trhtxt);
        try {
            for (int i = 0; i < dp.size() ; i++) {
                nam.add(new stokDurumId(dp.get(i).getMATERIAL() ,dp.get(i).getMTEXT() ,dp.get(i).getSKUNIT() ,dp.get(i).getDEVIR(),dp.get(i).getGIRIS() ,dp.get(i).getCIKIS(),dp.get(i).getKALAN() ) );
                for (int k = 0; k < devir.size() ; k++) {
                 if (nam.get(i).getMATERIAL().equals(devir.get(k).getMATERIAL())) {
                     nam.get(i).setDEVIR(devir.get(k).getDEVIR());
                     k= devir.size();
                    }
                }
                recyclerView.setAdapter(adapter);
            }
        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }
    }
    public void devirList (String trhtxt){
        devir.clear();
        dp =vt.getDepoDevir(COMPANY,PLANT,WAREHOUSE,STOCKPLACE,trhtxt);
        try {
            for (int i = 0; i < dp.size() ; i++) {
                devir.add(new stokDurumId(dp.get(i).getMATERIAL() ,dp.get(i).getMTEXT() ,dp.get(i).getSKUNIT() ,dp.get(i).getDEVIR(),dp.get(i).getGIRIS() ,dp.get(i).getCIKIS(),dp.get(i).getKALAN() ) );
            }
        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }
    }
}