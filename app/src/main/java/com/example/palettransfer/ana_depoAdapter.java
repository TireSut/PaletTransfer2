package com.example.palettransfer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ana_depoAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public String AYARGRUP, COMPANY, PLANT, PLANTTXT, WAREHOUSE, STOCKPLACE,TPLANT, TPLANTTXT, TWAREHOUSE, TSTOCKPLACE;
    ArrayList<depolist> mAnadepoList;
    LayoutInflater layoutInflater;
    Context context;
    VeriTabani vt;

    public ana_depoAdapter(ArrayList<depolist> mAnadepoList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mAnadepoList = mAnadepoList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.depo_secim_depo, parent, false);
        return new ana_depoAdapter.camv(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        depolist selecteddepo = mAnadepoList.get(position);
        // ******************************* depo menüsü ******************************************
            ana_depoAdapter.camv holdlist = (ana_depoAdapter.camv) holder;
            holdlist.setData(selecteddepo, position);
        holdlist.anadepobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TPLANT = mAnadepoList.get(position).getPLA();
                TPLANTTXT =mAnadepoList.get(position).getPLATXT();
                TWAREHOUSE = mAnadepoList.get(position).getWH();
                TSTOCKPLACE =mAnadepoList.get(position).getSP();
                vt = new VeriTabani();
                vt.setAnaDepo(AYARGRUP,PLANT,TPLANT,WAREHOUSE,TWAREHOUSE,STOCKPLACE,TSTOCKPLACE );
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {
        return mAnadepoList.size();
    }

    public class camv extends RecyclerView.ViewHolder  {
        TextView tv_tesis,tv_depo;
        LinearLayout depolayout;
        ImageButton anadepobtn;
        public camv(View v) {
            super(v);
            depolayout = (LinearLayout) v.findViewById(R.id.depolayout);
            tv_tesis = (TextView) v.findViewById(R.id.palettesis);
            tv_depo = (TextView) v.findViewById(R.id.paletdepo);
            anadepobtn = (ImageButton) v.findViewById(R.id.deporesim);
        }

        public void setData(depolist selecteddepo, int position) {
            String deg="";
            deg=selecteddepo.getPLA()+"-"+selecteddepo.getPLATXT();
            this.tv_tesis.setText(deg);
            deg="Depo:["+selecteddepo.getWH()+"] ["+selecteddepo.getSP()+"-"+selecteddepo.getSPTXT()+"]";
            this.tv_depo.setText(deg);
            // stok yeri adına göre resim seçim
            int arac=deg.indexOf("ARAC");
            if (arac>=0){
                this.anadepobtn.setBackgroundResource(R.drawable.ana_aracdepo);
            }
            int iade=deg.indexOf("İADE");
            if (iade>=0){
                this.anadepobtn.setBackgroundResource(R.drawable.iadedepo);
            }
            int urtim=deg.indexOf("URET");
            if (urtim>=0){
                this.anadepobtn.setBackgroundResource(R.drawable.factory);
            }
            int sevk=deg.indexOf("SEVK");
            if (sevk>=0){
                this.anadepobtn.setBackgroundResource(R.drawable.sevkdepo);
            }
        }

    }
}


