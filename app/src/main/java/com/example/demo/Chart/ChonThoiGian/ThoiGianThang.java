package com.example.demo.Chart.ChonThoiGian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.demo.Chart.OpenChartInterface;
import com.example.demo.R;

import java.util.Calendar;

public class ThoiGianThang extends AppCompatDialogFragment {
    private Spinner lichTKTT, lichTKTN;
    private View v;
    private OpenChartInterface listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.thoi_gian_thang,null);
        builder.setView(v).setTitle("Chọn khoảng thời gian").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String thang = lichTKTT.getSelectedItem().toString();
                String nam = lichTKTN.getSelectedItem().toString();
                listener.openBarChart(thang, nam);
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Calendar calendar = Calendar.getInstance();
        lichTKTT = v.findViewById(R.id.lichTKTT);
        int thang = calendar.get(Calendar.MONTH);
        lichTKTT.setSelection(thang);
        lichTKTN = v.findViewById(R.id.lichTKTN);
        int nam = calendar.get(Calendar.YEAR);
        nam = nam - 2014;
        lichTKTN.setSelection(nam);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OpenChartInterface) context;
        }catch (Exception e){

            e.printStackTrace();
        }

    }
}
