package com.example.palettransfer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class urunAdaptor extends RecyclerView.Adapter<urunAdaptor.camv> {

    ArrayList<urunlist> murunList;
    LayoutInflater layoutInflater;
    Context context;
    private urunAdaptor.camv holder;

    public urunAdaptor(ArrayList<urunlist> murunList,Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.murunList = murunList;
        this.context = context;

    }
    @Override
    public camv onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.urun_view, parent, false);
        camv holder = new camv(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(camv holder,int position) {
        urunlist selectedurun = murunList.get(position);
        holder.setData(selectedurun, position);
    }

    @Override
    public int getItemCount() {
        return murunList.size();
    }
    public class camv extends RecyclerView.ViewHolder  {
        TextView ITEMNUM,MATERIAL,MTEXT, SKQUANTITY, BOLUNEN,KALAN,HATA,HATATXT;
        LinearLayout urunlayout;

        public camv(View v) {
            super(v);
            urunlayout = (LinearLayout) v.findViewById(R.id.urunLayout);
            MATERIAL = (TextView) v.findViewById(R.id.material);
            MTEXT = (TextView) v.findViewById(R.id.mtext);
            SKQUANTITY = (TextView) v.findViewById(R.id.skquantitiy);
        }

        public void setData(urunlist selectedurun, int position) {
            this.MATERIAL.setText(selectedurun.getMATERIAL());
            this.MTEXT.setText(selectedurun.getMTEXT());
            this.SKQUANTITY.setText(selectedurun.getSKQUANTITY());
        }

    }

}
