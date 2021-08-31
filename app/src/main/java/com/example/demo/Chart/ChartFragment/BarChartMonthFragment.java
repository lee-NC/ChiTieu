package com.example.demo.Chart.ChartFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BarChartMonthFragment extends Fragment {

    private BarChart barChart;
    private View v;
    private String thang = "";
    private String nam = "";
    private SQLitedemoOpenHelper sqLitedemoOpenHelper;

    public BarChartMonthFragment(String thang, String nam) {
        // Required empty public constructor
        if (thang.length() <= 1) thang = "0" + thang;
        this.thang = thang;
        this.nam = nam;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_bar_chart_month, container, false);
        barChart = v.findViewById(R.id.barChartMonth);
        sqLitedemoOpenHelper = new SQLitedemoOpenHelper(getContext());

        //set data cho cot 1
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "Thu nhập");
        barDataSet1.setColor(Color.rgb(104, 241, 175));

        //set data cho cot 2
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "Chi tiêu");
        barDataSet2.setColor(Color.rgb(164, 228, 251));

        //set data cho bang
        BarData barData = new BarData(barDataSet1, barDataSet2);
        barChart.setData(barData);

        //set bieu do keo dai
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days1()));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

        //set hien thi gia tri so lieu cot
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        //set do dai, khoang cach cot
        float groupSpace = 0.4f;
        float barSpace = 0.1f;
        float barWidth = 0.20f;
        barData.setBarWidth(barWidth);

        //set hien thi vao bang
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 31);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);
        Description description = new Description();
        description.setText("Thống kê thu chi tháng " + thang + " năm " + nam);
        barChart.setDescription(description);
        return v;
    }
    //lay thong tin cua cac khoan thu
    private ArrayList<BarEntry> barEntries1() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (float i = 1; i <= 31; i++) {
            String ngay = String.valueOf(i);
            ngay = ngay.substring(0, ngay.length() - 2);
            if (ngay.length() <= 1) ngay = "0" + ngay;
            List<ThuNhap> thuNhapList = sqLitedemoOpenHelper.getThuNhapNgay(ngay, thang, nam);
            double tien = 0.0;
            if (!thuNhapList.isEmpty()) {
                for (ThuNhap j : thuNhapList) {
                    tien += j.getTien();
                }
            }
            float tient = Float.parseFloat(chiaChuoi(tien));
            barEntries.add(new BarEntry(i, tient));
        }
        return barEntries;
    }

    //lay thong tin cua cac khoan chi
    private ArrayList<BarEntry> barEntries2() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (float i = 1; i <= 31; i++) {
            String ngay = String.valueOf(i);
            ngay = ngay.substring(0, ngay.length() - 2);
            if (ngay.length() <= 1) ngay = "0" + ngay;
            List<ChiTieu> chiTieuList = sqLitedemoOpenHelper.getChiTieuNgay(ngay, thang, nam);
            double tien = 0.0;
            if (!chiTieuList.isEmpty()) {
                for (ChiTieu j : chiTieuList) {
                    tien += j.getTien();
                }
            }
            float tient = Float.parseFloat(chiaChuoi(tien));
            barEntries.add(new BarEntry(i, tient));
        }
        return barEntries;
    }

    //chia chuoi tien nhan duoc thanh dang can dung
    private String chiaChuoi(double t) {
        Locale locale = new Locale("en", "EN");
        NumberFormat decimalFormat = NumberFormat.getNumberInstance(locale);
        String tien = decimalFormat.format(t);
        String[] parts = tien.split(",");
        tien = "";
        for (String i : parts) {
            tien += i;
        }
        return tien;
    }
    //set thong tin hien thi ngay
    private ArrayList<String> days1() {
        ArrayList<String> days = new ArrayList<>();
        for (float i = 1; i <= 31; i++) {
            String ngay = String.valueOf(i);
            ngay = ngay.substring(0, ngay.length() - 2);
            if (ngay.length() <= 1) ngay = "0" + ngay;
            String day = ngay + "-" + thang + "-" + nam;
            days.add(day);
        }
        return days;
    }
}