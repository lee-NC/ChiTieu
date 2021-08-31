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

public class Nam extends Fragment {
    private TextView ttn, tcn, cln;
    private Spinner spNamN;
    private View v;
    private SQLitedemoOpenHelper sqLiteThuOpenHelper;
    private SQLitedemoOpenHelper sqLiteChiOpenHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.nam,container,false);
        //khai bao cac truong can su dung

        //sqlLite
        sqLiteThuOpenHelper = new SQLitedemoOpenHelper(getContext());
        sqLiteChiOpenHelper = new SQLitedemoOpenHelper(getContext());

        //textview hien thi
        ttn = v.findViewById(R.id.tongThuN);
        tcn = v.findViewById(R.id.tongChiN);
        cln = v.findViewById(R.id.conLaiN);

        //spinner chon thang nam
        spNamN = v.findViewById(R.id.spNamN);

        //set cac gia tri mac dinh trong thang nam
        Calendar calendar = Calendar.getInstance();
        int nam = calendar.get(Calendar.YEAR);
        nam = nam - 2014;
        spNamN.setSelection(nam);

        spNamN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //lay thong tin theo thang nam da chon
                String namTK = spNamN.getSelectedItem().toString();

                List<ThuNhap> thuNhapList = sqLiteThuOpenHelper.getThuNhapNam(namTK);
                Double tongThu = 0.0;
                String tient = "0 VNĐ";
                for(ThuNhap thuNhap: thuNhapList){
                    Double t = thuNhap.getTien();
                    tongThu = tongThu +t;
                }
                tient = tongThu.toString();
                tient = showTien(tient);
                ttn.setText(tient);
                List<ChiTieu> chiTieuList = sqLiteChiOpenHelper.getChiTieuNam(namTK);
                Double tongChi = 0.0;
                for(ChiTieu chiTieu: chiTieuList){
                    Double t = chiTieu.getTien();
                    tongChi = tongChi +t;
                }

                tient = tongChi.toString();
                tient = showTien(tient);
                tcn.setText(tient);
                Double conlai = tongThu - tongChi;
                tient = conlai.toString();
                tient = showTien(tient);
                cln.setText(tient);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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