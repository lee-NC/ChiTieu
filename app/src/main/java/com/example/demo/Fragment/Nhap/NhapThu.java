package com.example.demo.Fragment.Nhap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Model.DanhMucChi;
import com.example.demo.Model.DanhMucThu;
import com.example.demo.R;
import com.example.demo.Spinner.SpinnerChiAdapter;
import com.example.demo.Spinner.SpinnerThuAdapter;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NhapThu extends AppCompatDialogFragment  {
    private EditText tien, ghichu;
    private TextView lichThu;
    private Spinner spLoai;
    private View v;
    private int srcimg =0;
    public ShowInThuInterface showInThuInterface;
    private ImageButton btLich,btThemDanhMuc;
    private SpinnerThuAdapter spinnerAdapter;
    private SQLitedemoOpenHelper sqLiteDanhMucOpenHelper;
    private List<DanhMucThu> danhMuc;
    List<String>names;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        sqLiteDanhMucOpenHelper = new SQLitedemoOpenHelper(getContext());
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
        v= inflater.inflate(R.layout.nhap_thu,null);
        tien = v.findViewById(R.id.tienThu);
        tien.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tien.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }
                
                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tien.setText(formatted);
                tien.setSelection(formatted.length()-3);
                tien.addTextChangedListener(this);
            }
        });

        //set date now
        lichThu = v.findViewById(R.id.lichThu);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichThu.setText(simpleDateFormat.format(calendar.getTime()));

        ghichu= v.findViewById(R.id.ghichuThu);

        //set spinner
        spLoai =v.findViewById(R.id.spDanhMucThu);
        names = getDataLoai();
        spinnerAdapter = new SpinnerThuAdapter(getActivity(),names);
        spLoai.setAdapter(spinnerAdapter);

        //them danh muc
        btThemDanhMuc = v.findViewById(R.id.btThemDanhMucThu);
        btThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Thêm danh mục thu");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view= inflater.inflate(R.layout.them_danh_muc,null);
                dialog.setView(view);

                EditText edThem;

                edThem = view.findViewById(R.id.edThemDanhMuc);

                dialog.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edThem.getText().equals("")) Toast.makeText(getContext(),"Bạn chưa nhập danh mục muốn thêm",Toast.LENGTH_SHORT).show();
                        else{
                            DanhMucThu danhMucThu = new DanhMucThu(R.drawable.thu_chi,edThem.getText().toString());
                            sqLiteDanhMucOpenHelper.addDanhMucThu(danhMucThu);
                            SpinnerThuAdapter adapter = new SpinnerThuAdapter(getContext(),getDataLoai());
                            spLoai.setAdapter(adapter);
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        //set item
        names = getDataLoai();
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = names.get(position);
                String []parts = loai.split("-");
                String part2 = parts[1];
                srcimg = Integer.parseInt(part2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set DatePicker
        btLich = v.findViewById(R.id.btLichThu);
        btLich.setOnClickListener(new View.OnClickListener() {
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
                        lichThu.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });
        //set Dialog
        builder.setView(v).setTitle("Nhập khoản thu").setPositiveButton("LƯU", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String []parts = spLoai.getSelectedItem().toString().split("-");
                String loai = parts[0];
                String tient = tien.getText().toString();
                tient= tient.replaceAll("[., ]","");
                String lich = lichThu.getText().toString();
                String gc = ghichu.getText().toString();
                int src = srcimg;
                if(tien.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Bạn chưa nhập số tiền!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    showInThuInterface.sendThu(src,loai,tient,lich,gc);
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public List<String> getDataLoai(){
        List<String> kind = new ArrayList<>();
        danhMuc = sqLiteDanhMucOpenHelper.getDanhMucThuAll();
        if(danhMuc.isEmpty()){
            sqLiteDanhMucOpenHelper.createDanhMucThu();
            danhMuc = sqLiteDanhMucOpenHelper.getDanhMucThuAll();
        }
        for(DanhMucThu i:danhMuc){
            String value = i.getLoai()+"-"+ i.getSrcimg();
            kind.add(value);
        }
        return kind;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            showInThuInterface = (ShowInThuInterface) getTargetFragment();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface ShowInThuInterface {
        void sendThu(int srcimg,String loai, String tien, String tgian, String ghiChu);
    }
}
