package com.example.palettransfer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class deposayim extends AppCompatActivity {
    public String COMPANY,PLANT,WAREHOUSE,STOCKPLACE,TARIH,ATUR;
    String barkodTxt;
    final Context context = this;
    EditText edt_miktar,edt_barkod;
    TextView lbl_miktar,tv_bilgiler,tarih;
    ImageButton btn_tara,btn_ara,btn_info,btn_exit;
    Switch sw_tursec,sayimsec;
    Bundle g;
    VeriTabani vt =new VeriTabani();
    public ArrayList<deposayimId> nam = new ArrayList<>();
    public ArrayList<deposayimId> dp = new ArrayList<>();
    public deposayimAdapter adapter ;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposayim);

        edt_miktar = findViewById(R.id.miktarbox);
        lbl_miktar = findViewById(R.id.miktarlbl);
        tv_bilgiler = findViewById(R.id.STOKYERI);
        tarih = findViewById(R.id.tarih);
        edt_barkod = findViewById(R.id.paletBarkod);
        edt_barkod.setInputType(InputType.TYPE_CLASS_NUMBER);
        btn_ara = findViewById(R.id.paletAraBtn);
        btn_tara = findViewById(R.id.paletTaramaBtn);
        sw_tursec = (Switch) findViewById(R.id.tursec);
        g = getIntent().getExtras();
        if (g != null) {
            COMPANY = g.getCharSequence("COMPANY").toString();
            PLANT = g.getCharSequence("PLANT").toString();
            WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
            STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
            TARIH = g.getCharSequence("TARIH").toString();
            ATUR = g.getCharSequence("ATUR").toString();
            tarih.setText(TARIH);
            tv_bilgiler.setText(COMPANY+"-"+PLANT+"-"+WAREHOUSE+"-"+STOCKPLACE);
        }
        if (ATUR.equals("true")){
            sw_tursec.setChecked(true);
            sw_tursec.setText(sw_tursec.getTextOn());
            lbl_miktar.setVisibility(View.VISIBLE);
            edt_miktar.setVisibility(View.VISIBLE);
        } else {
            sw_tursec.setText(sw_tursec.getTextOff());
            lbl_miktar.setVisibility(View.INVISIBLE);
            edt_miktar.setVisibility(View.INVISIBLE);
        }

        sw_tursec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw_tursec.isChecked()) {

                    sw_tursec.setText(sw_tursec.getTextOn());
                    lbl_miktar.setVisibility(View.VISIBLE);
                    edt_miktar.setVisibility(View.VISIBLE);
                } else {

                    sw_tursec.setText(sw_tursec.getTextOff());
                    lbl_miktar.setVisibility(View.INVISIBLE);
                    edt_miktar.setVisibility(View.INVISIBLE);
                }
            }
        });


        sayimsec = (Switch) findViewById(R.id.sayimsec);
        sayimsec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sayimsec.isChecked()) {

                    sayimsec.setText(sayimsec.getTextOn());
                    lbl_miktar.setVisibility(View.VISIBLE);
                    edt_miktar.setVisibility(View.VISIBLE);
                } else {

                    sayimsec.setText(sayimsec.getTextOff());
                    lbl_miktar.setVisibility(View.INVISIBLE);
                    edt_miktar.setVisibility(View.INVISIBLE);
                }
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
        //   edt_barkod.setText("2109270101");
        btn_tara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(deposayim.this);
                scanIntegrator.initiateScan();

            }
        });
        btn_ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paletbul();
                edt_barkod.setText("");
                edt_miktar.setText("");
            }
        });
        edt_barkod.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    paletbul();
                }
                return false;
            }
        });

        adapter = new deposayimAdapter(nam, context);
        recyclerView = (RecyclerView) findViewById(R.id.rvSayimListe );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        nam.clear();
        dataList();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // ******************************************************** SOLA KAYDIRMA******************************************************************************************
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.krem));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }
                int yuzde=0;
                int oran=60;
                int genislik=(int) itemView.getRight();
                int x =(int) dX;
                x=(x*-1)*100;
                if (x!=0){
                    yuzde=x/genislik;
                    if (yuzde>oran) {
                        satirsil(viewHolder.getAdapterPosition());
                        return;
                    }
                }
                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // **************************************************************************************************************************************************
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                barkodTxt = intent.getStringExtra("SCAN_RESULT");
                edt_barkod.setText(barkodTxt);
                paletbul();
                edt_barkod.setText("");
                edt_miktar.setText("");
            }
        } catch (Exception e) {

        }
    }



    public void dataList (){
        String trhtxt= TARIH.substring(6,10)+TARIH.substring(2,6)+TARIH.substring(0,2);
        nam.clear();
        dp =vt.getDepoSayim(COMPANY,PLANT,WAREHOUSE,STOCKPLACE,trhtxt);
        try {
            for (int i = 0; i < dp.size() ; i++) {
                nam.add(new deposayimId(dp.get(i).getPALETNUM(),dp.get(i).getMATERIAL(),dp.get(i).getMTEXT(),dp.get(i).getSKUNIT(),dp.get(i).getSKQUANTITY()) );
                recyclerView.setAdapter(adapter);
            }
        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }
    }
    protected void paletbul() {
        String trhtxt= TARIH.substring(6,10)+TARIH.substring(2,6)+TARIH.substring(0,2);
        // getUrunListe (String barkod, String comp,String pla,String wh, String sp,Boolean tur,int miktar)
        barkodTxt = edt_barkod.getText().toString();
        if (barkodTxt.equals("")) {
            return;
        }
        double mik = Double.parseDouble("0" + edt_miktar.getText().toString());
        if (sw_tursec.isChecked() && mik <= 0.0) {
            hata_dlg("Palet / Ürün Bulma Hatası", "Miktar (0)Sıfır olamaz.",true);
            return;
        }
        nam.clear();
        String hatatxt=vt.getSayimUrunBul (barkodTxt, COMPANY,PLANT,WAREHOUSE, STOCKPLACE,trhtxt,edt_miktar.getText().toString() ,sw_tursec.isChecked(),sayimsec.isChecked() ,0);
          if (!hatatxt.equals("")){
              hata_dlg("Sayim",hatatxt,true);
        }
          dataList();
        adapter.notifyDataSetChanged();
        return;
    }
    public void satirsil(int pos){
        String trhtxt= TARIH.substring(6,10)+TARIH.substring(2,6)+TARIH.substring(0,2);
        String hatatxt = vt.setSayim(COMPANY,PLANT,WAREHOUSE,STOCKPLACE, trhtxt,adapter.msayimList.get(pos).getPALETNUM(),adapter.msayimList.get(pos).getMATERIAL(),adapter.msayimList.get(pos).getMTEXT(),"","0",Boolean.FALSE,Boolean.FALSE,1);
        if (!hatatxt.equals("")){
            hata_dlg("Sayim",hatatxt,true);
        }
        return;
    }
    protected void  hata_dlg(String baslik,String hatatxt,boolean ishata){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(baslik);
        builder.setMessage(hatatxt);

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edt_miktar.setText("");
                edt_barkod.setText("");
                nam.clear();
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = builder.create();
// set the gravity
        alertDialog.getWindow().setGravity(Gravity.TOP);
// set the margin
        alertDialog.getWindow().getAttributes().verticalMargin = 0.1F;
        if (ishata) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
        }else {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_light);

        }
        alertDialog.show();
    }
}