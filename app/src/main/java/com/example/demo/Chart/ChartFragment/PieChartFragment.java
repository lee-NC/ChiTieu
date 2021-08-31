package com.example.demo.Chart.ChartFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.ThuNhap;
import com.example.demo.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {
    private View v;
    private String timeBD = "";
    private String timeKT = "";
    private PieChart pieChart;
    private SQLitedemoOpenHelper sqLitedemoOpenHelper;

    public PieChartFragment(String tu, String den) {
        this.timeBD = tu;
        this.timeKT = den;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pieChart = v.findViewById(R.id.pieChart);
        setupPieChart();
        loadPieChartData();
        return v;
    }
    
    //tạo giao dien cho chart
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Thu chi từ "+timeBD+" đến "+timeKT);
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    //Lay du lieu de thưc thi
    private void loadPieChartData(){
        //Lay thong tin trong cơ so dư lieu va xu li
        double tongThu = 0.0;
        String tu = timeBD;
        String den = timeKT;
        sqLitedemoOpenHelper = new SQLitedemoOpenHelper(getContext());
        List<ThuNhap> thuNhapList = sqLitedemoOpenHelper.getThuNhapTuyChon(tu, den);
        for (ThuNhap i : thuNhapList) {
            tongThu += i.getTien();
        }
        double tongChi = 0.0;
        List<ChiTieu> chiTieuList = sqLitedemoOpenHelper.getChiTieuTuyChon(tu, den);
        for (ChiTieu i : chiTieuList) {
            tongChi += i.getTien();
        }
        float thu = (float) (tongThu);
        float chi = (float)(tongChi);

        //add du lieu vao du lieu nhap
        ArrayList<PieEntry> thongKe = new ArrayList<>();
        thongKe.add(new PieEntry(thu, "Thu nhập"));
        thongKe.add(new PieEntry(chi, "Chi tiêu"));
        //cau hinh cho bang
        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }
        PieDataSet pieDataSet = new PieDataSet(thongKe, "Thống kê");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}