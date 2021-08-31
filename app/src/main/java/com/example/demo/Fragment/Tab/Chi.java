package com.example.demo.Fragment.Tab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Fragment.Adapter.ChiRecyclerViewAdapter;
import com.example.demo.Fragment.SQLite.SQLitedemoOpenHelper;
import com.example.demo.Fragment.Nhap.NhapChi;
import com.example.demo.Model.ChiTieu;
import com.example.demo.Model.DanhMucChi;
import com.example.demo.R;
import com.example.demo.Spinner.SpinnerChiAdapter;
import com.example.demo.Spinner.SpinnerThuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Chi extends Fragment implements NhapChi.ShowInChiInterface {
    private View v;
    private RecyclerView revChi;
    private SQLitedemoOpenHelper sqLite;
    public List<ChiTieu> list;
    ChiRecyclerViewAdapter adapter;
    private FloatingActionButton fabChi;
    private List<DanhMucChi> danhMuc;
    private RadioButton btLocLoai, btLocThoiGian,btLocAll;
    int srcimg=0;
    List<String> names ;
    public Chi() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chi, container, false);

        //anh xa
        revChi = v.findViewById(R.id.revChi);
        fabChi = v.findViewById(R.id.fabChi);
        btLocLoai = v.findViewById(R.id.locChiLoai);
        btLocThoiGian = v.findViewById(R.id.locChiThoiGian);
        btLocAll = v.findViewById(R.id.locChiAll);

        //set recyclerView
        sqLite = new SQLitedemoOpenHelper(getContext());
        try {
            list = sqLite.getChiTieuAll();
        }catch (Exception e){
            System.out.println("Lỗi getAll chi tiêu");
        }
        adapter = new ChiRecyclerViewAdapter(this,list);
        adapter.setList(list);
        revChi.setLayoutManager(new LinearLayoutManager(getActivity()));
        revChi.setAdapter(adapter);

        //set fabChi
        fabChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhapChi nhapChi = new NhapChi();
                nhapChi.setTargetFragment(Chi.this,1);
                nhapChi.show(getFragmentManager(),"Nhap khoan chi");
            }
        });

        //set sploc
        btLocLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocThoiGian.isChecked()==true) btLocThoiGian.setChecked(false);
                if(btLocAll.isChecked()==true) btLocAll.setChecked(false);
                locTheoLoai();
            }
        });

        btLocThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocLoai.isChecked()==true) btLocLoai.setChecked(false);
                if(btLocAll.isChecked()==true) btLocAll.setChecked(false);
                locTheoThoiGian();
            }
        });

        btLocAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLocLoai.isChecked()==true) btLocLoai.setChecked(false);
                if(btLocThoiGian.isChecked()==true) btLocThoiGian.setChecked(false);
                list.clear();
                try {
                    list = sqLite.getChiTieuAll();
                }catch (Exception e){
                    System.out.println("Lỗi getAll chi tiêu");
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        });


        return v;
    }

    public void locTheoThoiGian(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Lọc khoản chi");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.thoi_gian_tuy_chon,null);
        dialog.setView(view);

        //khai bao cac truong
        TextView lichTTC, lichDTC;
        ImageButton btlichTTC,btlichDTC;

        //Anh xa
        lichTTC = view.findViewById(R.id.lichTTC);
        lichDTC = view.findViewById(R.id.lichDTC);
        btlichTTC = view.findViewById(R.id.btLichTTC);
        btlichDTC = view.findViewById(R.id.btLichDTC);


        //set thoi gian default
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichTTC.setText(simpleDateFormat.format(calendar.getTime()));
        lichDTC.setText(simpleDateFormat.format(calendar.getTime()));

        //set Thoi gian
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);


        //set lich chon
        btlichTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        //set cac button action
        dialog.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tu = lichTTC.getText().toString();
                String den = lichDTC.getText().toString();
                list.clear();
                list = sqLite.getChiTieuTuyChon(tu, den);
                adapter.setList(list);
                adapter.notifyDataSetChanged();

            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void locTheoLoai(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Lọc khoản chi");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.loc_du_lieu_loai,null);
        dialog.setView(view);
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);

        //khai bao cac truong
        EditText tienDau, tienCuoi;
        Spinner spLoai;
        SpinnerThuAdapter spinnerAdapter;

        //Anh xa
        tienCuoi = view.findViewById(R.id.edTienCuoi);
        tienDau = view.findViewById(R.id.edTienDau);
        spLoai = view.findViewById(R.id.spDanhMuc);

        //set spinner
        names = getDataLoai();
        spinnerAdapter = new SpinnerThuAdapter(getActivity(),names);
        spLoai.setAdapter(spinnerAdapter);

        //set item selection
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

        //cai dat nhap tien
        tienDau.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tienDau.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }
                
                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tienDau.setText(formatted);
                tienDau.setSelection(formatted.length()-3);
                tienDau.addTextChangedListener(this);
            }
        });

        tienCuoi.addTextChangedListener(new TextWatcher() {
            String t="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tienCuoi.removeTextChangedListener(this);
                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                if (parsed == 0.0) parsed=1000.0;
                if (parsed<100.0){
                    parsed = parsed*100;
                }
                
                String formatted = decimalFormat.format(parsed);
                t = formatted;
                tienCuoi.setText(formatted);
                tienCuoi.setSelection(formatted.length()-3);
                tienCuoi.addTextChangedListener(this);
            }
        });

        //set cac button action
        dialog.setPositiveButton("Lọc", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String []parts = spLoai.getSelectedItem().toString().split("-");
                String loai = parts[0];
                String tD = tienDau.getText().toString();
                if(tD.isEmpty())    {
                    double t = 100.0;
                    tD = decimalFormat.format(t);
                }
                tD= tD.replaceAll("[., ]","");

                String tC =tienCuoi.getText().toString();
                if ((tC.isEmpty())){
                    double t= 10.0E99;
                    tC = decimalFormat.format(t);
                }
                tC= tC.replaceAll("[., ]","");
                list.clear();
                try {
                    list = sqLite.getChiTieuTheoLoai(loai,tD,tC);
                }catch (Exception e){
                    System.out.println("Lỗi get chi tiêu theo loại");
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void updateChi(ChiTieu chiTieu){

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Sửa khoản chi");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.nhap_chi,null);
        dialog.setView(view);

        //Khai báo các trường để sử dụng
        EditText tien ,ghiChu;
        TextView lichChi;
        Spinner spLoai;
        SpinnerChiAdapter spinnerAdapter;
        ImageButton btLich,btThemDanhMuc;

        //anh xa ca truong
        tien = view.findViewById(R.id.tienChi);
        lichChi = view.findViewById(R.id.lichChi);
        ghiChu= view.findViewById(R.id.ghichuChi);
        spLoai =view.findViewById(R.id.spDanhMucChi);
        btLich = view.findViewById(R.id.btLichChi);
        btThemDanhMuc = view.findViewById(R.id.btThemDanhMucChi);

        // set cac gia tri vơi cac gia tri lay duoc tien, lich, ghichu

        double num = Double.parseDouble(chiTieu.getTien().toString());
        Locale locale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        String tient = decimalFormat.format(num);
        tien.setText(tient);
        lichChi.setText(chiTieu.getTgian());
        ghiChu.setText(chiTieu.getGhiChu());

        //set spinner
        names = getDataLoai();
        spinnerAdapter = new SpinnerChiAdapter(getActivity(), names);
        spLoai.setAdapter(spinnerAdapter);

        //set loai voi gia tri lay duoc
        int vitri=0;
        for(String i: names){
            String loai = i;
            String []parts = loai.split("-");
            String part1 = parts[0];
            if(chiTieu.getLoai().equals(part1)) break;
            else    vitri++;
        }
        spLoai.setSelection(vitri);

        //them danh muc
        btThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Thêm danh mục chi");
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
                            DanhMucChi danhMucChi = new DanhMucChi(R.drawable.thu_chi,edThem.getText().toString());
                            sqLite.addDanhMucChi(danhMucChi);
                            SpinnerChiAdapter adapter = new SpinnerChiAdapter(getContext(),getDataLoai());
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

        //set item chon
        names = getDataLoai();
        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = getDataLoai().get(position);
                String []parts = loai.split("-");
                String part2 = parts[1];
                srcimg = Integer.parseInt(part2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //cai dat nhap tien
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lichChi.setText(simpleDateFormat.format(calendar.getTime()));

        //get date
        btLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        lichChi.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },  nam,thang,ngay);
                dialog.show();
            }
        });

        //Set cac action
        dialog.setPositiveButton("SỬA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String []parts = spLoai.getSelectedItem().toString().split("-");
                chiTieu.setLoai(parts[0]);
                String t = tien.getText().toString();
                t= t.replaceAll("[., ]","");
                chiTieu.setTien(Double.parseDouble(t));
                chiTieu.setTgian(lichChi.getText().toString());
                chiTieu.setGhiChu(ghiChu.getText().toString());
                chiTieu.setSrcimg(srcimg);
                if(tien.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Bạn chưa nhập số tiền!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    sqLite.updateChiTieu(chiTieu);
                    list.clear();
                    try {
                        list = sqLite.getChiTieuAll();
                    }catch (Exception e){
                        System.out.println("Lỗi getAll chi tiêu");
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public List<String> getDataLoai(){
        List<String> kind = new ArrayList<>();
        danhMuc = sqLite.getDanhMucChiAll();
        if(danhMuc.isEmpty()){
            sqLite.createDanhMucChi();
            danhMuc = sqLite.getDanhMucChiAll();
        }
        for(DanhMucChi i:danhMuc){
            String value = i.getLoai()+"-"+ i.getSrcimg();
            kind.add(value);
        }
        return kind;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void sendChi(int src,String loai, String tien, String tgian, String ghiChu) {
        try {
            sqLite.addChiTieu(new ChiTieu(src,loai,Double.parseDouble(tien),tgian,ghiChu));
        }catch (Exception e){
            System.out.println("Lỗi thêm chi tiêu");
        }
        list.clear();
        try {
            list = sqLite.getChiTieuAll();
        }catch (Exception e){
            System.out.println("Lỗi getAll chi tiêu");
        }
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }
}