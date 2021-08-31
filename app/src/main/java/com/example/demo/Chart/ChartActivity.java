package com.example.demo.Chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.demo.Chart.ChartFragment.BarChartMonthFragment;
import com.example.demo.Chart.ChartFragment.BarChartYearFragment;
import com.example.demo.Chart.ChartFragment.PieChartFragment;
import com.example.demo.Chart.ChonThoiGian.ThoiGianNam;
import com.example.demo.Chart.ChonThoiGian.ThoiGianThang;
import com.example.demo.Chart.ChonThoiGian.ThoiGianTuyChon;
import com.example.demo.R;

public class ChartActivity extends AppCompatActivity implements OpenChartInterface {
    private RadioButton btTGTC, btTGT, btTGN;
    private FrameLayout frameLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        frameLayout = findViewById(R.id.chart);
        toolbar = findViewById(R.id.toolbarChart);
        btTGTC = findViewById(R.id.btthoiGianTuyChon);
        btTGT = findViewById(R.id.btthoiGianThang);
        btTGN = findViewById(R.id.btthoiGianNam);

        //set toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set cac nut
        btTGTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThoiGianTuyChon thoiGianTuyChon = new ThoiGianTuyChon();
                thoiGianTuyChon.show(getSupportFragmentManager(),"Chọn khoảng thời gian");
            }
        });
        btTGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThoiGianThang thoiGianThang = new ThoiGianThang();
                thoiGianThang.show(getSupportFragmentManager(),"Chọn khoảng thời gian");
            }
        });
        btTGN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThoiGianNam thoiGianNam = new ThoiGianNam();
                thoiGianNam.show(getSupportFragmentManager(),"Chọn khoảng thời gian");
            }
        });
    }

    @Override
    public void openPieChart(String tu, String den) {
        PieChartFragment pieChart = new PieChartFragment(tu,den);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.chart,pieChart);
        transaction.commit();
    }

    @Override
    public void openBarChart(String thang, String nam) {
        BarChartMonthFragment barMonthChart = new BarChartMonthFragment(thang, nam);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.chart,barMonthChart);
        transaction.commit();
    }

    @Override
    public void openBarYChart(String nam) {
        BarChartYearFragment barYearChart = new BarChartYearFragment(nam);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.chart,barYearChart);
        transaction.commit();
    }
}
