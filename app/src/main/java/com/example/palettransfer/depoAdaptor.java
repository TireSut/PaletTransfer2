package com.example.palettransfer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class depoAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    VeriTabani vt;
    public String AYARGRUP,COMPANY,PLANT,PLANTTXT,WAREHOUSE,STOCKPLACE;
    public String TPLANT,TPLANTTXT,TWAREHOUSE,TSTOCKPLACE,YON;
    ArrayList<depolist> mdepoList;
    LayoutInflater layoutInflater;
    Context context;
    private final int GIRIS_MENU = 2;
    private final int CIKIS_MENU = 3;
    private final int HIDE_MENU = 1;

    public depoAdaptor(ArrayList<depolist> mdepoList,Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mdepoList = mdepoList;
        this.context = context;
        //vt = new VeriTabani(this.context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType==GIRIS_MENU){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.depo_secim_giris, parent, false);
            return new girv(v);
        }else if(viewType==CIKIS_MENU){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.depo_secim_cikis, parent, false);
            return new cikv(v);
        }else{
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.depo_secim_depo, parent, false);
            return new camv(v);
        }
    }
    public int getItemViewType(int position) {
        if(mdepoList.get(position).getYON().equals("CIKIS")) {
            return CIKIS_MENU;
        }else if (mdepoList.get(position).getYON().equals("GIRIS")){
            return GIRIS_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        depolist selecteddepo = mdepoList.get(position);
        // ******************************* depo menüsü ******************************************
        if ( holder instanceof camv){
            camv holdlist = (camv) holder;
            holdlist.setData1(selecteddepo, position);
        }
        // ******************************* depo aç menüsü sağ ******************************************
        if ( holder instanceof cikv){
            cikv holdcikis = (cikv) holder;
            holdcikis.setData3(selecteddepo, position);
            int finalPosition = position;
            holdcikis.btn_paletcikis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(),transfer.class);
                    TPLANT = mdepoList.get(finalPosition).getPLA();
                    TPLANTTXT =mdepoList.get(finalPosition).getPLATXT();
                    TWAREHOUSE = mdepoList.get(finalPosition).getWH();
                    TSTOCKPLACE =mdepoList.get(finalPosition).getSP();
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
                    intent.putExtra("YON", "CIKIS");
                    context.startActivity(intent);
                    // notifyDataSetChanged();
                }
            });
        }
        // ******************************* depo sil menüsü sol ******************************************
        if ( holder instanceof girv){
            girv holdgiris = (girv) holder;
            holdgiris.setData2(selecteddepo, position);
            int finalPosition = position;
            holdgiris.btn_paletgiris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(),transfer.class);
                    TPLANT = mdepoList.get(finalPosition).getPLA();
                    TPLANTTXT =mdepoList.get(finalPosition).getPLATXT();
                    TWAREHOUSE = mdepoList.get(finalPosition).getWH();
                    TSTOCKPLACE =mdepoList.get(finalPosition).getSP();
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
                    intent.putExtra("YON", "GIRIS");
                    context.startActivity(intent);
                    // notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mdepoList.size();
    }
    public class camv extends RecyclerView.ViewHolder  {
        TextView tv_tesis,tv_depo;
        LinearLayout depolayout;
        ImageButton deporesim;
        public camv(View v) {
            super(v);
            depolayout = (LinearLayout) v.findViewById(R.id.depolayout);
            tv_tesis = (TextView) v.findViewById(R.id.palettesis);
            tv_depo = (TextView) v.findViewById(R.id.paletdepo);
            deporesim = (ImageButton) v.findViewById(R.id.deporesim);
        }

        public void setData1(depolist selecteddepo, int position) {
            String deg="";
            deg=selecteddepo.getPLA()+"-"+selecteddepo.getPLATXT();
            this.tv_tesis.setText(deg);
            deg="Depo:["+selecteddepo.getWH()+"]  Stok Yeri:["+selecteddepo.getSP()+"]";
            this.tv_depo.setText(deg);
            deg=selecteddepo.getSP();
            // stok yeri adına göre resim seçim
            int arac=deg.indexOf("ARAC");
            if (arac>=0){
                this.deporesim.setBackgroundResource(R.drawable.aracdepo);
            }
            int iade=deg.indexOf("İADE");
            if (iade>=0){
                this.deporesim.setBackgroundResource(R.drawable.iadedepo);
            }
            int urtim=deg.indexOf("URET");
            if (urtim>=0){
                this.deporesim.setBackgroundResource(R.drawable.factory);
            }
            int sevk=deg.indexOf("SEVK");
            if (sevk>=0){
                this.deporesim.setBackgroundResource(R.drawable.sevkdepo);
            }
        }

    }
    public class girv extends RecyclerView.ViewHolder  {
        TextView tv_gtesis,tv_gdepo;
        LinearLayout girislayout;
        ImageButton btn_paletgiris,deporesim;
        public girv(View v) {
            super(v);
            girislayout = (LinearLayout) v.findViewById(R.id.depogirislayout);
            tv_gtesis = (TextView) v.findViewById(R.id.giristesis);
            tv_gdepo = (TextView) v.findViewById(R.id.girisdepo);
            btn_paletgiris = (ImageButton) v.findViewById(R.id.transfergirisbtn);
            deporesim = (ImageButton) v.findViewById(R.id.deporesim);
        }

        public void setData2(depolist selecteddepo, int position) {
            String deg="";
            deg=selecteddepo.getPLA()+"-"+selecteddepo.getPLATXT();
            this.tv_gtesis.setText(deg);
            deg="Depo:["+selecteddepo.getWH()+"]  Stok Yeri:["+selecteddepo.getSP()+"]";
            this.tv_gdepo.setText(deg);
            // stok yeri adına göre resim seçim
            int arac=deg.indexOf("ARAC");
            if (arac>=0){
                this.deporesim.setBackgroundResource(R.drawable.aracdepo);
            }
            int iade=deg.indexOf("İADE");
            if (iade>=0){
                this.deporesim.setBackgroundResource(R.drawable.iadedepo);
            }
            int urtim=deg.indexOf("URET");
            if (urtim>=0){
                this.deporesim.setBackgroundResource(R.drawable.factory);
            }
            int sevk=deg.indexOf("SEVK");
            if (sevk>=0){
                this.deporesim.setBackgroundResource(R.drawable.sevkdepo);
            }
        }

    }
    public class cikv extends RecyclerView.ViewHolder  {
        TextView tv_ctesis,tv_cdepo;
        LinearLayout cikislayout;
        ImageButton btn_paletcikis,deporesim;
        public cikv(View v) {
            super(v);
            cikislayout = (LinearLayout) v.findViewById(R.id.depocikislayout);
            tv_ctesis = (TextView) v.findViewById(R.id.cikistesis);
            tv_cdepo = (TextView) v.findViewById(R.id.cikisdepo);
            btn_paletcikis = (ImageButton) v.findViewById(R.id.transfercikisbtn);
            deporesim = (ImageButton) v.findViewById(R.id.deporesim);
        }

        public void setData3(depolist selecteddepo, int position) {
            String deg="";
            deg=selecteddepo.getPLA()+"-"+selecteddepo.getPLATXT();
            this.tv_ctesis.setText(deg);
            deg="Depo:["+selecteddepo.getWH()+"]  Stok Yeri:["+selecteddepo.getSP()+"]";
            this.tv_cdepo.setText(deg);
            // stok yeri adına göre resim seçim
            int arac=deg.indexOf("ARAC");
            if (arac>=0){
                this.deporesim.setBackgroundResource(R.drawable.aracdepo);
            }
            int iade=deg.indexOf("İADE");
            if (iade>=0){
                this.deporesim.setBackgroundResource(R.drawable.iadedepo);
            }
            int urtim=deg.indexOf("URET");
            if (urtim>=0){
                this.deporesim.setBackgroundResource(R.drawable.factory);
            }
            int sevk=deg.indexOf("SEVK");
            if (sevk>=0){
                this.deporesim.setBackgroundResource(R.drawable.sevkdepo);
            }
        }

    }
    public void showMenu(int position,String islem) {

        for(int i=0; i<mdepoList.size(); i++){
            mdepoList.get(i).setYON("");
        }
        mdepoList.get(position).setYON(islem);
        notifyDataSetChanged();
    }

    public void closeMenu() {
        for(int i=0; i<mdepoList.size(); i++){
            mdepoList.get(i).setYON("");
        }
        notifyDataSetChanged();
    }
    public void silalert(String msg,String OKbtn,String CANbtn,Boolean iscancel,String Smid,int pos){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(iscancel);
        builder1.setPositiveButton(OKbtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //put your code that needed to be executed when okay is clicked
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(CANbtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
