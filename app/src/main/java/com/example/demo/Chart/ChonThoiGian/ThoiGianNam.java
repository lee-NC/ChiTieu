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

public class ThoiGianNam extends AppCompatDialogFragment {
    private Spinner lichTKN;
    private View v;
    private OpenChartInterface listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.thoi_gian_nam,null);
        builder.setView(v).setTitle("Chọn khoảng thời gian").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nam = lichTKN.getSelectedItem().toString();
                listener.openBarYChart(nam);
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Calendar calendar = Calendar.getInstance();
        lichTKN = v.findViewById(R.id.lichTKN);
        int nam = calendar.get(Calendar.YEAR);
        nam = nam - 2014;
        lichTKN.setSelection(nam);
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
