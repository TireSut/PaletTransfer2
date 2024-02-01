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


class deposayimAdapter extends RecyclerView.Adapter<deposayimAdapter.camv> {
    VeriTabani vt;
    public String COMPANY,PLANT,WAREHOUSE,STOCKPLACE;
    ArrayList<deposayimId> msayimList;
    LayoutInflater layoutInflater;
    Context context;
    private final int CIKIS_MENU = 3;
    private final int HIDE_MENU = 1;

    public deposayimAdapter(ArrayList<deposayimId> msayimList,Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.msayimList = msayimList;
        this.context = context;
        //vt = new VeriTabani(this.context);
    }
    @Override
    public camv onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.deposayimliste, parent, false);
            return new camv(v);
    }

    @Override
    public void onBindViewHolder(camv holder,int position) {
        deposayimId selecturun = msayimList.get(position);
        holder.setData1(selecturun, position);
   }

    @Override
    public int getItemCount() {
        return msayimList.size();
    }

    public class camv extends RecyclerView.ViewHolder  {
        TextView tv_mat,tv_mtext,tv_miktar,tv_pltno;
        LinearLayout sayimlayout;
        public camv(View v) {
            super(v);
            sayimlayout = (LinearLayout) v.findViewById(R.id.SayimUrunLayout);
            tv_mat = (TextView) v.findViewById(R.id.SayimMaterial);
            tv_mtext = (TextView) v.findViewById(R.id.SayimMtext);
            tv_miktar = (TextView) v.findViewById(R.id.SayimMiktar);
            tv_pltno = (TextView) v.findViewById(R.id.SayimPaletNo);
        }

        public void setData1(deposayimId selecturun, int position) {
            tv_mat.setText(selecturun.getMATERIAL());
            tv_pltno.setText(selecturun.getPALETNUM());
            tv_mtext.setText(selecturun.getMTEXT());
            tv_miktar.setText(selecturun.getSKQUANTITY());
          }

    }
 }
