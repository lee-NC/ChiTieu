package com.example.demo.Fragment.TKThuChi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

public class Thang extends Fragment {

    private TextView ttt, tct, clt;
    private View v;
    private Spinner spThangT, spNamT;
    private SQLitedemoOpenHelper sqLiteThuOpenHelper;
    private SQLitedemoOpenHelper sqLiteChiOpenHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.thang,container,false);
        //khai bao cac truong can su dung

        //sqlLite
        sqLiteThuOpenHelper = new SQLitedemoOpenHelper(getContext());
        sqLiteChiOpenHelper = new SQLitedemoOpenHelper(getContext());

        //textview hien thi
        tct = v.findViewById(R.id.tongChiT);
        ttt = v.findViewById(R.id.tongThuT);
        clt = v.findViewById(R.id.conLaiT);

        //spinner chon thang nam
        spNamT = v.findViewById(R.id.spNamT);
        spThangT = v.findViewById(R.id.spThangT);

        //set cac gia tri mac dinh trong thang nam
        Calendar calendar = Calendar.getInstance();
        int thang = calendar.get(Calendar.MONTH);
        spThangT.setSelection(thang);
        int nam = calendar.get(Calendar.YEAR);
        nam = nam - 2014;
        spNamT.setSelection(nam);

        spNamT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionTK();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spThangT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionTK();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    private void actionTK(){
        //lay thong tin theo thang nam da chon
        String thangTK = spThangT.getSelectedItem().toString();
        String namTK = spNamT.getSelectedItem().toString();
        if(thangTK.length()<=1) thangTK = "0"+thangTK;
        List<ThuNhap> thuNhapList = sqLiteThuOpenHelper.getThuNhapThang(thangTK,namTK);
        Double tongThu = 0.0;
        String tient = "0 VNĐ";
        for(ThuNhap thuNhap: thuNhapList){
            Double t = thuNhap.getTien();
            tongThu = tongThu +t;
        }
        tient = tongThu.toString();
        tient = showTien(tient);
        ttt.setText(tient);

        List<ChiTieu> chiTieuList = sqLiteChiOpenHelper.getChiTieuThang(thangTK,namTK);
        Double tongChi = 0.0;
        for(ChiTieu chiTieu: chiTieuList){
            Double t = chiTieu.getTien();
            tongChi = tongChi +t;
        }

        tient = tongChi.toString();
        tient = showTien(tient);
        tct.setText(tient);
        Double conlai = tongThu - tongChi;
        tient = conlai.toString();
        tient = showTien(tient);
        clt.setText(tient);
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