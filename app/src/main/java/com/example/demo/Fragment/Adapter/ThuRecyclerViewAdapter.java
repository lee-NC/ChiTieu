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
import com.example.demo.Fragment.Tab.Thu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;

import java.text.NumberFormat;
import java.util.List;

public class ThuRecyclerViewAdapter extends RecyclerView.Adapter<ThuRecyclerViewAdapter.MyViewHolder> {
    private Thu context;
    private List<ThuNhap> list;
    private SQLitedemoOpenHelper sqLite;

    public List<ThuNhap> getList() {
        return list;
    }

    public void setList(List<ThuNhap> list) {
        this.list = list;
    }

    public ThuRecyclerViewAdapter(Thu context, List<ThuNhap> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        v = LayoutInflater.from(context.getContext()).inflate(R.layout.recycler_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        sqLite = new SQLitedemoOpenHelper(context.getContext());
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThuRecyclerViewAdapter.MyViewHolder holder, int position) {
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
                ThuNhap thuNhap = list.get(position);
                context.updateThu(thuNhap);
                notifyDataSetChanged();
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuNhap thuNhap = list.get(position);
                list.remove(position);
                sqLite.deleteThuNhap(thuNhap.getId());
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
