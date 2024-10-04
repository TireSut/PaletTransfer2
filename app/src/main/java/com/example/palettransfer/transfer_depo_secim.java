package com.example.palettransfer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class transfer_depo_secim extends AppCompatActivity {
    final Context context = this;
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE,ATUR;
    public String TPLANT,TPLANTTXT,TWAREHOUSE,TSTOCKPLACE,YON,TARIH,BILTEKPARAM;
    TextView tv_tesisadi,tv_depo,tv_stokteri;
    ImageButton btn_exit;
    RecyclerView recyclerView;
    public ArrayList<depolist> nam = new ArrayList<>();
    public ArrayList<depolist> dp = new ArrayList<>();
    public depoAdaptor adapter ;
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
            setContentView(R.layout.depo_secim);

        }catch (Exception e) {
            Log.e("dataList",e.toString());
        }


 
        btn_exit=findViewById(R.id.exitbtn);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        g = getIntent().getExtras();
        if (g != null) {
            AYARGRUP = g.getCharSequence("AYARGRUP").toString();
            COMPANY = g.getCharSequence("COMPANY").toString();
            PLANT = g.getCharSequence("PLANT").toString();
            PLANTTXT = g.getCharSequence("PLANTTXT").toString();
            WAREHOUSE = g.getCharSequence("WAREHOUSE").toString();
            STOCKPLACE = g.getCharSequence("STOCKPLACE").toString();
            TARIH = g.getCharSequence("TARIH").toString();
            BILTEKPARAM = g.getCharSequence("BILTEKPARAM").toString();
            ATUR = g.getCharSequence("ATUR").toString();
            TPLANT = PLANT;
            TPLANTTXT=PLANTTXT;
            TWAREHOUSE = WAREHOUSE;
            TSTOCKPLACE = STOCKPLACE;
        }
        if ("".equals(AYARGRUP)) {
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
        adapter = new depoAdaptor(nam, context);
        recyclerView = (RecyclerView) findViewById(R.id.rvDepoListe);
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
        adapter.TPLANTTXT=TPLANTTXT;
        adapter.TPLANT=TPLANT;
        adapter.TWAREHOUSE=TWAREHOUSE;
        adapter.TSTOCKPLACE=TSTOCKPLACE;
        adapter.notifyDataSetChanged();



        // ******************************************************** SOLA KAYDIRMA******************************************************************************************
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.krem));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //adapter.closeMenu();
                adapter.showMenu(viewHolder.getAdapterPosition(), "GIRIS");
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
                      YON="GIRIS";
                       callTransfer(viewHolder.getAdapterPosition());
                   return;
                   }
                }
                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        // **************************************************************************************************************************************************
        // ******************************************************** SAĞA KAYDIRMA******************************************************************************************
        ItemTouchHelper.SimpleCallback touchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.krem));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //adapter.closeMenu();
                adapter.showMenu(viewHolder.getAdapterPosition(), "CIKIS");
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX < 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX > 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }
                int yuzde=0;
                int oran=80;
                int genislik=(int) itemView.getRight();
                int x =(int) dX;
                x=x*100;
                if (x!=0){
                    yuzde=x/genislik;
                    if (yuzde>oran) {
                        YON="CIKIS";
                        callTransfer(viewHolder.getAdapterPosition());
                    }
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(touchHelperCallback1);
        itemTouchHelper1.attachToRecyclerView(recyclerView);

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
    public void callTransfer(int pos){
        Intent intent = new Intent(context.getApplicationContext(),transfer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TPLANT = adapter.mdepoList.get(pos).getPLA();
        TPLANTTXT = adapter.mdepoList.get(pos).getPLATXT();
        TWAREHOUSE = adapter.mdepoList.get(pos).getWH();
        TSTOCKPLACE = adapter.mdepoList.get(pos).getSP();

        intent.putExtra("AYARGRUP",AYARGRUP );
        intent.putExtra("COMPANY",COMPANY);
        intent.putExtra("PLANT", PLANT);
        intent.putExtra("PLANTTXT", PLANTTXT);
        intent.putExtra("WAREHOUSE",WAREHOUSE);
        intent.putExtra("STOCKPLACE", STOCKPLACE);
        intent.putExtra("TPLANT", TPLANT);
        intent.putExtra("TPLANTTXT", TPLANTTXT);
        intent.putExtra("TWAREHOUSE",TWAREHOUSE);
        intent.putExtra("TSTOCKPLACE", TSTOCKPLACE);
        intent.putExtra("YON", YON);
        intent.putExtra("TARIH", TARIH);
        intent.putExtra("BILTEKPARAM", BILTEKPARAM);
        intent.putExtra("ATUR", ATUR);
        context.startActivity(intent);
    }
}