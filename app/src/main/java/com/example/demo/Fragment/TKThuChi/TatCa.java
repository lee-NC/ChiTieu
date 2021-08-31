package com.example.demo.Fragment.TKThuChi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;

import java.text.NumberFormat;
import java.util.List;

public class TatCa extends Fragment {

    private View v;
    private TextView tt,tc,cl;
    private SQLitedemoOpenHelper sqLiteThuOpenHelper;
    private SQLitedemoOpenHelper sqLiteChiOpenHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.tat_ca,container,false);
        sqLiteChiOpenHelper = new SQLitedemoOpenHelper(getContext());
        sqLiteThuOpenHelper = new SQLitedemoOpenHelper(getContext());
        List<ChiTieu> chiTieuList = sqLiteChiOpenHelper.getChiTieuAll();
        List<ThuNhap> thuNhapList = sqLiteThuOpenHelper.getThuNhapAll();
        tt = v.findViewById(R.id.tongThu);
        tc = v.findViewById(R.id.tongChi);
        cl = v.findViewById(R.id.conLai);
        //tinh thu chi
        Double tongThu = 0.0 ;
        String tient = "0 VNĐ";
        for(ThuNhap thuNhap: thuNhapList){
            Double t = thuNhap.getTien();
            tongThu = tongThu +t;
        }
        tient = tongThu.toString();
        tient = showTien(tient);
        tt.setText(tient);
        Double tongChi = 0.0;
        for(ChiTieu chiTieu: chiTieuList){
            Double t = chiTieu.getTien();
            tongChi = tongChi +t;
        }

        tient = tongChi.toString();
        tient = showTien(tient);
        tc.setText(tient);
        Double conLai = tongThu - tongChi;
        tient = conLai.toString();
        tient = showTien(tient);
        cl.setText(tient);
        return v;
    }

    private String showTien(String tient){
        double num = Double.parseDouble(tient);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(num);
        tient = moneyString;
        return tient;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
