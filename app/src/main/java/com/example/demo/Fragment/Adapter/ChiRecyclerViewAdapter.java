package com.example.demo.Fragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Fragment.Tab.Chi;
import com.example.demo.Model.ChiTieu;
import com.example.demo.R;

import java.text.NumberFormat;
import java.util.List;

public class ChiRecyclerViewAdapter extends RecyclerView.Adapter<ChiRecyclerViewAdapter.MyViewHolder> {
    private Chi context;
    private List<ChiTieu> list;
    private SQLitedemoOpenHelper sqLitedemoOpenHelper;

    public List<ChiTieu> getList() {
        return list;
    }

    public void setList(List<ChiTieu> list) {
        this.list = list;
    }

    public ChiRecyclerViewAdapter(Chi context, List<ChiTieu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        v = LayoutInflater.from(context.getContext()).inflate(R.layout.recycler_card,parent,false);
        ChiRecyclerViewAdapter.MyViewHolder myViewHolder = new ChiRecyclerViewAdapter.MyViewHolder(v);
        sqLitedemoOpenHelper = new SQLitedemoOpenHelper(context.getContext());
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChiRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.imgList.setImageResource(list.get(position).getSrcimg());
        holder.loai.setText(list.get(position).getLoai());
        holder.ghiChu.setText(list.get(position).getGhiChu());
        holder.thoiGian.setText(list.get(position).getTgian());
        double num = list.get(position).getTien();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(num);
        holder.tien.setText(moneyString);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTieu chiTieu = list.get(position);
                context.updateChi(chiTieu);
                notifyDataSetChanged();
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTieu chiTieu = list.get(position);
                list.remove(position);
                sqLitedemoOpenHelper.deleteChiTieu(chiTieu.getId());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView loai, ghiChu, thoiGian, tien;
        private ImageButton btEdit,btDelete;
        private ImageView imgList;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            loai = itemView.findViewById(R.id.loai);
            ghiChu = itemView.findViewById(R.id.ghiChu);
            thoiGian = itemView.findViewById(R.id.thoiGian);
            tien = itemView.findViewById(R.id.tien);
            imgList = itemView.findViewById(R.id.imgList);
            btEdit = itemView.findViewById(R.id.btEdit);
            btDelete = itemView.findViewById(R.id.btDelete);
        }
    }
}
