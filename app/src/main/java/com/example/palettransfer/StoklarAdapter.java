package com.example.palettransfer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class StoklarAdapter extends RecyclerView.Adapter<StoklarAdapter.ViewHolder> {

    private List<StokModel> stokList;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public StoklarAdapter(List<StokModel> stokList) {
        this.stokList = stokList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stok, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StokModel model = stokList.get(position);
        holder.tvMaterial.setText("Kod: " + model.getMaterial());
        holder.tvStext.setText(model.getStext());
        holder.tvStok.setText(df.format(model.getStok()));
    }

    @Override
    public int getItemCount() {
        return stokList != null ? stokList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaterial, tvStext, tvStok;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaterial = itemView.findViewById(R.id.tv_material);
            tvStext = itemView.findViewById(R.id.tv_stext);
            tvStok = itemView.findViewById(R.id.tv_stok);
        }
    }
}
