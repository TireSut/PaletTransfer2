package com.example.palettransfer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class stokDurumAdapter extends RecyclerView.Adapter<stokDurumAdapter.camv> {

    ArrayList<stokDurumId> mProductList;
    LayoutInflater layoutInflater;
    Context context;
    Double miktar;


    public stokDurumAdapter(ArrayList<stokDurumId> stokDurum, Context context) {
        this.mProductList = stokDurum;
        this.context = context;

    }


    @Override
    public camv onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.depostokmikcard, parent, false);
        camv holder = new camv(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(camv holder, final int position) {
        stokDurumId selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);
       // DecimalFormat numf = new DecimalFormat("###,###,###.##");
        //String devir= numf.format(selectedProduct.DEVIR);




    }

    @Override
    public int getItemCount() {return mProductList.size();}

    static class camv extends RecyclerView.ViewHolder {
        TextView tv_devir,tv_giris,tv_cikis,tv_kalan,tv_mKodu,tv_mtext,tv_skunit;
        RelativeLayout depoStokMikLayout;

        public camv(View v) {
            super(v);
            depoStokMikLayout =  v.findViewById(R.id.depoStokMikLayout);
            tv_devir =  v.findViewById(R.id.DEVIR);
            tv_giris =  v.findViewById(R.id.GIRIS);
            tv_cikis =  v.findViewById(R.id.CIKIS);
            tv_kalan =  v.findViewById(R.id.KALAN);
            tv_mtext =  v.findViewById(R.id.mtext );
        }

        public void setData(stokDurumId selectedProduct, int position)
        {
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
            otherSymbols.setDecimalSeparator(',');
            otherSymbols.setGroupingSeparator('.');
            DecimalFormat numf = new DecimalFormat("###,###,###.##",otherSymbols);
            Double miktar=Double.parseDouble(selectedProduct.getDEVIR());
            String miktxt=numf.format(miktar);
            this.tv_devir.setText(miktxt);

            miktar=Double.parseDouble(selectedProduct.getGIRIS());
            miktxt=numf.format(miktar);
            this.tv_giris.setText(miktxt);

            miktar=Double.parseDouble(selectedProduct.getCIKIS());
            miktxt=numf.format(miktar);
            this.tv_cikis.setText(miktxt);

            miktar=Double.parseDouble(selectedProduct.getKALAN());
            miktxt=numf.format(miktar);
            this.tv_kalan.setText(miktxt);

            this.tv_mtext.setText(selectedProduct.getMTEXT());
        }


    }
}
