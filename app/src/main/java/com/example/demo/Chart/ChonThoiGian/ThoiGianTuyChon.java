package com.example.demo.Chart.ChonThoiGian;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.demo.Chart.OpenChartInterface;
import com.example.demo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThoiGianTuyChon extends AppCompatDialogFragment {
    private TextView lichTTC,lichDTC;
    private ImageButton btlichTTC,btlichDTC;
    private View v;
    private OpenChartInterface listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.thoi_gian_tuy_chon,null);
        builder.setView(v).setTitle("Chọn khoảng thời gian").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tu = lichTTC.getText().toString();
                String den = lichDTC.getText().toString();
                listener.openPieChart(tu,den);
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //set thoi gian default
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichTTC = v.findViewById(R.id.lichTTC);
        lichTTC.setText(simpleDateFormat.format(calendar.getTime()));
        lichDTC = v.findViewById(R.id.lichDTC);
        lichDTC.setText(simpleDateFormat.format(calendar.getTime()));

        //set lich chon
        btlichTTC = v.findViewById(R.id.btLichTTC);
        btlichDTC = v.findViewById(R.id.btLichDTC);
        btlichTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        lichTTC.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });
        btlichDTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        lichDTC.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });
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

